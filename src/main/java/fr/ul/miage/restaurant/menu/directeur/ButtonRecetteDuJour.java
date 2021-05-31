package fr.ul.miage.restaurant.menu.directeur;

public class ButtonRecetteDuJour extends AbstractButtonRecette {
	
	public ButtonRecetteDuJour(InterfaceDirecteur parent) {
		super("Jour", parent);
	}
	
	@Override
	protected String getMessage() {
		return "Le plat du jour est : {0}";
	}
	
	@Override
	protected String getQuery() {
		return "select count(*), p.nom from commande com inner join plat p on com.idplat =p.idplat\n"
				+ " where DATE(com.dateheurecommande) = CURRENT_DATE"
				+ " group by p.idplat order by count(*) desc limit 1";
	}
}
