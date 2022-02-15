package fr.filmo.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import fr.filmo.services.ServiceFilm;
import fr.filmo.servlets.ServletTools;
import fr.filmo.services.ServiceException;
import fr.filmo.services.ServiceFilm;


@WebServlet("/film")
public class FilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FilmServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			String idFilm = request.getParameter("idFilm");
			if(idFilm != null) {
				Long id = Long.parseLong(idFilm);
				if(id > 0) {
					responseContent = new ServiceFilm().find(id);
					responseContentType = "application/json";
				} else {
					responseStatus = 400;
					responseContent = "Erreur : L'idFilm doit être strictement supérieur � 0.";
				}
			} else {
				responseContent = new ServiceFilm().list();
				responseContentType = "application/json";
			}
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du paramètre idFilm n'est pas bon.";
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch(Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			JsonObject data = ServletTools.getJsonFromBuffer(request);
			
			new ServiceFilm().create(data);
			
		} catch(JsonSyntaxException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format des données n'est pas bon, veuillez utiliser du JSON.";
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch(Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			JsonObject data = ServletTools.getJsonFromBuffer(request);
			
			new ServiceFilm().update(data);
			
		} catch(JsonSyntaxException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format des donn�es n'est pas bon, veuillez utiliser du JSON.";
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch(Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String responseContent="", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			String idFilm = request.getParameter("idFilm");
			if(idFilm != null) {
				Long id = Long.parseLong(idFilm);
				if(id > 0) {
					new ServiceFilm().delete(id);
					responseContent = "Suppression Film OK.";
				} else {
					responseStatus = 400;
					responseContent = "Erreur : idFilm doit être strictement supérieur à 0.";
				}
			} else {
				responseStatus = 400;
				responseContent = "Erreur : Le paramètre idFilm est obligatoire.";
			}
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du paramètre idFilm n'est pas bon.";
		} catch(Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getMethod().equalsIgnoreCase("PATCH")){
			
			String responseContent="Ok", responseContentType = "text";
			int responseStatus = 200;
			
			try {
				String action = request.getParameter("action");
				if(action != null && (action.equals("addActeur") || action.equals("removeActeur"))) {
					String idFilm = request.getParameter("idFilm");
					String idActeur = request.getParameter("idActeur");
					if(idFilm != null && idActeur != null) {
						
						Long idFilmParse = Long.parseLong(idFilm);
						Long idActeurParse = Long.parseLong(idActeur);
						
						if(idFilmParse > 0 && idActeurParse > 0) {
							
							if(action.equals("addActeur")) {
								new ServiceFilm().addActeur(idFilmParse, idActeurParse);
								responseContent = "Le acteur a bien �t� ajout� au film.";
							} else {
								new ServiceFilm().removeActeur(idFilmParse, idActeurParse);
								responseContent = "Le acteur a bien �t� supprim� du film.";
							}
						} else {
							responseStatus = 400;
							responseContent = "Erreur : L'idFilm et l'idActeur doit �tre strictement sup�rieur � 0.";
						}
					} else {
						responseStatus = 400;
						responseContent = "Erreur : Le param�tre idFilm et idActeur est obligatoire.";
					}
				} else {
					responseStatus = 400;
					responseContent = "Erreur : Le param�tre action est obligatoire. 2 valeurs possibles : addActeur et removeActeur";
				}
			} catch(NumberFormatException e) {
				responseStatus = 400;
				responseContent = "Erreur : Le format du param�tre idFilm ou idActeur n'est pas bon.";
			} catch(ServiceException e) {
				responseStatus = 400;
				responseContent = "Erreur : " +e.getMessage();
			} catch(Exception e) {
				e.printStackTrace();
				responseStatus = 500;
				responseContent = "Erreur : Erreur serveur.";
			}
			
			ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);	
			
        } else {
            super.service(request, response);
        }
	}
	
}
