package fr.filmo.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.filmo.models.Acteur;
import fr.filmo.models.Film;
import fr.filmo.models.Realisateur;
import fr.filmo.models.Scenario;
import fr.filmo.utils.ActeurAdapter;
import fr.filmo.utils.FilmAdapter;


@WebServlet("/filmotheque")
public class FilmothequeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction trans = null;
		Film film1 = null;
		Film film2 = null;
		Film film3 = null;
		
		
		try {
			emf = Persistence.createEntityManagerFactory("Filmotheque");
			em = emf.createEntityManager();
			
			trans = em.getTransaction();
			trans.begin();
			
			Realisateur realisateurA = new Realisateur("Michel","jean", 20, "France");
			em.persist(realisateurA);
			
			Realisateur realisateurB = new Realisateur("Mich","jeannot", 20, "France");
			em.persist(realisateurB);
			
			Realisateur realisateurC = new Realisateur("Mi","j", 20, "France");
			em.persist(realisateurC);
	
	
			Scenario scenario1 = new Scenario(film1,"la guerre des michels","c'est la guerre");
			em.persist(scenario1);
			
			Scenario scenario2 = new Scenario(film2,"la guerre des michels","c'est la guerre");
			em.persist(scenario2);
			
			Scenario scenario3 = new Scenario(film3,"la guerre des michels","c'est la guerre");
			em.persist(scenario3);
			
			
			film1 = new Film(realisateurA,scenario1,"la guerre des michels");
			em.persist(film1);
			
			film2 = new Film(realisateurB,scenario2,"la guerre des ");
			em.persist(film2);
			
			film3 = new Film(realisateurC,scenario3,"la guerre");
			em.persist(film3);
			
			Acteur acteur = new Acteur("Jean","Michel",19);
			em.persist(acteur);
			
			acteur = em.find(Acteur.class, 1L);
			acteur.addFilm(film1);
			acteur.addFilm(film2);
			acteur.addFilm(film3);
			
			
//			Jouer jouer = new Jouer(film,acteur);
//			em.persist(jouer);
			
//			 Suppression 
//			scenario= em.find(Scenario.class, 1L);
//			em.remove(scenario);
			
			acteur.removeFilm(film2); 
		
			
			
			
			
			trans.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
				em=null;
			}
		}
		
		em = emf.createEntityManager();
		
		List<Film> films = em.createQuery("From Film Order by id", Film.class).getResultList(); // getResultList = resultSet / getSingleResulte pour un élément seul
		List<Acteur> acteurs = em.createQuery("From Acteur Order by id", Acteur.class).getResultList(); //typeQuery = prepared statement
		
		GsonBuilder gsonBuilder = new GsonBuilder() // améliore le Gson pour créer des json
				.registerTypeAdapter(Film.class, new FilmAdapter()) // se fait en une ligne. On récupère l'adapter (qui est une classe) pour la classe donnée
				.registerTypeAdapter(Acteur.class, new ActeurAdapter());// typeadapter(la classe pour laquelle il faut faire autre chose, la méthode à utiliser)
		
		Gson gson = gsonBuilder.create(); // on crée ce super builder
		
		JsonElement acteursJson= gson.toJsonTree(acteurs); // on créer nos json grace à ce superbuilder
		JsonElement filmsJson= gson.toJsonTree(films);
		
		JsonObject json = new JsonObject();
		json.add("acteurs", acteursJson);
		json.add("films", filmsJson);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(acteursJson.toString());
		
		if(em!=null) {
			em.close();
		}
		if (emf != null ) {
			emf.close();
		}
	}
}
