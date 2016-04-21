package CraeyeMathieu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.jpacman.Launcher;

public class ChoiceMonster extends JFrame implements ActionListener {
	
	 private JButton TwoPlayers,ThirdPlayers,FourPlayers;
	private JButton blinky,clyde,inky,pinky;
	private JLabel Player;
	private int numPlayer=0;
	private static int gamers;
	JPanel panel = new JPanel();
	public Joueur jInky,jClyde,jPinky,jBlinky,j;
	public ArrayList<Joueur>listJ=new ArrayList<Joueur>();
	private Launcher l = new Launcher();
	
	/**
	 * Creating buttons for different Monsters
	 */
	public void ButtonGhost()
	{
		j=new Joueur();
		blinky = new JButton("Blinky");
		clyde = new JButton("Clyde");
		inky = new JButton("Inky");
		pinky = new JButton("Pinky");
		blinky.addActionListener(this);
		blinky.setVisible(false);
		blinky.setForeground(Color.RED);
		pinky.setForeground(Color.PINK);
		clyde.setForeground(Color.ORANGE);
		inky.setForeground(Color.CYAN);
		pinky.setVisible(false);
		clyde.setVisible(false);
		inky.setVisible(false);
		inky.addActionListener(this);
		pinky.addActionListener(this);
		clyde.addActionListener(this);
		JPanel panel2=new JPanel();
		panel2.add(blinky);
		panel2.add(inky);
		panel2.add(pinky);
		panel2.add(clyde);
		panel.add(panel2);
		panel.add(Player,BorderLayout.SOUTH);
	}
	/**
	 * it brings up the buttons one time we chose a number of players
	 */
	public void AppaerButtonGhost()
	{
		blinky.setVisible(true);
		pinky.setVisible(true);
		clyde.setVisible(true);
		inky.setVisible(true);
	}
	/**
	 * update texte players
	 */
	public void AffPlayer()
	{
		Player.setText("Joueur "+ numPlayer +", Veuillez chosir votre monstre");
	}
	/**
	 * creating buttons for choosing the number of players
	 */
	public void ButtonPlayer()
	{
		Player= new JLabel("Joueur "+ numPlayer +", Veuillez chosir votre monstre");
		TwoPlayers = new JButton("2 joueurs");
		ThirdPlayers = new JButton("3 joueurs");
		FourPlayers = new JButton("4 joueurs");
		TwoPlayers.addActionListener(this);
	
		ThirdPlayers.addActionListener(this);
		
		FourPlayers.addActionListener(this);

		this.setTitle("Nombre de joueurs");
		this.setSize(500,300);
		panel.add(TwoPlayers);
		panel.add(ThirdPlayers);
		panel.add(FourPlayers);
		this.setContentPane(panel);
		this.setVisible(true);
		ButtonGhost();
	}
	/**
	 * manages the event of button clicks
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

			
			if(source==pinky)
			{
				((JComponent) source).setVisible(false); 
				numPlayer--;
				AffPlayer();
				jPinky=new Joueur("pinky",numPlayer,0);
				listJ.add(jPinky);
				if (gamers==j.getNbrJoueur())
				{
				l.launch();
				j.setListJoueur(listJ);
				}
				
			}
			if(source==clyde)
			{
			((JComponent) source).setVisible(false); 
				numPlayer--;
				AffPlayer();
				jClyde=new Joueur("clyde",numPlayer,0);
				listJ.add(jClyde);
				if (numPlayer==0)
				{
				l.launch();
				j.setListJoueur(listJ);
				}
				
			}
			if( source==inky)
			{
				((JComponent) source).setVisible(false);
				numPlayer--;
				AffPlayer(); 
				jInky=new Joueur("inky",numPlayer,0);
				listJ.add(jInky);
				if (numPlayer==0)
				{
				l.launch();
				j.setListJoueur(listJ);
				}
				
			}
			if(source==blinky)
			{
				((JComponent) source).setVisible(false);
				numPlayer--;
				AffPlayer(); 
				jBlinky=new Joueur("blinky",numPlayer,0);
				listJ.add(jBlinky);
				if (numPlayer==0)
				{
				l.launch();
				j.setListJoueur(listJ);
				}
				
			}
		
		if(source == TwoPlayers)
		{
			AppaerButtonGhost();
			numPlayer=2;AffPlayer();j.SetNbrJoueur(2);gamers=2;
		}
			if(source == ThirdPlayers)
			{
				AppaerButtonGhost();numPlayer=3;AffPlayer();j.SetNbrJoueur(3);gamers=3;
			}
				
					if(source == FourPlayers)
				{
						AppaerButtonGhost();numPlayer=4;AffPlayer();j.SetNbrJoueur(4);gamers=4;
				}
					
		}

	

}
