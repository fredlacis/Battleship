package rules;

import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import gui.shipPositioning.JP_PositioningGrid;
import gui.shipPositioning.JP_ShipOptions;
import gui.ships.Ship;
import main.K;
import main.K.ORIENTATION;
import main.K.PHASE;
import main.Launcher;
import rules.designPatterns.IObservable;
import rules.designPatterns.IObserver;

public class CtrlRules implements IObservable{
		
	enum Ships{
		//NAME    -> Alive cell of ship
		//D_NAME  -> Destroyed cell of ship
		BATTLESHIP(5),
		D_BATTLESHIP(-5),
		CRUISER(4),
		D_CRUISER(-4),
		DESTROYER(2),
		D_DESTROYER(-2),
		SUBMARINE(1),
		D_SUBMARINE(-1),
		SEAPLANE(3),
		D_SEAPLANE(-3);
		
		private final int value;

        Ships(final int newValue) {
            value = newValue;
        }

        public int getValue() { return value; }
	}
	
	private int currentPlayer;	
	
	private PHASE phase;
	
	private String player1;
	private String player2;

	private int board1[][];
	private int board2[][];

	private boolean isValid;

	private Ship selectedShip;
	
	private int cellsToPaint[][];

	List<IObserver> lob = new ArrayList<IObserver>();
	List<String> messages = new ArrayList<String>();
	
	private int pointsPlayer1 = 0;
	private int pointsPlayer2 = 0;
	private int currentAttackCount = 1;
	private boolean result = false;
	
	
	/* CONSTRUTOR */

	public CtrlRules() {
		newGame();
	}
	public void newGame() {
		phase = PHASE.POSITION;
		board1 = K.createEmptyGrid();
		board2 = K.createEmptyGrid();
		currentPlayer = 1;
		refreshBoard();
	}
	
	
	/* FUNCOES PUBLICAS PARA FASE DE ATAQUES */
	
	public void startGame() {
		phase = PHASE.ATTACK;
	}
	public void nextPlayer() {
		checkResult();
		
		if(result) {
			System.out.printf("Player %d wins!\n", currentPlayer);
			refreshBoard();
			return;
		}
		
		currentPlayer = getNextPlayer();
		refreshBoard();
	}
	public void attack(int x, int y) {
		System.out.printf("Attack from player %s in position X:%d Y:%d\n", getPlayerName(currentPlayer), x, y);
		
		if(getOppositeBoard(currentPlayer)[x][y] > 0) {
			System.out.printf("HIT! Player %s destroying Ship : %d!\n", currentPlayer, getOppositeBoard(currentPlayer)[x][y]);
			//playSound("explosion.wav");
			
			destroyShip(x, y);
		}
		else if(getOppositeBoard(currentPlayer)[x][y] == 0) {
			System.out.println("WATER!");
			destroyShip(x, y);
		}
		
		if(currentAttackCount == 3 ) {	
			
			currentAttackCount = 1;
			nextPlayer();
		}
		else {
			currentAttackCount++;
			refreshBoard();
		}
	
	}
	public void checkResult() {
		
		int currentPlayerPoints = 0;
		
		switch(currentPlayer) {
			case 1: 
				currentPlayerPoints = pointsPlayer1;
				break;
			case 2: 
				currentPlayerPoints = pointsPlayer2;
				break;
		}
				
		if(currentPlayerPoints == 38) {
			result = true;
		}
		
		refreshBoard();
	}

	
	/* FUNCOES PRIVADAS PARA FASE DE ATAQUES */
	
	private void destroyShip(int x, int y) {
		int[][] currentBoard = getOppositeBoard(currentPlayer);
		int currentPlayerPoints = 0;
		
		if(currentBoard[x][y] == 0) {
			currentBoard[x][y] = -9;
		}
		if(currentBoard[x][y] > 0) {
			currentPlayerPoints += 1;
			currentBoard[x][y] = -currentBoard[x][y];
		}
		
		
		switch(currentPlayer) {
			case 1: 
				pointsPlayer1 += currentPlayerPoints;
				System.out.printf("Player %d Points: %d\n", currentPlayer, pointsPlayer1);
				break;
			case 2: 
				pointsPlayer2 += currentPlayerPoints;
				System.out.printf("Player %d Points: %d\n", currentPlayer, pointsPlayer2);
				break;
		}
		
	}
	private void destroySeaplane(int x, int y) {
		//TODO
	}
	
	
	/* FUNCOES PUBLICAS PARA POSICIONAMENTO DO TABULEIRO */
	
	public void shipRotate() {
		if(selectedShip == null) return;
		selectedShip.rotate();
		refreshBoard();
	}
	public void positionShip(int x, int y, int[][] definedCells) {
		if(selectedShip == null) {
			setIsValid(false);
			addMessage("Select a ship");
			return;
		}
				
		checkPos(x, y, definedCells);
				
		if(!selectedShip.getAvailability()) {
			addMessage("No more ships of this type");
			refreshBoard();
			return;
		}
		
		if(!isValid) {
			addMessage("Invalid position");
			refreshBoard();
			return;
		}
		
		addMessage("Positioning ship");
		JP_PositioningGrid.getGrid().paintCells(cellsToPaint);
		JP_ShipOptions.getShipOptions().reduceShipCount(selectedShip);
		
		cellsToPaint = K.createEmptyGrid();
		refreshBoard();
		
	}
	public void resetGrid() {
		//System.out.println("Limpando Grid");
		setIsValid(true);
		
		addMessage("Reseting Grid");
		
		JP_PositioningGrid.getGrid().reset();
		
		JP_ShipOptions.getShipOptions().resetShipCount();
		
		unsetSelectedShip();
	}
	public void checkPos(int x, int y, int[][] definedCells) {
		
		if(selectedShip == null) {
			return;
		}
				
		if(selectedShip.getClass().getName() == "gui.ships.Seaplane") {
			checkPosSeaplane(x, y, definedCells);
		}
		else{
			checkPosShip(x, y, definedCells);
		}
		
		refreshBoard();
	}
	
	
	/* FUNCOES PRIVADAS PARA POSICIONAMENTO DO TABULEIRO */
	
	private void checkPosShip(int x, int y, int[][] definedCells){
		
		cellsToPaint = K.createEmptyGrid();
		setIsValid(true);
		
		if(!selectedShip.getAvailability()) {
			setIsValid(false);
			return;
		}
		
		if(selectedShip.orientation == ORIENTATION.TOP) {
			for(int i = 0; i < selectedShip.shipSize; i++) {
				try {
					if(definedCells[x][y-i] != 0) setIsValid(false);
					cellsToPaint[x][y-i] = selectedShip.shipSize;
				}
				catch(Exception e) {
					setIsValid(false);
				}
			}
		}
		else if(selectedShip.orientation == ORIENTATION.RIGHT) {
			for(int i = 0; i < selectedShip.shipSize; i++) {
				try {
					if(definedCells[x+i][y] != 0) setIsValid(false);
					cellsToPaint[x+i][y] = selectedShip.shipSize;
				}
				catch(Exception e) {
					setIsValid(false);
				}
			}
		}
		else if(selectedShip.orientation == ORIENTATION.DOWN) {
			for(int i = 0; i < selectedShip.shipSize; i++) {
				try {
					if(definedCells[x][y+i] != 0) setIsValid(false);
					cellsToPaint[x][y+i] = selectedShip.shipSize;
				}
				catch(Exception e) {
					setIsValid(false);
				}
			}
		}
		else if(selectedShip.orientation == ORIENTATION.LEFT) {
			for(int i = 0; i < selectedShip.shipSize; i++) {
				try {
					if(definedCells[x-i][y] != 0) setIsValid(false);
					cellsToPaint[x-i][y] = selectedShip.shipSize;
				}
				catch(Exception e) {
					setIsValid(false);
				}
			}
		}
		
		if(isValid) {
			setIsValid( checkSurroundingsShip(x, y, definedCells) );
		}
						
	}
	private void checkPosSeaplane(int x, int y, int [][] definedCells){
		
		cellsToPaint = K.createEmptyGrid();
		
		setIsValid(true);
		
		if(!selectedShip.getAvailability()) {
			setIsValid(false);
			return;
		}
		
		if(definedCells[x][y] != 0) {
			setIsValid(false);
		}
		
		if(selectedShip.orientation == ORIENTATION.TOP) {
			cellsToPaint[x][y] = selectedShip.shipSize;
			try {
				if(definedCells[x-1][y-1] != 0) setIsValid(false);
				cellsToPaint[x-1][y-1] = selectedShip.shipSize;
			}
			catch (Exception e){
				setIsValid(false);
			}
			try {
				if(definedCells[x][y-2] != 0) setIsValid(false);
				cellsToPaint[x][y-2] = selectedShip.shipSize;
			}
			catch (Exception e){
				setIsValid(false);
			}
		}
		else if(selectedShip.orientation == ORIENTATION.RIGHT) {
			cellsToPaint[x][y] = selectedShip.shipSize;
			try {
				if(definedCells[x+1][y-1] != 0) setIsValid(false);
				cellsToPaint[x+1][y-1] = selectedShip.shipSize;
			}
			catch (Exception e){
				setIsValid(false);
			}
			try {
				if(definedCells[x+2][y] != 0) setIsValid(false);
				cellsToPaint[x+2][y] = selectedShip.shipSize;
			}
			catch (Exception e){
				setIsValid(false);
			}
		}
		else if(selectedShip.orientation == ORIENTATION.DOWN) {
			cellsToPaint[x][y] = selectedShip.shipSize;
			try {
				if(definedCells[x+1][y+1] != 0) setIsValid(false);
				cellsToPaint[x+1][y+1] = selectedShip.shipSize;
			}
			catch (Exception e){
				setIsValid(false);
			}
			try {
				if(definedCells[x][y+2] != 0) setIsValid(false);
				cellsToPaint[x][y+2] = selectedShip.shipSize;
			}
			catch (Exception e){
				setIsValid(false);
			}
		}
		else if(selectedShip.orientation == ORIENTATION.LEFT) {
			cellsToPaint[x][y] = selectedShip.shipSize;
			try {
				if(definedCells[x-1][y+1] != 0) setIsValid(false);
				cellsToPaint[x-1][y+1] = selectedShip.shipSize;
			}
			catch (Exception e){
				setIsValid(false);
			}
			try {
				if(definedCells[x-2][y] != 0) setIsValid(false);
				cellsToPaint[x-2][y] = selectedShip.shipSize;
			}
			catch (Exception e){
				setIsValid(false);
			}
		}
		
		if(isValid) {
			setIsValid( checkSurroundingsSeaplane(x, y, definedCells) );
		}
				
	}
	private boolean checkSurroundingsShip(int x, int y, int[][] definedCells) {
		
		if(selectedShip.orientation == ORIENTATION.TOP) {
			for(int i = selectedShip.shipSize-1; i >= 0; i--) {
				try { if(definedCells[x+1][y-i] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x][y-i+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-1][y-i] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x][y-i-1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x+1][y-i+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-1][y-i+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x+1][y-i-1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-1][y-i-1] != 0) return false; } catch(Exception e) {}
			}
		}
		else if(selectedShip.orientation == ORIENTATION.RIGHT) {
			for(int i = 0; i < selectedShip.shipSize; i++) {
				try { if(definedCells[x+i+1][y] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x+i][y+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x+i-1][y] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x+i][y-1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x+i+1][y+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x+i-1][y+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x+i+1][y-1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x+i-1][y-1] != 0) return false; } catch(Exception e) {}
			}
		}
		else if(selectedShip.orientation == ORIENTATION.DOWN) {
			for(int i = 0; i < selectedShip.shipSize; i++) {
				try { if(definedCells[x+1][y+i] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x][y+i+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-1][y+i] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x][y+i-1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x+1][y+i+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-1][y+i+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x+1][y+i-1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-1][y+i-1] != 0) return false; } catch(Exception e) {}	
			}
		}
		else if(selectedShip.orientation == ORIENTATION.LEFT) {
			for(int i = selectedShip.shipSize-1; i >= 0; i--) {
				try { if(definedCells[x-i+1][y] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-i][y+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-i-1][y] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-i][y-1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-i+1][y+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-i-1][y+1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-i+1][y-1] != 0) return false; } catch(Exception e) {}
				try { if(definedCells[x-i-1][y-1] != 0) return false; } catch(Exception e) {}
			}
		}
		
		return true;
	}
	private boolean checkSurroundingsSeaplane(int x, int y, int[][] definedCells) {
		
		try { if(definedCells[x+1][y] != 0) return false; } catch(Exception e) {}
		try { if(definedCells[x][y+1] != 0) return false; } catch(Exception e) {}
		try { if(definedCells[x-1][y] != 0) return false; } catch(Exception e) {}
		try { if(definedCells[x][y-1] != 0) return false; } catch(Exception e) {}
		
		try { if(definedCells[x+1][y+1] != 0) return false; } catch(Exception e) {}
		try { if(definedCells[x-1][y+1] != 0) return false; } catch(Exception e) {}
		try { if(definedCells[x+1][y-1] != 0) return false; } catch(Exception e) {}
		try { if(definedCells[x-1][y-1] != 0) return false; } catch(Exception e) {}
		
		if(selectedShip.orientation == ORIENTATION.TOP) {
			try { if(definedCells[x-1+1][y-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1][y-1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1-1][y-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1][y-1-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x-1+1][y-1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1-1][y-1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1+1][y-1-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1-1][y-1-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x+1][y-2] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x][y-2+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1][y-2] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x][y-2-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x+1][y-2+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1][y-2+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1][y-2-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1][y-2-1] != 0) return false; } catch(Exception e) {}

		}
		else if(selectedShip.orientation == ORIENTATION.RIGHT) {
			try { if(definedCells[x+1+1][y-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1][y-1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1-1][y-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1][y-1-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x+1+1][y-1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1-1][y-1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1+1][y-1-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1-1][y-1-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x+2+1][y] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+2][y+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+2-1][y] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+2][y-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x+2+1][y+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+2-1][y+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+2+1][y-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+2-1][y-1] != 0) return false; } catch(Exception e) {}
		}
		else if(selectedShip.orientation == ORIENTATION.DOWN) {
			try { if(definedCells[x+1+1][y+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1][y+1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1-1][y+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1][y+1-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x+1+1][y+1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1+1][y+1-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1-1][y+1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1-1][y+1-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x+1][y+2] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x][y+2+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1][y+2] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x][y+2-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x+1][y+2+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1][y+2+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x+1][y+2-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1][y+2-1] != 0) return false; } catch(Exception e) {}
		}
		else if(selectedShip.orientation == ORIENTATION.LEFT) {
			try { if(definedCells[x-1+1][y+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1][y+1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1-1][y+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1][y+1-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x-1+1][y+1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1-1][y+1+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1+1][y+1-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-1-1][y+1-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x-2+1][y] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-2][y+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-2-1][y] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-2][y-1] != 0) return false; } catch(Exception e) {}
			
			try { if(definedCells[x-2+1][y+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-2-1][y+1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-2+1][y-1] != 0) return false; } catch(Exception e) {}
			try { if(definedCells[x-2-1][y-1] != 0) return false; } catch(Exception e) {}
		}
		
		return true;
	}
	
	
	/* LISTA DE MENSAGENS */

	public void addMessage(String message) {
		messages.add(message);
		refreshBoard();
	}
		
	
	/* METODOS GET E SET */
	
	public PHASE getPhase() {
		return phase;
	}
	public void setIsValid(boolean validation) {
		isValid = validation;
	}
	public void setSelectedShip(Ship ship) {
		selectedShip = ship;
    }
	public void unsetSelectedShip() {
		if(selectedShip == null) return;
    	selectedShip.unselectPreviousShip();
		selectedShip = null;
		
		cellsToPaint = K.createEmptyGrid();
		refreshBoard();
    }
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	public int getNextPlayer() {
		if(currentPlayer == 1) {
			return 2;
		}
		else {
			return 1;
		}
	}
	public void setBoard(int playerNum) {
		switch(playerNum) {
			case 1: board1 = JP_PositioningGrid.getGrid().getFinalGrid();
			case 2: board2 = JP_PositioningGrid.getGrid().getFinalGrid();
		}
	}
	public int[][] getBoard(int playerNum) {
		switch(playerNum) {
			case 1: return board1;
			case 2: return board2;
		}
		return null;
	}
	public int[][] getOppositeBoard(int playerNum) {
		switch(playerNum) {
			case 1: return board2;
			case 2: return board1;
		}
		return null;
	}
	public String getPlayerName(int playerNum) {
		switch(playerNum) {
			case 1: return player1;
			case 2: return player2;
		}
		return null;
	}
	public void setPlayerName(int playerNum, String playerName) {
		switch(playerNum) {
		case 1: player1 = playerName;
		case 2: player2 = playerName;
		}
	}
	public Ship getSelectedShip() {
		return selectedShip;
	}

	
	/* FUNCOES DO OBSERVER */
	
	@Override
	public void addObserver(IObserver o) {
		lob.add(o);
	}
	@Override
	public void removeObserver(IObserver o) {
		lob.remove(o);
	}
	@Override
	public Object get() {
				
		Object dados[] = new Object[ K.objectValues.values().length ];
		
		dados[ K.objectValues.BOARD_1.getValue() ] 			= board1;
		dados[ K.objectValues.BOARD_2.getValue() ] 			= board2;
		dados[ K.objectValues.CURRENT_PLAYER.getValue() ] 	= currentPlayer;
		dados[ K.objectValues.RESULT.getValue() ] 			= result;
		dados[ K.objectValues.MESSAGES.getValue() ] 		= messages;
		dados[ K.objectValues.IS_VALID.getValue() ] 		= isValid;
		dados[ K.objectValues.CELLS_TO_PAINT.getValue() ] 	= cellsToPaint;
		dados[ K.objectValues.PLAYER_1_NAME.getValue() ] 	= player1;
		dados[ K.objectValues.PLAYER_2_NAME.getValue() ] 	= player2;
		
		return dados;
	}
	private void refreshBoard() {
		for(IObserver o:lob)
			o.notify(this);
	}

	/* PLAYERS DE SOM */
	
	public static synchronized void playSound(final String url) {
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(Launcher.class.getResourceAsStream("/" + url));
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}
}
