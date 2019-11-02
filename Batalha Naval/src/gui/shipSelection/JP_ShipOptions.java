package gui.shipSelection;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.ships.*;
import rules.CtrlRules;
import rules.designPatterns.RulesFacade;

public class JP_ShipOptions extends JPanel{

	private final int OFFSET_X = 70;
	private final int OFFSET_Y = 100;
	private final int LABEL_OFFSET_X = 250;

	public int BATTLESHIP_COUNT = 1;
	public int CRUISER_COUNT = 2;
	public int DESTROYER_COUNT = 3;
	public int SUBMARINE_COUNT = 4;
	public int SEAPLANE_COUNT = 5;
	
	private final int BATTLESHIP_POSITION = 2;
	private final int CRUISER_POSITION = 3;
	private final int DESTROYER_POSITION = 4;
	private final int SUBMARINE_POSITION = 5;
	private final int SEAPLANE_POSITION = 1;
	
	JLabel battleshipCount;
	JLabel cruiserCount;
	JLabel destroyerCount;
	JLabel submarineCount;
	JLabel seaplaneCount;
	
	Battleship battleship;
	Cruiser cruiser;
	Destroyer destroyer;
	Submarine submarine;
	Seaplane seaplane;
	
	static JP_ShipOptions shipOptions;
    
    public static JP_ShipOptions getShipOptions() {
        if(shipOptions == null)
            shipOptions = new JP_ShipOptions();
        
        return shipOptions;    
    }

	private JP_ShipOptions() {
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
		addSeaplanes();
		
		paintLabels();
	}
	
	private JLabel createLabel(int pos, int count) {
		JLabel label = new JLabel( "x" + Integer.toString(count) );
		label.setFont(new Font("SansSerif", Font.PLAIN, 13));
		label.setBounds(LABEL_OFFSET_X, OFFSET_Y*pos+8, 30, 15);
		
		return label;
	}
	public void paintLabels() {		
		
		battleshipCount = createLabel(BATTLESHIP_POSITION, BATTLESHIP_COUNT);
		cruiserCount = createLabel(CRUISER_POSITION, CRUISER_COUNT);
		destroyerCount = createLabel(DESTROYER_POSITION, DESTROYER_COUNT);
		submarineCount = createLabel(SUBMARINE_POSITION, SUBMARINE_COUNT);
		seaplaneCount = createLabel(SEAPLANE_POSITION, SEAPLANE_COUNT);
		
		add(battleshipCount);
		add(cruiserCount);
		add(destroyerCount);
		add(submarineCount);
		add(seaplaneCount);
		
		repaint();
	}
	
	private void repaintLabels() {
		remove(battleshipCount);
		remove(cruiserCount);
		remove(destroyerCount);
		remove(submarineCount);
		remove(seaplaneCount);
		
		repaint();
		
		battleshipCount = createLabel(BATTLESHIP_POSITION, BATTLESHIP_COUNT);
		cruiserCount = createLabel(CRUISER_POSITION, CRUISER_COUNT);
		destroyerCount = createLabel(DESTROYER_POSITION, DESTROYER_COUNT);
		submarineCount = createLabel(SUBMARINE_POSITION, SUBMARINE_COUNT);
		seaplaneCount = createLabel(SEAPLANE_POSITION, SEAPLANE_COUNT);
		
		add(battleshipCount);
		add(cruiserCount);
		add(destroyerCount);
		add(submarineCount);
		add(seaplaneCount);
		
		repaint();
	}

	public void addBattleships() {
		battleship = new Battleship(OFFSET_X, OFFSET_Y*BATTLESHIP_POSITION);
		add(battleship);
	}
	public void addCruisers() {
		cruiser = new Cruiser(OFFSET_X, OFFSET_Y*CRUISER_POSITION);
		add(cruiser);
	}
	public void addDestroyers() {
		destroyer = new Destroyer(OFFSET_X, OFFSET_Y*DESTROYER_POSITION);
		add(destroyer);
	}
	public void addSubmarines() {
		submarine = new Submarine(OFFSET_X, OFFSET_Y*SUBMARINE_POSITION);
		add(submarine);
	}
	public void addSeaplanes() {
		seaplane= new Seaplane(OFFSET_X, OFFSET_Y*SEAPLANE_POSITION-25);
		add(seaplane);
	}
	
	public void reduceShipCount(Ship ship) {
		String shipName = ship.getClass().getName();
		
		if(!ship.getAvailability()) {
			return;
		}
		
		if(shipName == "gui.ships.Battleship") {
			BATTLESHIP_COUNT--;
			
			if(BATTLESHIP_COUNT == 0) {
				ship.setUnavailable();
			}
		}
		else if(shipName == "gui.ships.Cruiser") {
			CRUISER_COUNT--;
			
			if(CRUISER_COUNT == 0) {
				ship.setUnavailable();
			}
		}
		else if(shipName == "gui.ships.Destroyer") {
			DESTROYER_COUNT--;
			
			if(DESTROYER_COUNT == 0) {
				ship.setUnavailable();
			}
		}
		else if(shipName == "gui.ships.Submarine") {
			SUBMARINE_COUNT--;
			
			if(SUBMARINE_COUNT == 0) {
				ship.setUnavailable();
			}
		}
		else if(shipName == "gui.ships.Seaplane") {
			SEAPLANE_COUNT--;
			
			if(SEAPLANE_COUNT == 0) {
				ship.setUnavailable();
			}
		}
				
		repaintLabels();
	}
	
	public void resetShipCount() {
		BATTLESHIP_COUNT = 1;
		CRUISER_COUNT = 2;
		DESTROYER_COUNT = 3;
		SUBMARINE_COUNT = 4;
		SEAPLANE_COUNT = 5;
		
		battleship.setAvailable();
		cruiser.setAvailable();
		destroyer.setAvailable();
		submarine.setAvailable();
		seaplane.setAvailable();
		
		repaintLabels();
	}
}


