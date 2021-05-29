package testUnitaire;

import fr.ul.miage.restaurant.menu.InterfaceDirecteur;
import fr.ul.miage.restaurant.models.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.swing.JComboBox;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InterfaceDirecteurTest {

	@Mock
	InterfaceDirecteur service;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetData() {
		ArrayList<Stock> lancement1 = new ArrayList<Stock>();
		ArrayList<Stock> lancement2 = new ArrayList<Stock>();
		lancement2.add(new Stock("sucre", 5));
		when(service.getData()).thenReturn(lancement1, lancement2);

		ArrayList<Stock> s = service.getData();
		int resultat = s.size();
		assertEquals(0, resultat);
		assertNotNull(s);
		
		ArrayList<Stock> s2 = service.getData();
		int resultat2 = s2.size();
		assertEquals(1, resultat2);
		assertNotNull(s2);
		
		assertNotEquals(s, s2);
	}
	
	@Test
	public void testRemplirNomListEmploye() {
		JComboBox<String> lancement1 = new JComboBox<String>();
		JComboBox<String> lancement2 = new JComboBox<String>();
		lancement2.addItem("test");
		when(service.remplirNomListEmploye()).thenReturn(lancement1,lancement2);
		
		JComboBox<String> s = service.remplirNomListEmploye();
		int resultat = s.getItemCount();
		assertEquals(0, resultat);
		assertNotNull(s);

		JComboBox<String> s2 = service.remplirNomListEmploye();
		int resultat2 = s2.getItemCount();
		assertEquals(1, resultat2);
		assertNotNull(s2);
		
		assertNotEquals(s, s2);

	}
	
	@Test
	public void testGetListMatierePremiere() {
		JComboBox<String> lancement1 = new JComboBox<String>();
		when(service.getListMatierePremiere(lancement1)).thenReturn(lancement1);
		JComboBox<String> s = service.getListMatierePremiere(lancement1);
		int resultat = s.getItemCount();
		assertEquals(0, resultat);
		assertNotNull(s);
		
		JComboBox<String> lancement2 = new JComboBox<String>();
		lancement2.addItem("test");
		when(service.getListMatierePremiere(lancement2)).thenReturn(lancement2);
		JComboBox<String> s2 = service.getListMatierePremiere(lancement2);
		int resultat2 = s2.getItemCount();
		assertEquals(1, resultat2);
		assertNotNull(s2);
		
		assertNotEquals(s, s2);
	}
	
	
	
	
	
	
	

}
