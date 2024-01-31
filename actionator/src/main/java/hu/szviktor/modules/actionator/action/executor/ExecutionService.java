package hu.szviktor.modules.actionator.action.executor;

import org.jetbrains.annotations.NotNull;

import hu.szviktor.modules.actionator.action.AbstractAction;

public interface ExecutionService {

	public boolean execute(@NotNull ActionExecutor executor, @NotNull AbstractAction action) throws ExecutionException;

	void checkConditions(@NotNull ActionExecutor executor, @NotNull AbstractAction action);

}
