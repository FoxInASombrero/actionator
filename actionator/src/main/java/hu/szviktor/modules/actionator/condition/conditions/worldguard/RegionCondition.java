package hu.szviktor.modules.actionator.condition.conditions.worldguard;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import hu.szviktor.modules.actionator.condition.AbstractCondition;
import hu.szviktor.modules.actionator.condition.ConditionType;

public class RegionCondition extends AbstractCondition {

	@NotNull
	private RegionConditionData data;

	public RegionCondition(@NotNull RegionConditionData data) {
		super(ConditionType.IN_REGION);

		Preconditions.checkArgument(data != null, "A megadott RegionConditionData nem lehet null!");

		this.data = data;
	}

	public @NotNull RegionConditionData getData() {
		return this.data;
	}

	public class RegionConditionData {

		@NotNull
		private String world;

		@NotNull
		private String value;

		public RegionConditionData(@NotNull String world, @NotNull String value) {
			Preconditions.checkArgument(world != null, "A világ neve nem lehet null!");
			Preconditions.checkArgument(value != null, "A region értéke nem lehet null!");

			this.world = world;
			this.value = value;
		}

		public @NotNull String getWorld() {
			return this.world;
		}

		public @NotNull String getValue() {
			return this.value;
		}

	}

}
