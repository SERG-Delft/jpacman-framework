package CraeyeMathieu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	
	private final static int height=40;
	private int numPlayer=0;
	private final static int widht=1000;
	JPanel panel = new JPanel();
	public Joueur jInky,jClyde,jPinky,jBlinky,j;
	public ArrayList<Joueur>listJ=new ArrayList<Joueur>();
	private Launcher l = new Launcher();
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

		panel.add(blinky,BorderLayout.SOUTH);
		panel.add(inky,BorderLayout.CENTER);
		panel.add(pinky,BorderLayout.CENTER);
		panel.add(clyde,BorderLayout.CENTER);
		panel.add(Player);		
	}
	public void AppaerButtonGhost()
	{
		blinky.setVisible(true);
		pinky.setVisible(true);
		clyde.setVisible(true);
		inky.setVisible(true);
	}
	public void AffPlayer()
	{
		Player.setText("Joueur "+ numPlayer +", Veuillez chosir votre monstre");
	}
	public void ButtonPlayer()
	{
		Player= new JLabel("Joueur "+ numPlayer +", Veuillez chosir votre monstre");
		TwoPlayers = new JButton("2 joueurs");
		ThirdPlayers = new JButton("3 joueurs");
		FourPlayers = new JButton("4 joueurs");
		TwoPlayers.addActionListener(this);
		TwoPlayers.setSize(widht, height);
		ThirdPlayers.addActionListener(this);
		ThirdPlayers.setSize(widht, height);
		FourPlayers.addActionListener(this);
		FourPlayers.setSize(widht, height);
		this.setTitle("Nombre de joueurs");
		this.setSize(600,300);
	
		panel.add(TwoPlayers, BorderLayout.NORTH);
		panel.add(ThirdPlayers, BorderLayout.NORTH);
		panel.add(FourPlayers, BorderLayout.NORTH);
		this.setContentPane(panel);
		this.setVisible(true);
		ButtonGhost();
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

			
			if(source==pinky)
			{
				((JComponent) source).setVisible(false); 
				numPlayer--;
				AffPlayer();
				jPinky=new Joueur("pinky",numPlayer,0);
				listJ.add(jPinky);
				if (numPlayer==0)
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
				jClyde=new Joueur("clyde",numPlayer,0);listJ.add(jClyde);
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
				jInky=new Joueur("inky",numPlayer,0);listJ.add(jInky);
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
				jBlinky=new Joueur("blinky",numPlayer,0);listJ.add(jBlinky);
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
			numPlayer=2;AffPlayer();j.SetNbrJoueur(2);
		}
			if(source == ThirdPlayers)
			{
				AppaerButtonGhost();numPlayer=3;AffPlayer();j.SetNbrJoueur(3);
			}
				
					if(source == FourPlayers)
				{
						AppaerButtonGhost();numPlayer=4;AffPlayer();j.SetNbrJoueur(4);
				}
					
		}

	

}
