package fr.filmo.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name="film")
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JoinColumn(name="idRealisateur" , nullable = false)
	private Realisateur realisateur;
	
	@OneToOne(fetch=FetchType.LAZY)
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JoinColumn(name="idScenario", nullable = false)
	private Scenario scenario;

	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="jouer",
				joinColumns = @JoinColumn(name="idFilm"),
				inverseJoinColumns = @JoinColumn(name="idActeur"))
	private List<Acteur> acteurs = new ArrayList<>();
	
	@Column(nullable=false)
	private String titre;
	
	private String description;
	
	private Long dureeMinute;
	
	private String genre;
	
	public Film() {
	}

	public Film(Realisateur realisateur, Scenario scenario, String titre, String description, Long dureeMinute,
			String genre) {
		this.realisateur = realisateur;
		this.scenario = scenario;
		this.titre = titre;
		this.description = description;
		this.dureeMinute = dureeMinute;
		this.genre = genre;
	}

	public Film(Realisateur realisateur, Scenario scenario, String titre) {
		this.realisateur = realisateur;
		this.scenario = scenario;
		this.titre = titre;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Realisateur getRealisateur() {
		return realisateur;
	}

	public void setRealisateur(Realisateur realisateur) {
		this.realisateur = realisateur;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getDureeMinute() {
		return dureeMinute;
	}

	public void setDureeMinute(Long dureeMinute) {
		this.dureeMinute = dureeMinute;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	
	public List<Acteur> getActeurs() {
		return acteurs;
	}

	public void setActeurs(List<Acteur> acteurs) {
		this.acteurs = acteurs;
	}

	@Override
	public String toString() {
		return titre;
	}
	
	public void removeActeur(Acteur acteur) {
		this.acteurs.remove(acteur);
		acteur.getFilms().remove(this);
	}
	
	public void addActeur(Acteur acteur) {
		this.acteurs.add(acteur);
		acteur.getFilms().add(this);
	}
	
	@Override
    public boolean equals(Object obj) {
        if(obj instanceof Film) {
            return this.id == ((Film) obj).id;
        }
        return false;
    }
	
}
