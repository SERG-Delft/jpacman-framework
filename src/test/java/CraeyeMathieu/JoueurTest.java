package CraeyeMathieu;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class JoueurTest {
	
	private Joueur j;
	private ArrayList<Joueur> listJ=new ArrayList<Joueur>();
	
	@Before
	public void init()
	{
		j=new Joueur("clyde",1,230);
		j.setListJoueur(listJ);
	}
	@Test
	public void testJoueur() {
		// test le score
		 assertEquals(230,j.getScore());
		 // test le nom
		 assertEquals("clyde",j.getName());
	}

	@Test
	public void testAddJoueur() {
		Joueur joueur = new Joueur ("pinky",2,111);
		j.addJoueur(joueur);
		assertEquals(1,listJ.size());
	}

}
