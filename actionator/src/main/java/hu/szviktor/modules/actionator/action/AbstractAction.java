package hu.szviktor.modules.actionator.action;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import hu.szviktor.modules.actionator.condition.AbstractCondition;

public abstract class AbstractAction {

	private ActionType type;

	@Nullable
	private List<AbstractCondition> conditions;

	public AbstractAction(ActionType type, @Nullable List<AbstractCondition> conditions) {
		this.type = type;
		this.conditions = conditions;
	}

	public ActionType getType() {
		return this.type;
	}

	public @Nullable List<AbstractCondition> getConditions() {
		return this.conditions;
	}

}
