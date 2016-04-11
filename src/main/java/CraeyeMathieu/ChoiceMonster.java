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
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.npc.ghost.GhostColor;

public class ChoiceMonster extends JFrame implements ActionListener {
	
	 private JButton TwoPlayers,ThirdPlayers,FourPlayers;
	 private ArrayList<JButton> listPlayer = new ArrayList<JButton>();
	private JButton blinky,clyde,inky,pinky;
	private JLabel Player;
	private final static int height=40;
	private int numPlayer=0;
	private final static int widht=1000;
	JPanel panel = new JPanel();
	private Game game;
	private Launcher l = new Launcher();
	public void ButtonGhost()
	{
	
		blinky = new JButton("Blinky");
		clyde = new JButton("Clyde");
		inky = new JButton("Inky");
		pinky = new JButton("Pinky");
	/*	listButton.add(blinky);
		listButton.add(clyde);
		listButton.add(inky);
		listButton.add(pinky);*/
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
	public void addPlayer( Object o)
	{
		listPlayer.add((JButton) o);
	}
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
	
		if (source==pinky ||source==inky ||source==clyde || source==blinky)
		{
			((JComponent) source).setVisible(false); numPlayer--;
			addPlayer(source);
			AffPlayer();
			if(numPlayer==0 && source==pinky)
			{
				GameFactory gf = l.getGameFactory();
				Level level = l.makeLevel();
				gf.createSinglePlayerGame(level,GhostColor.PINK);
				this.dispose();
			}
		}
		if(source == TwoPlayers)
		{
			AppaerButtonGhost();
			numPlayer=2;AffPlayer();
		}
			if(source == ThirdPlayers)
			{
				AppaerButtonGhost();numPlayer=3;AffPlayer();
			}
				
					if(source == FourPlayers)
				{
						AppaerButtonGhost();numPlayer=4;AffPlayer();
				}
		
			
		

			
	}

	

}
