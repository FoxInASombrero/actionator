package hu.szviktor.modules.actionator.condition;

public abstract class AbstractCondition {

	private ConditionType type;

	private ConditionResponse response;

	public AbstractCondition(ConditionType type) {
		this.type = type;
	}

	public ConditionType getType() {
		return this.type;
	}

	public ConditionResponse getResponse() {
		return this.response;
	}

	public void setResponse(ConditionResponse response) {
		this.response = response;
	}

}
