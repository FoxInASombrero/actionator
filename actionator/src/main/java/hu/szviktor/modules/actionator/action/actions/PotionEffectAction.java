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

	public PotionEffectAction(@NotNull PotionEffectActionData data, @Nullable List<AbstractCondition> conditions) {
		super(ActionType.POTION_EFFECT, conditions);

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

		private int amplifier;

		private boolean ambient;

		private boolean particles;

		public PotionEffectActionData(@NotNull String value, int duration, int amplifier, boolean ambient,
				boolean particles) {
			Preconditions.checkArgument(value != null, "A PotionEffectActionData értéke nem lehet null!");
			Preconditions.checkArgument(duration >= 1, "Az effekt ideje nem lehet kevesebb, mint 1 másodperc!");
			Preconditions.checkArgument(amplifier >= 1, "Az effekt erőssége nem lehet kevesebb, mint 1!");

			this.value = value;
			this.duration = duration;
			this.amplifier = amplifier;
			this.ambient = ambient;
			this.particles = particles;
		}

		public @NotNull String getValue() {
			return this.value;
		}

		public int getDuration() {
			return this.duration;
		}

		public int getAmplifier() {
			return this.amplifier;
		}

		public boolean isAmbient() {
			return this.ambient;
		}

		public boolean hasParticles() {
			return this.particles;
		}

	}

}
