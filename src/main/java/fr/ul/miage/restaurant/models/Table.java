package fr.ul.miage.restaurant.models;

public class Table {
	private int idtable;
	private String statut;
	private int nbcouverts;
	private int etage;
	private int idemploye;

	public int getIdtable() {
		return idtable;
	}

	public void setIdtable(int idtable) {
		this.idtable = idtable;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public int getNbcouverts() {
		return nbcouverts;
	}

	public void setNbcouverts(int nbcouverts) {
		this.nbcouverts = nbcouverts;
	}

	public int getEtage() {
		return etage;
	}

	public void setEtage(int etage) {
		this.etage = etage;
	}

	public int getIdemploye() {
		return idemploye;
	}

	public void setIdemploye(int idemploye) {
		this.idemploye = idemploye;
	}

	public Table(int idtable, String statut, int nbcouverts, int etage, int idemploye) {
		super();
		this.idtable = idtable;
		this.statut = statut;
		this.nbcouverts = nbcouverts;
		this.etage = etage;
		this.idemploye = idemploye;
	}

	@Override
	public String toString() {
		return "Table [idtable=" + idtable + ", statut=" + statut + ", nbcouverts=" + nbcouverts + ", etage=" + etage
				+ ", idemploye=" + idemploye + "]";
	}

}
