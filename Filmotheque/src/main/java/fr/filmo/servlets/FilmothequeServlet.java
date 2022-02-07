package fr.filmo.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.filmo.models.Acteur;
import fr.filmo.models.Film;
import fr.filmo.models.Realisateur;
import fr.filmo.models.Scenario;


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
	
	
			
			Scenario scenario = new Scenario(film1,"la guerre des michels","c'est la guerre");
			em.persist(scenario);
			
			
			film1 = new Film(realisateurA,scenario,"la guerre des michels");
			em.persist(film1);
			
			film2 = new Film(realisateurB,scenario,"la guerre des ");
			em.persist(film2);
			
			film3 = new Film(realisateurC,scenario,"la guerre");
			em.persist(film3);
			
			Acteur acteur = new Acteur("Jean","Michel",19);
			em.persist(acteur);
			
			acteur = em.find(Acteur.class, 1L);
			acteur.getFilms().add(film1);
			acteur.getFilms().add(film2);
			acteur.getFilms().add(film3);
			
//			Jouer jouer = new Jouer(film,acteur);
//			em.persist(jouer);
			
//			 Suppression 
//			scenario= em.find(Scenario.class, 1L);
//			em.remove(scenario);
			
			acteur = em.find(Acteur.class, 1L);
			for(Film associatedfilm : acteur.getFilms()){
				System.out.println(associatedfilm);
			}
			
			
			
			
			trans.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
				em=null;
			}
		}
	}
}
