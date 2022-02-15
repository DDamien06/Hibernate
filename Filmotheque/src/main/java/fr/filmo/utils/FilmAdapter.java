package fr.filmo.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import fr.filmo.models.Acteur;
import fr.filmo.models.Film;

public class FilmAdapter implements JsonSerializer<Film> {

	@Override
	public JsonElement serialize(Film film, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		
		jsonObject.addProperty("id", film.getId());
		jsonObject.addProperty("titre", film.getTitre());
		jsonObject.addProperty("realisateur", film.getRealisateur().getNom());
		jsonObject.addProperty("scenario", film.getScenario().getDescription());
		
		JsonArray acteursJson = new JsonArray();
		JsonObject tmp;

		if(film.getActeurs() != null) {
			
			for(Acteur a : film.getActeurs()) {
				tmp = new JsonObject();
				tmp.addProperty("nom", a.getNom());
				tmp.addProperty("prenom", a.getPrenom());
				tmp.addProperty("age", a.getAge());
				acteursJson.add(tmp);
			}
			
			jsonObject.add("acteurs", acteursJson);
		}
		
		return jsonObject;
	}

}
