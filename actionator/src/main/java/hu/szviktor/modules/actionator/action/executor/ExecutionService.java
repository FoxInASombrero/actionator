package hu.szviktor.modules.actionator.action.executor;

import org.jetbrains.annotations.NotNull;

import hu.szviktor.modules.actionator.action.Action;

public interface ExecutionService {

	public boolean execute(@NotNull ActionExecutor executor, @NotNull Action action) throws ExecutionException;

}
