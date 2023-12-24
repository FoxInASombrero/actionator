package hu.szviktor.modules.actionator.action.executor.bukkit;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import hu.szviktor.modules.actionator.action.executor.ActionExecutor;

public class BukkitActionExecutor implements ActionExecutor {

	@NotNull
	private Player executor;

	public BukkitActionExecutor(@NotNull Player executor) {
		Preconditions.checkArgument(executor != null, "A parancsot végrehajtó játékos nem lehet null!");

		this.executor = executor;
	}

	@Override
	public Object getExecutor() {
		return this.executor;
	}

}
