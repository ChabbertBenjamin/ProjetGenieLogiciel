package testUnitaire;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

import fr.ul.miage.restaurant.menu.assistant.InterfaceAssistantService;


@ExtendWith(MockitoExtension.class)
public class InterfaceAssistantServiceTest {

	@Mock
	InterfaceAssistantService service;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getDataTest() {
		String[][] lancement1 = null;
		when(service.getData()).thenReturn(lancement1);
		String[][] s = service.getData();
		assertNull(s);
		
		String[][] lancement2 = new String[1][1];
		lancement2[0][0] = "test";
		when(service.getData()).thenReturn(lancement2);
		String[][] s2 = service.getData();
		assertNotNull(s2);
		assertEquals("test", lancement2[0][0]);
	}
	
	@Test
	public void getListTableToSetTest() {
		JComboBox<String> lancement1 = new JComboBox<String>();
		when(service.getListTableToSet(lancement1)).thenReturn(lancement1);
		JComboBox<String> s = service.getListTableToSet(lancement1);
		int resultat = s.getItemCount();
		assertEquals(0, resultat);
		assertNotNull(s);
		
		JComboBox<String> lancement2 = new JComboBox<String>();
		lancement2.addItem("test");
		when(service.getListTableToSet(lancement2)).thenReturn(lancement2);
		JComboBox<String> s2 = service.getListTableToSet(lancement2);
		int resultat2 = s2.getItemCount();
		assertEquals(1, resultat2);
		assertNotNull(s2);
		
		assertNotEquals(s, s2);
	}

	
	
	
	
	

}
