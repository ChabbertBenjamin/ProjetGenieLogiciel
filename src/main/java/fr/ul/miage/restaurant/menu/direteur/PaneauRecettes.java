package fr.ul.miage.restaurant.menu.direteur;


import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class PaneauRecettes extends JPanel {
	public PaneauRecettes() {
		setLayout(new BorderLayout());
		setBorder(new CompoundBorder(new EmptyBorder(4, 4, 4, 4),
				new MatteBorder(0, 0, 1, 0, Color.BLACK)));
		JLabel label = new JLabel("Recettes");
		JLabel empty = new JLabel("");
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		setLayout(new GridLayout(3, 1));
		add(label);
		add(empty);
	}
}