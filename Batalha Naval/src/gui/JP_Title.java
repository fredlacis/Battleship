package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.K;

@SuppressWarnings("serial")
public class JP_Title extends JPanel{

	JLabel titleLabel = new JLabel();
	
	public JP_Title(String title) {
		
		setLayout(null);
		setBounds(0, 0, K.LARG_DEFAULT, 50);
		setBackground(Color.CYAN);
		setOpaque(false);
		
		titleLabel.setText(title);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVerticalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 5, 1024, 40);
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		titleLabel.setForeground(Color.BLACK);
		
		add(titleLabel);
		
	}
	
	public void setText(String text) {
		titleLabel.setText(text);
		repaint();
	}
	
}
