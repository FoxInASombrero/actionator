package hu.szviktor.modules.actionator.action.actions;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.base.Preconditions;

import hu.szviktor.modules.actionator.action.AbstractAction;
import hu.szviktor.modules.actionator.action.ActionType;
import hu.szviktor.modules.actionator.condition.AbstractCondition;

public class RunCommandAction extends AbstractAction {

	@NotNull
	private String value;

	public RunCommandAction(@NotNull String value, @Nullable List<AbstractCondition> conditions) {
		super(ActionType.RUN_COMMAND, conditions);

		Preconditions.checkArgument(value != null, "Az action értéke nem lehet null!");

		this.value = value;
	}

	public @NotNull String getValue() {
		return this.value;
	}

}
