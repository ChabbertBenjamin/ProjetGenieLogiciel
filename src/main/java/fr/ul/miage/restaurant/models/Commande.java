package fr.ul.miage.restaurant.models;

import java.time.LocalDate;
import java.util.Date;

public class Commande {
	private int idcommande;
	private int idplat;
	private String statut;
	private boolean menuenfant;
	private LocalDate dateheurecommande;
	private int idrepasclient;
	public int getIdcommande() {
		return idcommande;
	}
	public void setIdcommande(int idcommande) {
		this.idcommande = idcommande;
	}
	public int getIdplat() {
		return idplat;
	}
	public void setIdplat(int idplat) {
		this.idplat = idplat;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public boolean isMenuenfant() {
		return menuenfant;
	}
	public void setMenuenfant(boolean menuenfant) {
		this.menuenfant = menuenfant;
	}
	public LocalDate getDateheurecommande() {
		return dateheurecommande;
	}
	public void setDateheurecommande(LocalDate dateheurecommande) {
		this.dateheurecommande = dateheurecommande;
	}
	public int getIdrepasclient() {
		return idrepasclient;
	}
	public void setIdrepasclient(int idrepasclient) {
		this.idrepasclient = idrepasclient;
	}
	public Commande(int idcommande, int idplat, String statut, boolean menuenfant, LocalDate dateheurecommande,
			int idrepasclient) {
		super();
		this.idcommande = idcommande;
		this.idplat = idplat;
		this.statut = statut;
		this.menuenfant = menuenfant;
		this.dateheurecommande = dateheurecommande;
		this.idrepasclient = idrepasclient;
	}
	public Commande(int idplat, String statut, boolean menuenfant, LocalDate dateheurecommande, int idrepasclient) {
		super();
		this.idplat = idplat;
		this.statut = statut;
		this.menuenfant = menuenfant;
		this.dateheurecommande = dateheurecommande;
		this.idrepasclient = idrepasclient;
	}
	@Override
	public String toString() {
		return "Commande [idcommande=" + idcommande + ", idplat=" + idplat + ", statut=" + statut + ", menuenfant="
				+ menuenfant + ", dateheurecommande=" + dateheurecommande + ", idrepasclient=" + idrepasclient + "]";
	}
	
	
	
	
}
