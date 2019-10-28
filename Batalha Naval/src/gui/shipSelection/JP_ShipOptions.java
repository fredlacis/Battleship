package gui.shipSelection;

import javax.swing.JPanel;

import gui.ships.*;

public class JP_ShipOptions extends JPanel{

	public JP_ShipOptions() {
		setLayout(null);
		setBounds(0,0,1024/3,618);
		setOpaque(false);
		
		displayShipOptions();
		
	}
	
	public void displayShipOptions() {
		System.out.println("Loading Ship Options");
		
		//add( new Destroyer());
		
		Destroyer destruidor = new Destroyer();
		
		//destruidor.setBounds();
		
		repaint();
		
	}
	
}
