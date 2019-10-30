package gui.shipSelection;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import gui.board.JP_MouseBoard;

public class JP_ShipPlacement extends JPanel{

	public JP_ShipPlacement() {

		setBounds(1024/3, 0, 1024-(1024/3), 618);
		setLayout(null);
		//setBackground(Color.GREEN);
		setOpaque(false);
		
		add(new JP_MouseBoard());
		
	}

}