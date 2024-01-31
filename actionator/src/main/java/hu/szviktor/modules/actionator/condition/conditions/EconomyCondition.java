package hu.szviktor.modules.actionator.condition.conditions;

import hu.szviktor.modules.actionator.condition.AbstractCondition;
import hu.szviktor.modules.actionator.condition.ConditionType;

public class EconomyCondition extends AbstractCondition {

	private double amount;

	public EconomyCondition(double amount) {
		super(ConditionType.HAS_MONEY);

		this.amount = amount;
	}

	public double getAmount() {
		return this.amount;
	}

}
