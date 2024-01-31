package hu.szviktor.modules.actionator.action.executor.bukkit;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import hu.szviktor.modules.actionator.action.AbstractAction;
import hu.szviktor.modules.actionator.action.ActionType;
import hu.szviktor.modules.actionator.action.actions.EconomyAction;
import hu.szviktor.modules.actionator.action.actions.RunCommandAction;
import hu.szviktor.modules.actionator.action.executor.ActionExecutor;
import hu.szviktor.modules.actionator.action.executor.ExecutionException;
import hu.szviktor.modules.actionator.action.executor.ExecutionService;
import hu.szviktor.modules.actionator.condition.AbstractCondition;
import hu.szviktor.modules.actionator.condition.ConditionResponse;
import hu.szviktor.modules.actionator.condition.ConditionType;
import hu.szviktor.modules.actionator.condition.conditions.PermissionCondition;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class BukkitExecutionService implements ExecutionService {

	@NotNull
	private Economy economy;

	@NotNull
	private Permission permission;

	boolean isVaultSupported;

	public BukkitExecutionService() {
		this.checkVault();
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

	@Override
	public boolean execute(@NotNull ActionExecutor executor, @NotNull AbstractAction action) throws ExecutionException {
		Preconditions.checkArgument(action != null, "Az Action nem lehet null!");

		if (!(executor.getExecutor() instanceof Player))
			throw new ExecutionException("Bukkit üzemmódban a végrehajtó csak játékos lehet!");

		Player player = (Player) executor.getExecutor();
		ActionType type = action.getType();
		List<AbstractCondition> conditions = action.getConditions();

		conditions.forEach(condition -> {
			ConditionType conditionType = condition.getType();

			if (conditionType == ConditionType.HAS_PERMISSION) {
				PermissionCondition wrappedCondition = (PermissionCondition) condition;
				String permission = wrappedCondition.getValue();

				condition.setResponse(
						player.hasPermission(permission) ? ConditionResponse.SUCCESS : ConditionResponse.FAILED);
			}
		});

		if (conditions.stream().allMatch(condition -> condition.getResponse().equals(ConditionResponse.SUCCESS))) {
			if (type == ActionType.RUN_COMMAND) {
				RunCommandAction wrappedAction = (RunCommandAction) action;

				return player.performCommand(wrappedAction.getValue());
			}

			if (type == ActionType.ECONOMY) {
				EconomyAction wrappedAction = (EconomyAction) action;
				double amount = wrappedAction.getData().getAmount();

				return (this.isVaultSupported) && (this.economy.withdrawPlayer(player, amount).transactionSuccess());
			}
		}

		return false;
	}

}
