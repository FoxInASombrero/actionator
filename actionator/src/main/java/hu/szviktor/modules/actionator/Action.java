package hu.szviktor.modules.actionator;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

public class Action {

	private ActionType type;

	@NotNull
	private String value;

	public Action(ActionType type, @NotNull String value) {
		Preconditions.checkArgument(value != null, "Az érték nem lehet null!");

		this.type = type;
		this.value = value;
	}

	public ActionType getType() {
		return this.type;
	}

	public @NotNull String getValue() {
		return this.value;
	}

}
