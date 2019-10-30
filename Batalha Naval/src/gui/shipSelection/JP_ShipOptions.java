package gui.shipSelection;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.ships.*;

public class JP_ShipOptions extends JPanel{

	private final int SQUARE_SIZE = 31;
	private final int OFFSET_X = 70;
	private final int OFFSET_Y = 100;
	private final int LABEL_OFFSET_X = 250;

	private final int BATTLESHIP_SIZE = 5; 
	private final int CRUISER_SIZE = 4; 
	private final int DESTROYER_SIZE = 2; 
	private final int SUBMARINE_SIZE = 1;

	private final int BATTLESHIP_COUNT = 1;
	private final int CRUISER_COUNT = 2;
	private final int DESTROYER_COUNT = 3;
	private final int SUBMARINE_COUNT = 4;
	private final int HYDROPLANE_COUNT = 5;

	public JP_ShipOptions() {
		setLayout(null);
		setBounds(0,0,1024/3,618);
		setOpaque(false);

		displayShipOptions();
	}

	public void displayShipOptions() {
		System.out.println("Loading Ship Options");

		addBattleships();
		addCruisers();
		addDestroyers();
		addSubmarines();

		repaint();
	}

	public void addBattleships() {

		int battleshipSize = BATTLESHIP_SIZE*SQUARE_SIZE;
		Battleship battleship = new Battleship(OFFSET_X, OFFSET_Y*BATTLESHIP_COUNT);
		
		JLabel battleshipCount = new JLabel( "x" + Integer.toString(BATTLESHIP_COUNT) );
		battleshipCount.setFont(new Font("SansSerif", Font.PLAIN, 13));
		battleshipCount.setBounds(LABEL_OFFSET_X, OFFSET_Y*BATTLESHIP_COUNT+8, 30, 15);
		
		add(battleship);
		add(battleshipCount);
		
	}
	public void addCruisers() {
		
		int cruiserSize = CRUISER_SIZE*SQUARE_SIZE;
		Cruiser cruiser = new Cruiser(OFFSET_X, OFFSET_Y*CRUISER_COUNT);
		
		JLabel cruiserCount = new JLabel( "x" + Integer.toString(CRUISER_COUNT) );
		cruiserCount.setFont(new Font("SansSerif", Font.PLAIN, 13));
		cruiserCount.setBounds(LABEL_OFFSET_X, OFFSET_Y*CRUISER_COUNT+8, 30, 15);
		
		add(cruiser);
		add(cruiserCount);
		
	}
	public void addDestroyers() {
		
		int destroyerSize = DESTROYER_SIZE*SQUARE_SIZE;
		Destroyer destroyer = new Destroyer(OFFSET_X, OFFSET_Y*DESTROYER_COUNT);
		
		JLabel destroyerCount = new JLabel( "x" + Integer.toString(DESTROYER_COUNT) );
		destroyerCount.setFont(new Font("SansSerif", Font.PLAIN, 13));
		destroyerCount.setBounds(LABEL_OFFSET_X, OFFSET_Y*DESTROYER_COUNT+8, 30, 15);
		
		add(destroyer);
		add(destroyerCount);
		
	}
	public void addSubmarines() {
		
		int submarineSize = SUBMARINE_SIZE*SQUARE_SIZE;
		Submarine submarine = new Submarine(OFFSET_X, OFFSET_Y*SUBMARINE_COUNT);
		
		JLabel submarineCount = new JLabel( "x" + Integer.toString(SUBMARINE_COUNT) );
		submarineCount.setFont(new Font("SansSerif", Font.PLAIN, 13));
		submarineCount.setBounds(LABEL_OFFSET_X, OFFSET_Y*SUBMARINE_COUNT+8, 30, 15);
		
		add(submarine);
		add(submarineCount);
		
	}

}