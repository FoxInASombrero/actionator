package hu.szviktor.modules.actionator.action.executor.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import hu.szviktor.modules.actionator.action.AbstractAction;
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

		boolean isVaultSupported = true;
	}

	@Override
	public boolean execute(@NotNull ActionExecutor executor, @NotNull AbstractAction action) throws ExecutionException {
		Preconditions.checkArgument(action != null, "Az Action nem lehet null!");

		if (!(executor.getExecutor() instanceof Player))
			throw new ExecutionException("Bukkit üzemmódban a végrehajtó csak játékos lehet!");

		Player player = (Player) executor.getExecutor();

		ActionType type = action.getType();

		// TODO: teljesen újraírni az egészet.

		return false;
	}

}
