package hu.szviktor.modules.actionator.action.actions;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.base.Preconditions;

import hu.szviktor.modules.actionator.action.AbstractAction;
import hu.szviktor.modules.actionator.action.ActionType;
import hu.szviktor.modules.actionator.condition.AbstractCondition;

public class PotionEffectAction extends AbstractAction {

	@NotNull
	private PotionEffectActionData data;

	public PotionEffectAction(ActionType type, @NotNull PotionEffectActionData data,
			@Nullable List<AbstractCondition> conditions) {
		super(type, conditions);

		Preconditions.checkArgument(data != null, "A megadott PotionEffectActionData nem lehet null!");

		this.data = data;
	}

	public @NotNull PotionEffectActionData getData() {
		return this.data;
	}

	public class PotionEffectActionData {

		@NotNull
		private String value;

		private int duration;

		private int modifier;

		public PotionEffectActionData(@NotNull String value, int duration, int modifier) {
			Preconditions.checkArgument(value != null, "A PotionEffectActionData értéke nem lehet null!");
			Preconditions.checkArgument(duration >= 1, "Az effekt ideje nem lehet kevesebb, mint 1 másodperc!");
			Preconditions.checkArgument(modifier >= 1, "Az effekt erőssége nem lehet kevesebb, mint 1!");

			this.value = value;
			this.duration = duration;
			this.modifier = modifier;
		}

		public @NotNull String getValue() {
			return this.value;
		}

		public int getDuration() {
			return this.duration;
		}

		public int getModifier() {
			return this.modifier;
		}

	}

}