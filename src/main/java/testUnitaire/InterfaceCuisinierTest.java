package testUnitaire;

import fr.ul.miage.restaurant.menu.InterfaceCuisinier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import javax.swing.JComboBox;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InterfaceCuisinierTest {

	@Mock
	InterfaceCuisinier service;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetListPlat() {
		JComboBox<String> lancement1 = new JComboBox<String>();
		JComboBox<String> lancement2 = new JComboBox<String>();
		lancement2.addItem("test");
		when(service.getListPlat()).thenReturn(lancement1,lancement2);
		
		JComboBox<String> s = service.getListPlat();
		int resultat = s.getItemCount();
		assertEquals(0, resultat);
		assertNotNull(s);

		JComboBox<String> s2 = service.getListPlat();
		int resultat2 = s2.getItemCount();
		assertEquals(1, resultat2);
		assertNotNull(s2);
		
		assertNotEquals(s, s2);
	}
	
	@Test
	public void testGetListMatierePremiere() {
		JComboBox<String> lancement1 = new JComboBox<String>();
		when(service.getListMatierePremiere()).thenReturn(lancement1);
		JComboBox<String> s = service.getListMatierePremiere();
		int resultat = s.getItemCount();
		assertEquals(0, resultat);
		assertNotNull(s);
		
		JComboBox<String> lancement2 = new JComboBox<String>();
		lancement2.addItem("test");
		when(service.getListMatierePremiere()).thenReturn(lancement2);
		JComboBox<String> s2 = service.getListMatierePremiere();
		int resultat2 = s2.getItemCount();
		assertEquals(1, resultat2);
		assertNotNull(s2);
		
		assertNotEquals(s, s2);
	}
	
	@Test
	public void testGetidPlat() {
		when(service.getidPlat("pizza")).thenReturn(5);
		when(service.getidPlat("pate saumon")).thenReturn(9);
		
		int resultat = service.getidPlat("pizza");
		assertEquals(5,resultat);
		
		int resultat2 = service.getidPlat("pate saumon");
		assertEquals(9,resultat2);
	}

	
	
	
	
	
	
	

}
