package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.K;
import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public class JP_Utilities extends JPanel{

	public final int UTILITIES_HEIGHT = 150;
	
	JLabel message1 = new JLabel("Mensagem 1");
	JLabel message2 = new JLabel("Mensagem 2");
	JLabel message3 = new JLabel("Mensagem 3");
	
	public JP_Utilities() {
		
		setLayout(null);
		
		setBounds(0, K.ALT_DEFAULT - UTILITIES_HEIGHT, K.LARG_DEFAULT, UTILITIES_HEIGHT);
		//setBackground(Color.BLUE);
		setOpaque(false);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBounds(0, 0, K.LARG_DEFAULT/3, UTILITIES_HEIGHT);
		
		JLabel key1 = new JLabel("ESC - Unselect ship");
		JLabel key2 = new JLabel("R - Reset board");
		JLabel key3 = new JLabel("Right Click - Rotate Ship");
		
		infoPanel.add(Box.createRigidArea(new Dimension(0, 27)));
		infoPanel.add(key3);
		infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		infoPanel.add(key1);
		infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		infoPanel.add(key2);
		
		JPanel messagesPanel = new JPanel();
		messagesPanel.setLayout(null);
		messagesPanel.setBounds(K.LARG_DEFAULT/3, 0, K.LARG_DEFAULT/3, UTILITIES_HEIGHT);
		
		message1.setBounds(0, 60, K.LARG_DEFAULT/3, 40);
		message1.setFont(new Font("SansSerif", Font.BOLD, 20));
		message1.setForeground(Color.BLACK);
		message1.setHorizontalAlignment(SwingConstants.CENTER);
		message1.setVerticalAlignment(SwingConstants.CENTER);
		
		message2.setBounds(0, 40, K.LARG_DEFAULT/3, 30);
		message2.setFont(new Font("SansSerif", Font.BOLD, 15));
		message2.setForeground(Color.GRAY);
		message2.setHorizontalAlignment(SwingConstants.CENTER);
		message2.setVerticalAlignment(SwingConstants.CENTER);
		
		message3.setBounds(0, 20, K.LARG_DEFAULT/3, 30);
		message3.setFont(new Font("SansSerif", Font.BOLD, 10));
		message3.setForeground(Color.LIGHT_GRAY);
		message3.setHorizontalAlignment(SwingConstants.CENTER);
		message3.setVerticalAlignment(SwingConstants.CENTER);
		
		messagesPanel.add(message1);
		messagesPanel.add(message2);
		messagesPanel.add(message3);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBounds(2 * (K.LARG_DEFAULT/3), 0, K.LARG_DEFAULT/3, UTILITIES_HEIGHT);
		
		add(infoPanel);
		add(messagesPanel);
		add(buttonsPanel);
		
	}
	
	public void setMessages(List<String> messages) {
		message1.setText( RulesFacade.getRules().getMessages().get(0) );
		message2.setText( RulesFacade.getRules().getMessages().get(1) );
		message3.setText( RulesFacade.getRules().getMessages().get(2) );
		repaint();
	}
	
}
