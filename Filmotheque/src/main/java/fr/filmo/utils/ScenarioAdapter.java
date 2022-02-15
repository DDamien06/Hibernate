package fr.filmo.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.filmo.models.Scenario;

public class ScenarioAdapter implements JsonSerializer<Scenario>{

	@Override
	public JsonElement serialize(Scenario scenario, Type typeOfSrc, JsonSerializationContext context) {
		
		JsonObject jsonObject = new JsonObject();
		
		jsonObject.addProperty("id",scenario.getId());
		jsonObject.addProperty("nom",scenario.getTitre());
		
		if(scenario.getFilm() != null) {
			JsonObject filmJson = new JsonObject();
			filmJson.addProperty("id",scenario.getFilm().getId());
			filmJson.addProperty("titre",scenario.getFilm().getTitre());
			filmJson.addProperty("realisateur", scenario.getFilm().getRealisateur().getNom());
			jsonObject.add("film", filmJson);
		}
		
		return jsonObject;
	}

}
