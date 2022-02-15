package fr.filmo.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import fr.filmo.models.Acteur;
import fr.filmo.models.Film;

public class ActeurAdapter implements JsonSerializer<Acteur> {

	@Override
	public JsonElement serialize(Acteur acteur, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		
		jsonObject.addProperty("id",acteur.getId());
		jsonObject.addProperty("nom",acteur.getNom());
		jsonObject.addProperty("prenom",acteur.getPrenom());
		
		JsonArray filmsJson = new JsonArray();
		JsonObject tmp;
		if(acteur.getFilms() != null) {
			
			for(Film f : acteur.getFilms()) {
				tmp = new JsonObject();
				tmp.addProperty("id", f.getId());
				tmp.addProperty("titre", f.getTitre());
				tmp.addProperty("realisateur", f.getRealisateur().getNom());
				tmp.addProperty("scenario", f.getScenario().getDescription());
				filmsJson.add(tmp);
			}
			
		}
		jsonObject.add("films", filmsJson);
	
		return jsonObject;
	}

}
