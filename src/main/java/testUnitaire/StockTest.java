package testUnitaire;

import fr.ul.miage.restaurant.menu.InterfaceDirecteur;
import fr.ul.miage.restaurant.models.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StockTest {

	@Mock
	InterfaceDirecteur service;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetDataMock() {
		ArrayList<Stock> lancement1 = new ArrayList<Stock>();
		ArrayList<Stock> lancement2 = new ArrayList<Stock>();
		lancement2.add(new Stock("sucre", 5));
		when(service.getData()).thenReturn(lancement1, lancement2);

		ArrayList<Stock> s = service.getData();
		int resultat = s.size();
		assertEquals(0, resultat);

		ArrayList<Stock> s2 = service.getData();
		int resultat2 = s2.size();
		assertEquals(1, resultat2);
	}

}
