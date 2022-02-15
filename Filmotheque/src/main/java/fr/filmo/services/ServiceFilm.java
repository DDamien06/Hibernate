package fr.filmo.services;

import com.google.gson.JsonObject;

import fr.filmo.models.Acteur;
import fr.filmo.services.ServiceException;
import fr.filmo.dao.DaoException;
import fr.filmo.dao.impl.DaoActeur;
import fr.filmo.dao.impl.DaoFilm;
import fr.filmo.dao.impl.DaoRealisateur;
import fr.filmo.dao.impl.DaoScenario;
import fr.filmo.models.Film;
import fr.filmo.models.Realisateur;
import fr.filmo.models.Scenario;

public class ServiceFilm {
	
	private DaoFilm dao;
	private DaoRealisateur daoRealisateur;
	private DaoActeur daoActeur;
	private DaoScenario daoScenario;
	
	public ServiceFilm() {
		dao = new DaoFilm();
		daoActeur = new DaoActeur();
		daoRealisateur = new DaoRealisateur();
		daoScenario = new DaoScenario();
	}
	
	public String find(long id) throws ServiceException {
		Film film = dao.find(id);
		
		if(film == null)
			throw new ServiceException("Le film n'existe pas. Id : "+id);
		
		return ServiceTools.getSuperJson().toJson(film);
	}

	
	public String list() throws ServiceException {
		return ServiceTools.getSuperJson().toJson(dao.list());	
	}
	
	public void create(JsonObject data) throws ServiceException {
		String titre = null, idScenario = null, idRealisateur= null;
		Realisateur realisateur= null;
		Scenario scenario = null;
		
		
		try {
			titre = ServiceTools.getStringParameter(data, "titre", 2, 255);	
			idRealisateur = ServiceTools.getStringParameter(data, "idRealisateur", 0, 50, "^\\d+$");
			idScenario = ServiceTools.getStringParameter(data, "idScenario", 0, 50, "^\\d+$");	
			
			if(titre == null)
				throw new ServiceException("Le champ titreFilm est obligatoire.");
			
			if(idRealisateur != null) {
				realisateur= daoRealisateur.find(Long.parseLong(idRealisateur));
				if(realisateur==null)
					throw new ServiceException("Le réalisateur n'existe pas. Id : "+idRealisateur);
			}else {
				throw new ServiceException("Le champ idRealisateur est obligatoire.");
			}
			
			if(idScenario !=null) {
				scenario = daoScenario.find(Long.parseLong(idScenario));
				if(scenario==null)
					throw new ServiceException("Le scénario n'existe pas. Id : "+idScenario);
				
				if(scenario.getFilm() !=null)
					throw new ServiceException("La scénario est déja associé au film d'id : "+scenario.getFilm().getId());
			}else {
				throw new ServiceException("Le champ idScenario est obligatoire.");
			}
			
			dao.create(new Film(realisateur,scenario,titre));
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}

	public void update(JsonObject data) throws ServiceException {
		String titre = null, idScenario= null, idRealisateur = null, id=null, description = null, dureeMinute=null;
		Realisateur realisateur = null;
		Scenario scenario = null;
		Long dureeMinuteLong = null;
		
		try {
			id = ServiceTools.getStringParameter(data, "idFilm", 2, 255);
			titre = ServiceTools.getStringParameter(data, "titre", 2, 255);	
			idRealisateur = ServiceTools.getStringParameter(data, "idRealisateur", 0, 50, "^\\d+$");
			idScenario = ServiceTools.getStringParameter(data, "idScenario", 0, 50, "^\\d+$");
			description = ServiceTools.getStringParameter(data, "description", 0, 500);
			dureeMinute = ServiceTools.getStringParameter(data, "dureeMinute", 0, 50, "^\\d+$");
			
			if(titre == null)
				throw new ServiceException("Le champ titreFilm est obligatoire.");
			
			if(idRealisateur != null) {
				realisateur= daoRealisateur.find(Long.parseLong(idRealisateur));
				if(realisateur==null)
					throw new ServiceException("Le réalisateur n'existe pas. Id : "+idRealisateur);
			}else {
				throw new ServiceException("Le champ idRealisateur est obligatoire.");
			}
			
			if(idScenario !=null) {
				scenario = daoScenario.find(Long.parseLong(idScenario));
				if(scenario==null)
					throw new ServiceException("Le scénario n'existe pas. Id : "+idScenario);
				
				if(scenario.getFilm() !=null)
					throw new ServiceException("La scénario est déja associé au film d'id : "+scenario.getFilm().getId());
			}else {
				throw new ServiceException("Le champ idScenario est obligatoire.");
			}
			
			if(dureeMinute !=null)
				dureeMinuteLong = Long.parseLong(dureeMinute);				
			
			Film film = dao.find(Long.parseLong(id));
			if(film == null)
				throw new ServiceException("Le film n'existe pas. Id : "+id);
			
			film.setTitre(titre);
			film.setRealisateur(realisateur);
			film.setScenario(scenario);
			film.setDescription(description);
			film.setDureeMinute(dureeMinuteLong);
			
			dao.update(film);
		} catch(NumberFormatException e) {
			throw new ServiceException("Le format du paramêtre idFilm n'est pas bon.");
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}

	public void delete(long id) throws ServiceException {
		try {
			dao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Le film n'existe pas. Id : "+id);
		}
	}
	
	public void addActeur(long idFilm, long idActeur) throws ServiceException {
		Film film = dao.find(idFilm);
		if(film == null)
			throw new ServiceException("Le film n'existe pas. Id : "+idFilm);
		
		Acteur acteur = daoActeur.find(idActeur);
		if(acteur == null)
			throw new ServiceException("Le acteur n'existe pas. Id : "+idActeur);
		
		if(film.getActeurs().contains(acteur))
			throw new ServiceException("Le acteur est déja associé au film.");
		
		try {
			film.addActeur(acteur);
			dao.update(film);
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
	public void removeActeur(long idFilm, long idActeur) throws ServiceException {
		Film film = dao.find(idFilm);
		if(film == null)
			throw new ServiceException("Le film n'existe pas. Id : "+idFilm);
		
		Acteur acteur = daoActeur.find(idActeur);
		if(acteur == null)
			throw new ServiceException("Le acteur n'existe pas. Id : "+idActeur);
		
		if(!film.getActeurs().contains(acteur))
			throw new ServiceException("l'acteur n'est pas associé au film.");
		
		try {
			film.removeActeur(acteur);
			dao.update(film);

		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
}
