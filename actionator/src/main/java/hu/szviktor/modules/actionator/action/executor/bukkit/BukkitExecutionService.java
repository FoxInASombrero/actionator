package hu.szviktor.modules.actionator.action.executor.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import hu.szviktor.modules.actionator.action.Action;
import hu.szviktor.modules.actionator.action.ActionType;
import hu.szviktor.modules.actionator.action.executor.ActionExecutor;
import hu.szviktor.modules.actionator.action.executor.ExecutionException;
import hu.szviktor.modules.actionator.action.executor.ExecutionService;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class BukkitExecutionService implements ExecutionService {

	@NotNull
	private Economy economy;

	@NotNull
	private Permission permission;

	private boolean isVaultSupported = false;

	public BukkitExecutionService() {
		this.checkVault();
	}

	private void checkVault() {
		PluginManager pluginManager = Bukkit.getPluginManager();

		if (pluginManager.getPlugin("Vault") == null)
			return;

		ServicesManager servicesManager = Bukkit.getServicesManager();

		this.economy = servicesManager.getRegistration(Economy.class).getProvider();
		this.permission = servicesManager.getRegistration(Permission.class).getProvider();

		this.isVaultSupported = true;
	}

	@Override
	public boolean execute(@NotNull ActionExecutor executor, @NotNull Action action) throws ExecutionException {
		Preconditions.checkArgument(action != null, "Az Action nem lehet null!");

		if (!(executor.getExecutor() instanceof Player))
			throw new ExecutionException("Bukkit üzemmódban a végrehajtó csak játékos lehet!");

		Player player = (Player) executor.getExecutor();

		ActionType type = action.getType();
		String value = action.getValue();

		if (type == ActionType.RUN_COMMAND) {
			if (value.startsWith("console:")) {
				String[] args = value.split(":");
				String command = args[1].replaceAll("%player%", player.getName());

				return Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
			}

			return Bukkit.dispatchCommand(player, value);
		}

		if (type == ActionType.SEND_MESSAGE) {
			player.sendRawMessage(value);

			return true;
		}

		if (type == ActionType.GRANT_PERMISSION) {
			if (!this.isVaultSupported) {
				Bukkit.getLogger().warning(
						"A Vault kapcsolat nem jöhet létre, ezért a feladatot nem lehet Vault segítségével végrehajtani!");

				throw new ExecutionException(
						"Nem sikerült a feladat végrehajtása! Kérlek, vedd fel a kapcsolatot egy adminisztrátorral!");
			}

			return this.permission.playerAdd(null, player, value);
		}

		if (type == ActionType.REVOKE_PERMISSION) {
			if (!this.isVaultSupported) {
				Bukkit.getLogger().warning(
						"A Vault kapcsolat nem jöhet létre, ezért a feladatot nem lehet Vault segítségével végrehajtani!");

				throw new ExecutionException(
						"Nem sikerült a feladat végrehajtása! Kérlek, vedd fel a kapcsolatot egy adminisztrátorral!");
			}

			return this.permission.playerRemove(null, player, value);
		}

		if (type == ActionType.SET_GROUP) {
			if (!this.isVaultSupported) {
				Bukkit.getLogger().warning(
						"A Vault kapcsolat nem jöhet létre, ezért a feladatot nem lehet Vault segítségével végrehajtani!");

				throw new ExecutionException(
						"Nem sikerült a feladat végrehajtása! Kérlek, vedd fel a kapcsolatot egy adminisztrátorral!");
			}

			return this.permission.playerAddGroup(null, player, value);
		}

		return false;
	}

}
