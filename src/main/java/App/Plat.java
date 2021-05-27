package App;

public class Plat {
	private int idplat;
    private int idcategorie;
    private int prix;
    private int idmatierepremiere;
    

    public Plat() {
    }
    
    public Plat(int idplat, int idcategorie, int prix, int idmatierepremiere) {
        
        this.idplat = idplat;
        this.idcategorie = idcategorie;
        this.prix = prix;
        this.idcategorie = idcategorie;
    }

	
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

	public int getIdmatierepremiere() {
		return idmatierepremiere;
	}

	public void setIdmatierepremiere(int idmatierepremiere) {
		this.idmatierepremiere = idmatierepremiere;
	}

	@Override
    public String toString() {
        return "Plat{" + "id" + idplat + ", categorie=" + idcategorie + ", prix=" + prix + ", matiere premiere= " + idmatierepremiere + '}';
    }
}
