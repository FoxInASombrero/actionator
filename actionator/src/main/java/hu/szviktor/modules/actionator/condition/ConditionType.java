package hu.szviktor.modules.actionator.condition;

import java.util.Arrays;
import java.util.HashMap;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public enum ConditionType {

	HAS_PERMISSION("has_permission"), HAS_MONEY("has_money"), IN_REGION("in_region");

	@NotNull
	private String name;

	@NotNull
	private static final HashMap<String, ConditionType> NAME_MAP = Maps.newHashMap();

	static {
		Arrays.stream(values()).forEach(value -> {
			NAME_MAP.put(value.getName(), value);
		});
	}

	private ConditionType(@NotNull String name) {
		Preconditions.checkArgument(name != null, "A név nem lehet null!");

		this.name = name;
	}

	public @NotNull String getName() {
		return this.name;
	}

	public static ConditionType findByName(@NotNull String name) {
		Preconditions.checkArgument(name != null, "A név nem lehet null!");

		if (!NAME_MAP.containsKey(name))
			return null;

		return NAME_MAP.get(name);
	}

}
