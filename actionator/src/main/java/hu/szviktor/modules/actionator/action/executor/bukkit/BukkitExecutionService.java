package hu.szviktor.modules.actionator.action.executor.bukkit;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import hu.szviktor.modules.actionator.action.AbstractAction;
import hu.szviktor.modules.actionator.action.ActionType;
import hu.szviktor.modules.actionator.action.actions.EconomyAction;
import hu.szviktor.modules.actionator.action.actions.MessageAction;
import hu.szviktor.modules.actionator.action.actions.PotionEffectAction;
import hu.szviktor.modules.actionator.action.actions.PotionEffectAction.PotionEffectActionData;
import hu.szviktor.modules.actionator.action.actions.RunCommandAction;
import hu.szviktor.modules.actionator.action.executor.ActionExecutor;
import hu.szviktor.modules.actionator.action.executor.ExecutionException;
import hu.szviktor.modules.actionator.action.executor.ExecutionService;
import hu.szviktor.modules.actionator.condition.AbstractCondition;
import hu.szviktor.modules.actionator.condition.ConditionResponse;
import hu.szviktor.modules.actionator.condition.ConditionType;
import hu.szviktor.modules.actionator.condition.conditions.EconomyCondition;
import hu.szviktor.modules.actionator.condition.conditions.PermissionCondition;
import hu.szviktor.modules.actionator.condition.conditions.worldguard.RegionCondition;
import hu.szviktor.modules.actionator.condition.conditions.worldguard.RegionCondition.RegionConditionData;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class BukkitExecutionService implements ExecutionService {

	@NotNull
	private Economy economy;

	@NotNull
	private Permission permission;

	boolean isVaultSupported;

	private WorldGuard worldGuard;

	public BukkitExecutionService() {
		this.checkVault();

		this.worldGuard = WorldGuard.getInstance();
	}

	private void checkVault() {
		PluginManager pluginManager = Bukkit.getPluginManager();

		if (pluginManager.getPlugin("Vault") == null) {
			Bukkit.getLogger()
					.warning("A Vault kapcsolat nem tudott létrejönni, ezért annak funkciói nem használhatóak!");

			return;
		}

		ServicesManager servicesManager = Bukkit.getServicesManager();

		this.economy = servicesManager.getRegistration(Economy.class).getProvider();
		this.permission = servicesManager.getRegistration(Permission.class).getProvider();

		this.isVaultSupported = true;
	}

	public ActionExecutor wrapPlayer(@NotNull Player executor) {
		return new BukkitActionExecutor(executor);
	}

	@Override
	public boolean execute(@NotNull ActionExecutor executor, @NotNull AbstractAction action) throws ExecutionException {
		Preconditions.checkArgument(executor != null, "A végrehajtó nem lehet null!");
		Preconditions.checkArgument(action != null, "Az Action nem lehet null!");

		if (!(executor.getExecutor() instanceof Player))
			throw new ExecutionException("Bukkit üzemmódban a végrehajtó csak játékos lehet!");

		Player player = (Player) executor.getExecutor();
		ActionType type = action.getType();
		List<AbstractCondition> conditions = action.getConditions();

		this.checkConditions(executor, action);

		if (conditions.stream().allMatch(condition -> condition.getResponse().equals(ConditionResponse.SUCCESS))) {
			if (type == ActionType.SEND_MESSAGE) {
				MessageAction wrappedAction = (MessageAction) action;
				String message = wrappedAction.getValue();

				player.sendRawMessage(message);

				return true;
			}

			if (type == ActionType.RUN_COMMAND) {
				RunCommandAction wrappedAction = (RunCommandAction) action;

				return player.performCommand(wrappedAction.getValue());
			}

			if (type == ActionType.ECONOMY) {
				EconomyAction wrappedAction = (EconomyAction) action;
				double amount = wrappedAction.getData().getAmount();

				return (this.isVaultSupported) && (this.economy.withdrawPlayer(player, amount).transactionSuccess());
			}

			if (type == ActionType.POTION_EFFECT) {
				PotionEffectAction wrappedAction = (PotionEffectAction) action;
				PotionEffectActionData data = wrappedAction.getData();
				String effect = data.getValue();
				int duration = data.getDuration();
				int amplifier = data.getAmplifier();
				boolean ambient = data.isAmbient();
				boolean particles = data.hasParticles();

				return player.addPotionEffect(
						new PotionEffect(PotionEffectType.getByName(effect), duration, amplifier, ambient, particles));
			}
		}

		return false;
	}

	@Override
	public void checkConditions(@NotNull ActionExecutor executor, @NotNull AbstractAction action) {
		Player player = (Player) executor.getExecutor();
		List<AbstractCondition> conditions = action.getConditions();

		conditions.forEach(condition -> {
			ConditionType conditionType = condition.getType();

			if (conditionType == ConditionType.HAS_PERMISSION) {
				PermissionCondition wrappedCondition = (PermissionCondition) condition;
				String permission = wrappedCondition.getValue();

				condition.setResponse(
						player.hasPermission(permission) ? ConditionResponse.SUCCESS : ConditionResponse.FAILED);
			}

			if (conditionType == ConditionType.HAS_MONEY) {
				EconomyCondition wrappedCondition = (EconomyCondition) condition;
				double amount = wrappedCondition.getAmount();

				condition.setResponse(
						((this.isVaultSupported) && this.economy.has(player, amount)) ? ConditionResponse.SUCCESS
								: ConditionResponse.FAILED);
			}

			if (conditionType == ConditionType.IN_REGION) {
				RegionCondition wrappedCondition = (RegionCondition) condition;
				RegionConditionData data = wrappedCondition.getData();
				String world = data.getWorld();
				String region = data.getValue();

				RegionManager regionManager = this.worldGuard.getPlatform().getRegionContainer()
						.get(BukkitAdapter.adapt(Bukkit.getWorld(world)));

				if (!regionManager.hasRegion(region)) {
					condition.setResponse(ConditionResponse.FAILED);

					return;
				}

				ProtectedRegion wrappedRegion = regionManager.getRegion(region);

				condition
						.setResponse(wrappedRegion.contains(BukkitAdapter.asVector(player.getLocation()).toBlockPoint())
								? ConditionResponse.SUCCESS
								: ConditionResponse.FAILED);
			}
		});
	}

}
