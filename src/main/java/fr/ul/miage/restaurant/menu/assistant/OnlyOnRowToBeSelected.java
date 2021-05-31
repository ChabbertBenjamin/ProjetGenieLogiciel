package fr.ul.miage.restaurant.menu.assistant;

import javax.swing.*;

public class OnlyOnRowToBeSelected extends DefaultListSelectionModel {
	
	public OnlyOnRowToBeSelected() {
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	@Override
	public void clearSelection() {
	}
	
	@Override
	public void removeSelectionInterval(int index0, int index1) {
	}
	
}