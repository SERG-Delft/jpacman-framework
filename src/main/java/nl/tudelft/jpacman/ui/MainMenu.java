package nl.tudelft.jpacman.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Window.Type;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import nl.tudelft.jpacman.Launcher;

import com.jgoodies.forms.layout.FormSpecs;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu {

	private JFrame frmJpacmanframework;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frmJpacmanframework.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJpacmanframework = new JFrame();
		frmJpacmanframework.setTitle("jpacman-framework");
		frmJpacmanframework.setBounds(100, 100, 465, 411);
		frmJpacmanframework.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJpacmanframework.setResizable(false);
	
		ImageIcon image = new ImageIcon(MainMenu.class.getResource("/nl/tudelft/jpacman/ui/res/pacman.png"));
		frmJpacmanframework.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel panel = new JLabel(image);
		frmJpacmanframework.getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblJpacmanframework = new JLabel("jpacman-framework");
		lblJpacmanframework.setFont(new Font("Arial Black", Font.ITALIC, 26));
		lblJpacmanframework.setHorizontalAlignment(SwingConstants.CENTER);
		frmJpacmanframework.getContentPane().add(lblJpacmanframework, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		frmJpacmanframework.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJpacmanframework.dispose();
				new Launcher().launch();
			}
		});
		buttonPanel.add(btnStartGame);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		buttonPanel.add(btnExit);
	}

}
