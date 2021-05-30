package fr.ul.miage.restaurant.models;

public class Plat {
	private int idplat;
    private int idcategorie;
    private int prix;
    private String nom;
	public int getIdplat() {
		return idplat;
	}
	public void setIdplat(int idplat) {
		this.idplat = idplat;
	}
	public int getIdcategorie() {
		return idcategorie;
	}
	public void setIdcategorie(int idcategorie) {
		this.idcategorie = idcategorie;
	}
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Plat(int idplat, int idcategorie, int prix, String nom) {
		super();
		this.idplat = idplat;
		this.idcategorie = idcategorie;
		this.prix = prix;
		this.nom = nom;
	}
	@Override
	public String toString() {
		return nom + " / "+prix+" €";
	}
    

    
    
}
