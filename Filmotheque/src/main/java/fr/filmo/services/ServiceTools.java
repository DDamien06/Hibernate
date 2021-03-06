package fr.filmo.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import fr.filmo.models.Acteur;
import fr.filmo.models.Film;
import fr.filmo.models.Realisateur;
import fr.filmo.models.Scenario;
import fr.filmo.utils.ActeurAdapter;
import fr.filmo.utils.FilmAdapter;
import fr.filmo.utils.RealisateurAdapter;
import fr.filmo.utils.ScenarioAdapter;



public class ServiceTools {

	public static String getStringParameter(JsonObject data, String nameField, int minLength, int maxLength) throws ServiceException {
		String parameter = null;
		
		if(data.get(nameField) != null && !data.get(nameField).isJsonNull()) {
			parameter = data.get(nameField).getAsString().trim();
			if(parameter.length() < minLength ) {
				throw new ServiceException("Le champ "+nameField+" doit contenir au moins "+minLength+" caractères.");
			}
			if(parameter.length() > maxLength ) {
				throw new ServiceException("Le champ "+nameField+" doit contenir au maximum "+maxLength+" caractères.");
			}
		}
		
		return parameter;
	}
	
	public static int getIntParameter(JsonObject data, String nameField, int minValue, int maxValue) throws ServiceException {
		int parameter = 0;
		
		if(data.get(nameField) != null && !data.get(nameField).isJsonNull()) {
			parameter = data.get(nameField).getAsInt();
			if(parameter < minValue ) {
				throw new ServiceException("Le champ "+nameField+" doit être avoir une valeur supérieur à"+minValue);
			}
			if(parameter > maxValue ) {
				throw new ServiceException("Le champ "+nameField+" doit être avoir une valeur supérieur à"+maxValue);
			}
		}
		
		return parameter;
	}
	
	public static String getStringParameter(JsonObject data, String nameField, int minLength, int maxLength, String regexFormat) throws ServiceException {
		String parameter = getStringParameter(data, nameField, minLength, maxLength);
		
		if(parameter != null) {
			if(!parameter.matches(regexFormat)) {
				throw new ServiceException("Le champ "+nameField+" n'a pas un format valide (Regex : "+regexFormat+").");
			}
		}
		
		return parameter;
	}
	
	public static Gson getSuperJson() {
		GsonBuilder gsonBuilder = new GsonBuilder()
				.registerTypeAdapter(Acteur.class, new ActeurAdapter())
				.registerTypeAdapter(Film.class, new FilmAdapter())
				.registerTypeAdapter(Realisateur.class, new RealisateurAdapter())
				.registerTypeAdapter(Scenario.class, new ScenarioAdapter())
				.serializeNulls();
		return gsonBuilder.create();		
	}
	
}
