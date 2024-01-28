package hu.szviktor.modules.actionator.action.actions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import hu.szviktor.modules.actionator.action.AbstractAction;
import hu.szviktor.modules.actionator.action.ActionType;
import hu.szviktor.modules.actionator.condition.AbstractCondition;

public class EconomyAction extends AbstractAction {

	@NotNull
	private EconomyActionData data;

	public EconomyAction(ActionType type, @NotNull EconomyActionData data,
			@Nullable List<AbstractCondition> conditions) {
		super(ActionType.ECONOMY, conditions);

		Preconditions.checkArgument(data != null, "Az action adatainak gyűjteménye nem lehet null!");

		this.data = data;
	}

	public @NotNull EconomyActionData getData() {
		return this.data;
	}

	public class EconomyActionData {

		private EconomyActionType type;

		private double amount;

		public EconomyActionData(EconomyActionType type, double amount) {
			this.type = type;
			this.amount = amount;
		}

		public EconomyActionType getType() {
			return this.type;
		}

		public double getAmount() {
			return this.amount;
		}

	}

	public enum EconomyActionType {
		GRANT("grant"), REVOKE("revoke"), SET("set");

		@NotNull
		private String name;

		@NotNull
		private static final HashMap<String, EconomyActionType> NAME_MAP = Maps.newHashMap();

		static {
			Arrays.stream(values()).forEach(value -> {
				NAME_MAP.put(value.getName(), value);
			});
		}

		private EconomyActionType(@NotNull String name) {
			Preconditions.checkArgument(name != null, "A név nem lehet null!");

			this.name = name;
		}

		public @NotNull String getName() {
			return this.name;
		}

		public static EconomyActionType findByName(@NotNull String name) {
			Preconditions.checkArgument(name != null, "A név nem lehet null!");

			if (!NAME_MAP.containsKey(name))
				return null;

			return NAME_MAP.get(name);
		}
	}

}
