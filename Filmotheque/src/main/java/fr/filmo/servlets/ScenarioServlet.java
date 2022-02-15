package fr.filmo.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import fr.filmo.services.ServiceException;
import fr.filmo.services.ServiceScenario;

@WebServlet("/scenario")
public class ScenarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 public ScenarioServlet() {
	        super();
	    }

	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	
	    	String responseContent="Ok", responseContentType = "text";
			int responseStatus = 200;
			
			try {
				String idScenario = request.getParameter("idScenario");
				if(idScenario != null) {
					Long id = Long.parseLong(idScenario);
					if(id > 0) {
						responseContent = new ServiceScenario().find(id);
						responseContentType = "application/json";
					} else {
						responseStatus = 400;
						responseContent = "Erreur : L'idScenario doit �tre strictement sup�rieur � 0.";
					}
				} else {
					responseContent = new ServiceScenario().list();
					responseContentType = "application/json";
				}
			} catch(NumberFormatException e) {
				responseStatus = 400;
				responseContent = "Erreur : Le format du param�tre idScenario n'est pas bon.";
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
				
				new ServiceScenario().create(data);
				
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
		
		protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String responseContent="Ok", responseContentType = "text";
			int responseStatus = 200;
			
			try {
				JsonObject data = ServletTools.getJsonFromBuffer(request);
				
				new ServiceScenario().update(data);
				
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
				String idScenario = request.getParameter("idScenario");
				if(idScenario != null) {
					Long id = Long.parseLong(idScenario);
					if(id > 0) {
						new ServiceScenario().delete(id);
						responseContent = "Suppression auteur OK.";
					} else {
						responseStatus = 400;
						responseContent = "Erreur : idScenario doit �tre strictement sup�rieur � 0.";
					}
				} else {
					responseStatus = 400;
					responseContent = "Erreur : Le param�tre idScenario est obligatoire.";
				}
			} catch(ServiceException e) {
				responseStatus = 400;
				responseContent = "Erreur : " +e.getMessage();
			} catch(NumberFormatException e) {
				responseStatus = 400;
				responseContent = "Erreur : Le format du param�tre idScenario n'est pas bon.";
			} catch(Exception e) {
				e.printStackTrace();
				responseStatus = 500;
				responseContent = "Erreur : Erreur serveur.";
			}
			
			ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
		}
		
	}
