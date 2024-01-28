package hu.szviktor.modules.actionator.action;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ActionTypeAdapter extends TypeAdapter<AbstractAction> {

	@Override
	public AbstractAction read(JsonReader in) throws IOException {
		ActionType type = ActionType.SEND_MESSAGE;
		String value = "Érvénytelen beállítás!";

		in.beginObject();

		while (in.hasNext()) {
			String name = in.nextName();

			switch (name) {
			case "type": {
				type = ActionType.findByName(in.nextString());

				break;
			}

			case "value": {
				value = in.nextString();

				break;
			}
			}
		}

		in.endObject();

		return null; // TODO: teljesen újraírni az egészet.
	}

	@Override
	public void write(JsonWriter out, AbstractAction value) throws IOException {
		out.beginObject();

		out.name("type").value(value.getType().getName());
		out.name("value").value(""); // TODO: teljesen újraírni az egészet.

		out.endObject();
	}

}
