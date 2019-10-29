package gui.shipSelection;

import javax.swing.JPanel;

import gui.ships.*;

public class JP_ShipOptions extends JPanel{

	private final int SQUARE_SIZE = 31;
	private final int OFFSET_X = 70;
	private final int OFFSET_Y = 100;

	private final int BATTLESHIP_SIZE = 5; 
	private final int CRUISER_SIZE = 4; 
	private final int DESTROYER_SIZE = 3; 
	private final int SUBMARINE_SIZE = 2;

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
		for(int i = 0; i < BATTLESHIP_COUNT; i++){
			int battleshipSize = BATTLESHIP_SIZE*SQUARE_SIZE;
			Battleship battleship = new Battleship(OFFSET_X + (battleshipSize * i), OFFSET_Y*BATTLESHIP_COUNT);
			add(battleship);
		}
	}
	public void addCruisers() {
		for(int i = 0; i < CRUISER_COUNT; i++){
			int cruiserSize = CRUISER_SIZE*SQUARE_SIZE + SQUARE_SIZE;
			Cruiser cruiser = new Cruiser(OFFSET_X + (cruiserSize * i), OFFSET_Y*CRUISER_COUNT);
			add(cruiser);
		}
	}
	public void addDestroyers() {
		for(int i = 0; i < DESTROYER_COUNT; i++){
			int destroyerSize = DESTROYER_SIZE*SQUARE_SIZE;
			Destroyer destroyer = new Destroyer(OFFSET_X + (destroyerSize * i), OFFSET_Y*DESTROYER_COUNT);
			add(destroyer);
		}
	}
	public void addSubmarines() {
		for(int i = 0; i < SUBMARINE_COUNT; i++){
			int submarineSize = SUBMARINE_SIZE*SQUARE_SIZE;
			Submarine submarine = new Submarine(OFFSET_X + (submarineSize * i), OFFSET_Y*SUBMARINE_COUNT);
			add(submarine);
		}
	}

}