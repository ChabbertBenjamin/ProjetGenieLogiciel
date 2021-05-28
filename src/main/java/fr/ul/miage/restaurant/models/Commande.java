package fr.ul.miage.restaurant.models;

public class Commande {
	private int idcommande;
    private int idrepasclient;
    private int idplat;
    private String statut;
    //private date dateheurecommande;
    

    public Commande() {
    }
    
    public Commande(int idcommande, int idrepasclient, int idplat, String statut ) {
        
        this.idcommande = idcommande;
        this.idrepasclient = idrepasclient;
        this.idplat = idplat;
        this.statut = statut;
    }


	
	public int getIdcommande() {
		return idcommande;
	}

	public void setIdcommande(int idcommande) {
		this.idcommande = idcommande;
	}

	public int getIdrepasclient() {
		return idrepasclient;
	}

	public void setIdrepasclient(int idrepasclient) {
		this.idrepasclient = idrepasclient;
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

	@Override
    public String toString() {
        return "Commande{" + "id de la commande =" + idcommande + ", repas client =" + idrepasclient + ", plat=" + idplat + ", statut=" + statut + '}';
    }
    

}
