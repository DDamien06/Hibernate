package fr.filmo.services;

import com.google.gson.JsonObject;

import fr.filmo.dao.DaoException;
import fr.filmo.dao.impl.DaoRealisateur;
import fr.filmo.models.Realisateur;
import fr.filmo.services.ServiceException;
import fr.filmo.services.ServiceTools;

public class ServiceRealisateur {
private DaoRealisateur dao;
	
	public ServiceRealisateur() {
		dao = new DaoRealisateur();
	}
	
	
	public String find(long id) throws ServiceException {
		Realisateur realisateur = dao.find(id);
		
		if(realisateur == null)
			throw new ServiceException("Le realisateur n'existe pas. Id : "+id);
		
		return ServiceTools.getSuperJson().toJson(realisateur);
	}

	
	public String list() throws ServiceException {
		return ServiceTools.getSuperJson().toJson(dao.list());	
	}
	
	public void create(JsonObject data) throws ServiceException {
		String nom = null, prenom = null, pays = null;
		int age= 0;
		
		try {
			nom = ServiceTools.getStringParameter(data, "nomRealisateur", 2, 255);	
			prenom = ServiceTools.getStringParameter(data, "prenomRealisateur", 3, 255);	
			age = ServiceTools.getIntParameter(data, "ageRealisateur", 1, 99);	
			pays = ServiceTools.getStringParameter(data, "paysRealisateur", 1, 255);	
			
			if(nom == null)
				throw new ServiceException("Le champ nomRealisateur est obligatoire.");
			
			if(prenom == null)
				throw new ServiceException("Le champ prenomRealisateur est obligatoire.");
			
			dao.create(new Realisateur(prenom, nom, age, pays));
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}

	public void update(JsonObject data) throws ServiceException {
		String nom = null, prenom = null, pays = null, id=null;
		int age= 0;
		
		try {
			id = ServiceTools.getStringParameter(data, "nomRealisateur", 2, 255);
			nom = ServiceTools.getStringParameter(data, "nomRealisateur", 2, 255);	
			prenom = ServiceTools.getStringParameter(data, "prenomRealisateur", 3, 255);	
			age = ServiceTools.getIntParameter(data, "ageRealisateur", 1, 99);	
			pays = ServiceTools.getStringParameter(data, "paysRealisateur", 1, 255);	
			
			if(id == null)
				throw new ServiceException("Le champ idRealisateur est obligatoire.");
			
			if(nom == null)
				throw new ServiceException("Le champ nomRealisateur est obligatoire.");
			
			if(prenom == null)
				throw new ServiceException("Le champ prenomRealisateur est obligatoire.");

			Realisateur realisateur = dao.find(Long.parseLong(id));
			if(realisateur == null)
				throw new ServiceException("Le realisateur n'existe pas. Id : "+id);
			
			realisateur.setNom(nom);
			realisateur.setPrenom(prenom);
			realisateur.setAge(age);
			realisateur.setPays(pays);
			
			dao.update(realisateur);
		} catch(NumberFormatException e) {
			throw new ServiceException("Le format du paramêtre idRealisateur n'est pas bon.");
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}

	public void delete(long id) throws ServiceException {
		try {
			dao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Le realisateur n'existe pas. Id : "+id);
		}
	}
}
