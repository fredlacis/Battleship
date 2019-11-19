package gui.initialScreen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JP_InitialScreen extends JPanel{
	
	/*
	 * https://www.colourlovers.com/palette/462628/Blazin_Jell-O_Rainbo
	 */
	
	private JPanel containerPnl = new JPanel();
	private JButton startBtn = new JButton("New Game");
	private JButton loadBtn = new JButton("Load Game");
	private JButton exitBtn = new JButton("Exit");

	public JP_InitialScreen() {
		setLayout(new GridBagLayout());
		setBounds(0,0,1024,768);
		setBackground(new Color(250, 250, 250));
		
		Dimension btnDimension = new Dimension(150, 50);
		
		containerPnl.setLayout(new BoxLayout(containerPnl, BoxLayout.Y_AXIS));
		containerPnl.setOpaque(false);
		
		startBtn.setBackground(new Color(0, 218, 60));
		startBtn.setForeground(new Color(0, 100, 10));
		startBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
		startBtn.setPreferredSize(btnDimension);
		startBtn.setMinimumSize(btnDimension);
		startBtn.setMaximumSize(btnDimension);
		startBtn.setAlignmentX( Component.CENTER_ALIGNMENT );
		startBtn.setToolTipText("Começar um novo jogo.");
		startBtn.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				new JF_NameForm().setVisible(true);
			} 
		} );
		
		loadBtn.setBackground(new Color(0, 203, 231));
		loadBtn.setForeground(new Color(0, 103, 131));
		loadBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
		loadBtn.setPreferredSize(btnDimension);
		loadBtn.setMinimumSize(btnDimension);
		loadBtn.setMaximumSize(btnDimension);
		loadBtn.setAlignmentX( Component.CENTER_ALIGNMENT );
		loadBtn.setToolTipText("Carregar um arquivo de jogo salvo.");
		loadBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				    
			  } 
			} );
		
		exitBtn.setBackground(new Color(223, 21, 26));
		exitBtn.setForeground(new Color(100, 5, 9));
		exitBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
		exitBtn.setPreferredSize(btnDimension);
		exitBtn.setMinimumSize(btnDimension);
		exitBtn.setMaximumSize(btnDimension);
		exitBtn.setAlignmentX( Component.CENTER_ALIGNMENT );
		exitBtn.setToolTipText("Sair do jogo :(");
		exitBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  System.exit(0);
			  } 
			} );
        
		containerPnl.add(startBtn);
		containerPnl.add(Box.createRigidArea(new Dimension(0, 15)));
		containerPnl.add(loadBtn);
		containerPnl.add(Box.createRigidArea(new Dimension(0, 15)));
		containerPnl.add(exitBtn);
		add(containerPnl);
	}
	
}
