package fr.ul.miage.restaurant.menu.directeur;

public class ButtonRecetteDuMois extends AbstractButtonRecette {
	
	public ButtonRecetteDuMois(InterfaceDirecteur parent) {
		super("Mois", parent);
	}
	
	@Override
	protected String getMessage() {
		return "Le plat du mois est : {0}";
	}
	
	@Override
	protected String getQuery() {
		return  "select count(*), p.nom from commande com inner join plat p on com.idplat =p.idplat\n"
				+ "  where DATE_PART('day', CURRENT_TIMESTAMP - com.dateheurecommande) <= 30"
				+ " group by p.idplat order by count(*) desc limit 1";
	}
}
