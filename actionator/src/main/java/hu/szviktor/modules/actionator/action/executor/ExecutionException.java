package hu.szviktor.modules.actionator.action.executor;

import org.jetbrains.annotations.NotNull;

public class ExecutionException extends Exception {

	private static final long serialVersionUID = -4944836193146072794L;

	public ExecutionException(@NotNull String message) {
		super(message);
	}

}
