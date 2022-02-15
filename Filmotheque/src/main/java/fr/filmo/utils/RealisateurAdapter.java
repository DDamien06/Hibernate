package fr.filmo.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import fr.filmo.models.Film;
import fr.filmo.models.Realisateur;

public class RealisateurAdapter implements JsonSerializer<Realisateur> {

	@Override
	public JsonElement serialize(Realisateur r, Type arg1, JsonSerializationContext arg2) {
		JsonObject json = new JsonObject();
		
		json.addProperty("id", r.getId());
		json.addProperty("nom",r.getNom());
		json.addProperty("prenom",r.getPrenom());
		
		JsonArray filmJson = new JsonArray();
		JsonObject filmJ;
		
		if(r.getFilms() != null) {
			for(Film film : r.getFilms()) {
				filmJ = new JsonObject();
				filmJ.addProperty("titre", film.getTitre());
				filmJson.add(filmJ);
			}		
		}

		json.add("film", filmJson);

		return json;
	}
}
