package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JP_InitialScreen extends JPanel {
	
	
	
	private JLabel titleLbl = new JLabel("Batalha Naval");
	private JButton startBtn = new JButton("Start Game");
	private JButton loadBtn = new JButton("Load Game");
	private JButton exitBtn = new JButton("Exit");

	public JP_InitialScreen() {
		setLayout(null);
		
		titleLbl.setBounds(437, 350, 150, 50);
		
		startBtn.setBackground(new Color(44, 163, 70));
		startBtn.setForeground(Color.WHITE);
		startBtn.setBounds(437, 400, 150, 50);
		
		
		loadBtn.setBackground(Color.BLUE);
		loadBtn.setForeground(Color.WHITE);
		loadBtn.setBounds(437, 460, 150, 50);
		
		exitBtn.setBackground(Color.RED);
		exitBtn.setForeground(Color.WHITE);
		exitBtn.setBounds(437, 520, 150, 50);
		
        
		
		add(titleLbl);
		add(startBtn);
		add(loadBtn);
		add(exitBtn);
		
		setSize(new Dimension(200, 200));
		setBackground(new Color(250, 250, 250));
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	
}
