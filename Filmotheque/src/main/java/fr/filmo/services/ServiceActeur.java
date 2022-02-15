package fr.filmo.services;

import fr.filmo.models.Acteur;
import fr.filmo.models.Film;
import fr.filmo.services.ServiceException;
import fr.filmo.services.ServiceTools;
import com.google.gson.JsonObject;
import fr.filmo.dao.DaoException;
import fr.filmo.dao.impl.DaoActeur;
import fr.filmo.dao.impl.DaoFilm;
import fr.filmo.models.Acteur;


public class ServiceActeur {

	private DaoActeur dao;
	private DaoFilm daoFilm;
	
	public ServiceActeur() {
		dao=new DaoActeur();
		daoFilm=new DaoFilm();
	}
	
	public String find(long id) throws ServiceException {
		Acteur acteur = dao.find(id);
		
		if(acteur == null)
			throw new ServiceException("L'acteur n'existe pas. Id : "+id);
		
		return ServiceTools.getSuperJson().toJson(acteur);
	}
	
	public String list() throws ServiceException {
		return ServiceTools.getSuperJson().toJson(dao.list());	
	}
	
	public void create(JsonObject data) throws ServiceException {
		String nom = null, prenom = null;
		int age=0;
		
		try {
			nom = ServiceTools.getStringParameter(data, "nom", 2, 255);	
			prenom = ServiceTools.getStringParameter(data, "prenom", 2, 255);
			age = ServiceTools.getIntParameter(data, "age", 1, 99);	

			if(nom == null)
				throw new ServiceException("Le champ nomActeur est obligatoire.");
			
			if(prenom == null)
				throw new ServiceException("Le champ prenomActeur est obligatoire.");

			dao.create(new Acteur(nom,prenom,age));
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
	public void update(JsonObject data) throws ServiceException {
		String nom = null, prenom = null, id=null;
		int age=0;
		
		try {
			id = ServiceTools.getStringParameter(data, "idActeur", 0, 900, "^\\d+$");
			nom = ServiceTools.getStringParameter(data, "nom", 2, 255);	
			prenom = ServiceTools.getStringParameter(data, "prenom", 2, 255);
			age = ServiceTools.getIntParameter(data, "age", 1, 99);	

			if(id == null)
				throw new ServiceException("Le champ idActeur est obligatoire.");
			
			if(nom == null)
				throw new ServiceException("Le champ nomActeur est obligatoire.");
			
			if(prenom == null)
				throw new ServiceException("Le champ prenomActeur est obligatoire.");
			
			Acteur acteur = dao.find(Long.parseLong(id));
			if(acteur==null)
				throw new ServiceException("L'acteur n'existe pas. Id : "+id);

			dao.update(new Acteur(nom,prenom,age));
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
	public void delete(long id) throws ServiceException {
		try {
			dao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("L'acteur n'existe pas. Id : "+id);
		}
	}
	
	public void addFilm(long idActeur, long idFilm) throws ServiceException {
		Acteur acteur= dao.find(idActeur);
		if(acteur == null)
			throw new ServiceException("L'acteur n'existe pas. Id : "+idActeur);
		
		Film film = daoFilm.find(idFilm);
		if(film == null)
			throw new ServiceException("Le film n'existe pas. Id : "+idFilm);
		
		if(acteur.getFilms().contains(film))
			throw new ServiceException("Le film est déja associé à l'acteur.");
		
		try {
			acteur.addFilm(film);
			dao.update(acteur);
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
	public void removeFilm(long idActeur, long idFilm) throws ServiceException {
		Acteur acteur= dao.find(idActeur);
		if(acteur == null)
			throw new ServiceException("L'acteur n'existe pas. Id : "+idActeur);
		
		Film film = daoFilm.find(idFilm);
		if(film == null)
			throw new ServiceException("Le film n'existe pas. Id : "+idFilm);
		
		if(acteur.getFilms().contains(film))
			throw new ServiceException("Le film est déja associé à l'acteur.");
		
		try {
			acteur.removeFilm(film);
			dao.update(acteur);
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
}
