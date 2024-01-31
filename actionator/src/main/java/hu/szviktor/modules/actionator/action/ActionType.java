package hu.szviktor.modules.actionator.action;

import java.util.Arrays;
import java.util.HashMap;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public enum ActionType {

	RUN_COMMAND("run_command"), ECONOMY("economy"), POTION_EFFECT("potion_effect"), SEND_MESSAGE("send_message");

	@NotNull
	private String name;

	@NotNull
	private static final HashMap<String, ActionType> NAME_MAP = Maps.newHashMap();

	static {
		Arrays.stream(values()).forEach(value -> {
			NAME_MAP.put(value.getName(), value);
		});
	}

	private ActionType(@NotNull String name) {
		Preconditions.checkArgument(name != null, "A név nem lehet null!");

		this.name = name;
	}

	public @NotNull String getName() {
		return this.name;
	}

	public static ActionType findByName(@NotNull String name) {
		Preconditions.checkArgument(name != null, "A név nem lehet null!");

		if (!NAME_MAP.containsKey(name))
			return null;

		return NAME_MAP.get(name);
	}

}
