package hu.szviktor.modules.actionator.condition;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ConditionTypeAdapter extends TypeAdapter<AbstractCondition> {

	@Override
	public AbstractCondition read(JsonReader in) throws IOException {
		in.beginObject();

		return null;
	}

	@Override
	public void write(JsonWriter out, AbstractCondition value) throws IOException {

	}
}
