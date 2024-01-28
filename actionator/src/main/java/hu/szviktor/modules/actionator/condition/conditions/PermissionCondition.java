package hu.szviktor.modules.actionator.condition.conditions;

import org.jetbrains.annotations.NotNull;

import hu.szviktor.modules.actionator.condition.AbstractCondition;
import hu.szviktor.modules.actionator.condition.ConditionType;

public class PermissionCondition extends AbstractCondition {

	@NotNull
	private String value;

	public PermissionCondition() {
		super(ConditionType.HAS_PERMISSION);
	}

	public @NotNull String getValue() {
		return this.value;
	}

}
