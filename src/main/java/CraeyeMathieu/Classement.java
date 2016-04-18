package CraeyeMathieu;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.jpacman.Launcher;

public class Classement extends JFrame {

	ChoiceMonster cM=new ChoiceMonster();
	JPanel panel = new JPanel();
	Launcher l=new Launcher();
	public void ClassementJoueur()
	{
		if(l.cM.jBlinky!=null )
		{
			JLabel Joueur1= new JLabel("Blinky, Votre score est :"+l.cM.jBlinky.getScore());
			panel.add(Joueur1);
		}
		if(l.cM.jPinky!=null )
		{
			JLabel Joueur2= new JLabel("Pinky, Votre score est :" +l.cM.jPinky.getScore());
			panel.add(Joueur2);
		}
		
		if(l.cM.jClyde!=null )
	
		{
			JLabel Joueur4= new JLabel("Clyde, Votre score est :" +l.cM.jClyde.getScore());
			panel.add(Joueur4);
		}
			
		if(l.cM.jInky!=null )
	
		{
			JLabel Joueur3= new JLabel("Inky, Votre score est :"+l.cM.jInky.getScore());
			panel.add(Joueur3);
		}
		
		JLabel Winner;
		
		/*if(((l.cM.jBlinky.getScore()>l.cM.jPinky.getScore()&& l.cM.jBlinky.getScore()> l.cM.jInky.getScore())
				&& l.cM.jBlinky.getScore()>l.cM.jClyde.getScore())
				&& l.cM.jPinky!=null && l.cM.jBlinky!=null&& l.cM.jInky!=null && l.cM.jClyde!=null)
		{
			Winner= new JLabel("Le Gagnant est : Blinky");
		}
		if(((l.cM.jPinky.getScore()>l.cM.jBlinky.getScore()&& l.cM.jPinky.getScore()> l.cM.jInky.getScore())
				&& l.cM.jPinky.getScore()>l.cM.jClyde.getScore())
				&& l.cM.jPinky!=null && l.cM.jBlinky!=null&& l.cM.jInky!=null && l.cM.jClyde!=null)
		{
			Winner= new JLabel("Le Gagnant est : Pinky");
		}
		if(((l.cM.jInky.getScore()>l.cM.jPinky.getScore()&& l.cM.jInky.getScore()> l.cM.jBlinky.getScore())
				&& l.cM.jInky.getScore()>l.cM.jClyde.getScore())
				&& l.cM.jPinky!=null && l.cM.jBlinky!=null&& l.cM.jInky!=null && l.cM.jClyde!=null)
		{
			Winner= new JLabel("Le Gagnant est : Inky");
		}
		if(((l.cM.jClyde.getScore()>l.cM.jBlinky.getScore()&& l.cM.jClyde.getScore()> l.cM.jInky.getScore())
				&& l.cM.jClyde.getScore()>l.cM.jPinky.getScore())
				&& l.cM.jPinky!=null && l.cM.jBlinky!=null&& l.cM.jInky!=null && l.cM.jClyde!=null)
		{
			Winner= new JLabel("Le Gagnant est : Clyde");
		}*/
		
		
		
		this.setTitle("Classement des joueurs");
		this.setSize(700,300);
		
		//panel.add(Winner);
		this.setContentPane(panel);
		this.setVisible(true);
	}
}
