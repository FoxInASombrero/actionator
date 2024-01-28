package hu.szviktor.modules.actionator.condition;

public abstract class AbstractCondition {

	private ConditionType type;

	public AbstractCondition(ConditionType type) {
		this.type = type;
	}

	public ConditionType getType() {
		return this.type;
	}

}
