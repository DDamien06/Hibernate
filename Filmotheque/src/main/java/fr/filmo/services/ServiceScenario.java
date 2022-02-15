package fr.filmo.services;

import com.google.gson.JsonObject;

import fr.filmo.services.ServiceException;
import fr.filmo.services.ServiceTools;
import fr.filmo.dao.DaoException;
import fr.filmo.dao.impl.DaoFilm;
import fr.filmo.dao.impl.DaoScenario;
import fr.filmo.models.Film;
import fr.filmo.models.Scenario;

public class ServiceScenario {
	private DaoScenario dao;
	private DaoFilm daoFilm;

	
	public ServiceScenario() {
		dao=new DaoScenario();
		daoFilm = new DaoFilm();
	}
	
	public String find(long id) throws ServiceException {
		Scenario scenario = dao.find(id);
		
		if(scenario == null)
			throw new ServiceException("Le scenario n'existe pas. Id : "+id);
		
		return ServiceTools.getSuperJson().toJson(scenario);
	}
	
	public String list() throws ServiceException {
		return ServiceTools.getSuperJson().toJson(dao.list());	
	}
	
	public void create(JsonObject data) throws ServiceException {
		String titre=null, idFilm=null, description=null;
		Film film = null;
		
		try {
			titre = ServiceTools.getStringParameter(data, "titreScenario", 2, 255);	
			description = ServiceTools.getStringParameter(data, "description", 2, 255);
			idFilm = ServiceTools.getStringParameter(data, "idFilm", 0, 50, "^\\d+$");

			if(titre == null)
				throw new ServiceException("Le champ titreScenario est obligatoire.");
			
			if(description == null)
				throw new ServiceException("Le champ description est obligatoire.");
			
			if(idFilm != null) {
				film = daoFilm.find(Long.parseLong(idFilm));
				if(film == null)
					throw new ServiceException("Le film n'existe pas. Id : "+idFilm);
				
				if(film.getScenario() != null)
					throw new ServiceException("Le film est déja associé au scénario d'id : "+film.getScenario().getId());
			}else {
				throw new ServiceException("Le champ idFilm est obligatoire.");
			}
			
			dao.create(new Scenario(film,titre,description));
			
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
	public void update(JsonObject data) throws ServiceException {
		String titre=null, idFilm=null, description=null, id=null;
		Film film = null;
		
		try {
			id = ServiceTools.getStringParameter(data, "idScenario", 0, 900, "^\\d+$");
			titre = ServiceTools.getStringParameter(data, "titreScenario", 2, 255);	
			description = ServiceTools.getStringParameter(data, "description", 2, 255);
			idFilm = ServiceTools.getStringParameter(data, "idFilm", 0, 50, "^\\d+$");

			if(id == null)
				throw new ServiceException("Le champ idScenario est obligatoire.");
			
			if(titre == null)
				throw new ServiceException("Le champ titreScenario est obligatoire.");
			
			if(description == null)
				throw new ServiceException("Le champ description est obligatoire.");
			
			Scenario scenario = dao.find(Long.parseLong(id));
			if(scenario==null)
				throw new ServiceException("Le scenario n'existe pas. Id : "+id);
			
			if(idFilm != null) {
				film = daoFilm.find(Long.parseLong(idFilm));
				if(film == null)
					throw new ServiceException("Le film n'existe pas. Id : "+idFilm);
				
				if(film.getScenario() != null)
					throw new ServiceException("Le film est déja associé au scénario d'id : "+film.getScenario().getId());
			}else {
				throw new ServiceException("Le champ idFilm est obligatoire.");
			}
			

			dao.update(new Scenario(film,titre,description));
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
	public void delete(long id) throws ServiceException {
		try {
			dao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Le scenario n'existe pas. Id : "+id);
		}
	}
}
