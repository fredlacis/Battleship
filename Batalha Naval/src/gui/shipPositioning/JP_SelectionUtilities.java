package gui.shipPositioning;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gui.attack.JF_Attack;
import main.K;
import rules.designPatterns.IObservable;
import rules.designPatterns.IObserver;
import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public class JP_SelectionUtilities extends JPanel implements IObserver{

	public final int UTILITIES_HEIGHT = 150;
	
	JLabel message1 = new JLabel("");
	JLabel message2 = new JLabel("");
	JLabel message3 = new JLabel("");
	
	JButton next = new JButton();
	
	static JP_SelectionUtilities selectionUtilites;
    
    public static JP_SelectionUtilities getSelectionUtilites() {
        if(selectionUtilites == null)
        	selectionUtilites = new JP_SelectionUtilities();
        
        return selectionUtilites;    
    }
	
	private JP_SelectionUtilities() {
		
		RulesFacade.getRules().register(this);
		
		setLayout(null);
		
		setBounds(0, K.ALT_DEFAULT - UTILITIES_HEIGHT, K.LARG_DEFAULT, UTILITIES_HEIGHT);
		setOpaque(false);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBounds(0, 0, K.LARG_DEFAULT/3, UTILITIES_HEIGHT);
		
		JLabel key1 = new JLabel("ESC - Unselect ship");
		JLabel key2 = new JLabel("R - Reset board");
		JLabel key3 = new JLabel("Right Click - Rotate Ship");
		
		key1.setFont(new Font("SansSerif", Font.BOLD, 12));
		key1.setForeground(Color.BLACK);
		key2.setFont(new Font("SansSerif", Font.BOLD, 12));
		key2.setForeground(Color.BLACK);
		key3.setFont(new Font("SansSerif", Font.BOLD, 12));
		key3.setForeground(Color.BLACK);

		
		infoPanel.add(Box.createRigidArea(new Dimension(20, 27)));
		infoPanel.add(key3);
		infoPanel.add(Box.createRigidArea(new Dimension(20, 15)));
		infoPanel.add(key1);
		infoPanel.add(Box.createRigidArea(new Dimension(20, 15)));
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
		
		buttonsPanel.setLayout(null);
		
		next.setBounds((K.LARG_DEFAULT/3)-150-10, (UTILITIES_HEIGHT/2)-35, 150, 50);
		
		next.setText("Next");
		next.setBackground(new Color(0, 218, 60));
		next.setForeground(new Color(100, 100, 100));
		next.setFont(new Font("SansSerif", Font.BOLD, 16));
		buttonDisable();
		
		next.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				
				buttonDisable();
				
				RulesFacade.getRules().unsetSelectedShip();
				RulesFacade.getRules().setBoard(RulesFacade.getRules().getCurrentPlayer());
				
				if( RulesFacade.getRules().getCurrentPlayer() == 2 ) {
					JF_Attack.getAttackFrame().setVisible(true);
					JF_ShipSelection.getShipSelection().setVisible(false);
				}
				else {
					RulesFacade.getRules().resetGrid();
				}
				
				RulesFacade.getRules().nextPlayer();
			}
		});
		
		buttonsPanel.add(next);
		
		add(infoPanel);
		add(messagesPanel);
		add(buttonsPanel);
		
	}
	
	public void buttonEnable() {
		next.setBackground(new Color(0, 218, 60));
		next.setEnabled(true);
		this.repaint();
	}
	
	public void buttonDisable() {
		next.setBackground(Color.LIGHT_GRAY.darker());
		next.setEnabled(false);
		this.repaint();
	}
	
	public void setMessages(List<String> messages, boolean validation) {
		
		try {
			if( this.message1.getText() != messages.get( messages.size() - 1 ) ) {
				if( validation ) {
					message1.setForeground(Color.GREEN.darker().darker());
				}
				else {
					message1.setForeground(Color.RED);
				}
			}
		
		
			message1.setText( messages.get( messages.size() - 1 ) );
			message2.setText( messages.get( messages.size() - 2 ) );
			message3.setText( messages.get( messages.size() - 3 ) );
		}
		catch(IndexOutOfBoundsException e) {
			
		}
		
		repaint();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void notify(IObservable o) {
		
		Object lob[] = (Object []) o.get();
		
		List<String> newMessages = (List<String>) lob[K.objectValues.MESSAGES.getValue()];
		boolean validation = (boolean) lob[ K.objectValues.IS_VALID.getValue() ];
		
		setMessages(newMessages, validation);
		
	}
	
}
