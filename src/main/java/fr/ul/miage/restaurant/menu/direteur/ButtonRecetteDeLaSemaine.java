package fr.ul.miage.restaurant.menu.direteur;

public class ButtonRecetteDeLaSemaine extends AbstractButtonRecette {
	
	public ButtonRecetteDeLaSemaine(InterfaceDirecteur parent) {
		super("Semaine", parent);
	}
	
	
	@Override
	protected String getMessage() {
		return "Le plat de la semaine est : {0}";
	}
	
	@Override
	protected String getQuery() {
		return "select count(*), p.nom from commande com inner join plat p on com.idplat =p.idplat\n"
				+ "  where DATE_PART('day', CURRENT_TIMESTAMP - com.dateheurecommande) <= 7"
				+ " group by p.idplat order by count(*) desc limit 1";
	}
}
