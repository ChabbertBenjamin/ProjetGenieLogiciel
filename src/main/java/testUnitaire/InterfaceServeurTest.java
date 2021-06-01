package testUnitaire;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.ul.miage.restaurant.menu.InterfaceServeur;

import fr.ul.miage.restaurant.models.Stock;
import fr.ul.miage.restaurant.models.Table;

@ExtendWith(MockitoExtension.class)
public class InterfaceServeurTest {

	@Mock
	InterfaceServeur service;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getDataTest() {
		String[][] lancement1 = null;
		Table t = new Table(5, "sale", 4, 1, 4);
		when(service.getDataRepas(t)).thenReturn(lancement1);
		String[][] s = service.getDataRepas(t);
		assertNull(s);
		
		String[][] lancement2 = new String[1][5];
		Table t2 = new Table(6, "propre", 2, 1, 4);
		lancement2[0][0] = "frite";
		lancement2[0][1] = "15";
		lancement2[0][2] = "2021-05-30 12:00:00";
		lancement2[0][3] = "false";
		lancement2[0][4] = "15";
		when(service.getDataRepas(t2)).thenReturn(lancement2);
		String[][] s2 = service.getDataRepas(t2);
		assertNotNull(s2);
		assertEquals("frite", lancement2[0][0]);
		assertEquals("15", lancement2[0][1]);
		assertEquals("2021-05-30 12:00:00", lancement2[0][2]);
		assertEquals("false", lancement2[0][3]);
		assertEquals("15", lancement2[0][4]);
	}
	
	@Test
	public void retourIndexButtonTest() {
		ArrayList<JButton> buttonList = new ArrayList<JButton>();
		JButton button = new JButton();
		when(service.retourIndexButton(buttonList, button)).thenReturn(0);
		
		int x = service.retourIndexButton(buttonList, button);
		assertEquals(0, x);
		
		JButton button2 = new JButton();
		buttonList.add(button);
		buttonList.add(button2);
		
		when(service.retourIndexButton(buttonList, button2)).thenReturn(1);
		
		int x2 = service.retourIndexButton(buttonList, button2);
		assertEquals(1, x2);
	}

	
	
	

}
