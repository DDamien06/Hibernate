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
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;


@Entity
@Table(name="acteur")
public class Acteur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String nom;
	
	@Column(nullable=false)
	private String prenom;

	@ManyToMany(mappedBy = "acteurs", fetch=FetchType.LAZY)
	private List<Film> films = new ArrayList<>();
	
	private int age;
	
	@PreRemove
	private void preRemove() {
		for(Film f : this.films) {
			f.getActeurs().remove(this);
		}
	}
	
	public Acteur() {
	}
	
	public Acteur(String nom, String prenom, int age) {
		this.nom=nom;
		this.prenom=prenom;
		this.age=age;
	}
	

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Film> getFilms() {
		return films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}
	
	public void addFilm(Film film) {
		this.films.add(film);
		film.getActeurs().add(this);
	}
	
	public void removeFilm(Film film) {
		this.films.remove(film);
		film.getActeurs().remove(this);
	}
	
	@Override
    public boolean equals(Object obj) {
        if(obj instanceof Acteur) {
            return this.id == ((Acteur) obj).id;
        }
        return false;
    }
}
