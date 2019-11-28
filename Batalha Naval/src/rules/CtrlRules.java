package rules;

import java.io.Serializable;
import java.util.*;

import gui.shipPositioning.JP_PositioningGrid;
import gui.shipPositioning.JP_ShipOptions;
import gui.ships.Battleship;
import gui.ships.Cruiser;
import gui.ships.Destroyer;
import gui.ships.Seaplane;
import gui.ships.Ship;
import gui.ships.Submarine;
import main.K;
import main.K.ORIENTATION;
import main.K.PHASE;
import main.K.SHIPS;
import rules.designPatterns.IObservable;
import rules.designPatterns.IObserver;

public class CtrlRules implements IObservable, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/* ATRIBUTOS OBSERVABLE */
	
	private int board1[][];
	private int board2[][];
	private int currentPlayer;
	private boolean result = false;
	private boolean isValid;
	private int cellsToPaint[][];
	private String player1;
	private String player2;
	List<String> messages = new ArrayList<String>();
	
	
	/* LISTA DE OBSERVERS */
	
	List<IObserver> lob = new ArrayList<IObserver>();
	
	
	/* ATRIBUTOS N�O OBSERVABLE */
	
	private PHASE phase;
	private Ship selectedShip;
	private int pointsPlayer1 = 0;
	private int pointsPlayer2 = 0;
	private int currentAttackCount = 1;
	
	
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
	public void resetGame() {
		board1 = K.createEmptyGrid();
		board2 = K.createEmptyGrid();
		currentPlayer = 1;
		result = false;
		isValid = false;
		cellsToPaint = K.createEmptyGrid();
		player1 = "";
		player2 = "";
		messages.clear();
		lob.clear();
		
		phase = PHASE.POSITION;
		selectedShip = null;
		pointsPlayer1 = 0;
		pointsPlayer2 = 0;
		currentAttackCount = 1;
		
		resetGrid();
		
		refreshBoard();
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
		
		if(!isValid) {
			addMessage("Invalid position");
			refreshBoard();
			return;
		}
		
		addMessage("Positioning ship");
		JP_PositioningGrid.getGrid().paintCells(cellsToPaint);
		JP_ShipOptions.getShipOptions().reduceShipCount(selectedShip);
		
		if(!selectedShip.getAvailability()) {
			unsetSelectedShip();
			refreshBoard();
			return;
		}
		
		cellsToPaint = K.createEmptyGrid();
		refreshBoard();
		
	}
	public void repositionShip(int x, int y, int[][] definedCells) {
		
		isValid = true;
		addMessage("Repositioning Ship");		
		
		setSelectedShipBySize(definedCells[x][y]);
		definedCells = removeShip(x, y, definedCells);
		
		if(currentPlayer == 1)
			board1 = definedCells;
		else
			board2 = definedCells;
		
		JP_PositioningGrid.getGrid().paintCells(definedCells);
		JP_ShipOptions.getShipOptions().increaseShipCount(selectedShip);
		
		K.printGrid(definedCells);
		
		refreshBoard();
		
	}
	public void resetGrid() {
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
	
	private int[][] removeShip(int x, int y, int[][] definedCells){
		try { 
			//LEFT-RIGHT -> Reach left end and go to right end
			if(definedCells[x+1][y] > 0) {
				try {
					while(definedCells[x][y] != 0) {
						x--;
					}
				} catch(Exception e) {   }

				//Reached end => sum 1 to x to get back to ship
				x += 1;

				//Beginning left to right removal
				try {
					while(definedCells[x][y] != 0) {
						definedCells[x][y] = 0;
						x++;
					}
				} catch(Exception e) { }
			}; 
		} catch(Exception e) { }
		try { 
			//LEFT-RIGHT -> Reach left end and go to right end
			if(definedCells[x-1][y] > 0) {
				try {
					while(definedCells[x][y] != 0) {
						x--;
					}
				} catch(Exception e) { }

				//Reached end => sum 1 to x to get back to ship
				x += 1;

				//Beginning left to right removal
				try {
					while(definedCells[x][y] != 0) {
						definedCells[x][y] = 0;
						x++;
					}
				} catch(Exception e) { }
			}; 
		} catch(Exception e) { }
		try { 
			//BOTTOM-TOP -> Reach bottom and go to top end
			if(definedCells[x][y+1] > 0) {
				try {
					while(definedCells[x][y] != 0) {
						y--;
					}
				} catch(Exception e) { }

				//Reached end => sum 1 to y to get back to ship
				y += 1;

				//Beginning bottom to top removal
				try {
					while(definedCells[x][y] != 0) {
						definedCells[x][y] = 0;
						y++;
					}
				} catch(Exception e) { }
			}; 
		} catch(Exception e) { }	
		try { 
			//BOTTOM-TOP -> Reach bottom and go to top end
			if(definedCells[x][y-1] > 0) {
				try {
					while(definedCells[x][y] != 0) {
						y--;
					}
				} catch(Exception e) { }

				//Reached end => sum 1 to y to get back to ship
				y += 1;

				//Beginning bottom to top removal
				try {
					while(definedCells[x][y] != 0) {
						definedCells[x][y] = 0;
						y++;
					}
				} catch(Exception e) { }
			};
		} catch(Exception e) { }
		
		return definedCells;
	}
	private void setSelectedShipBySize(int shipSize) {
		if(shipSize == 1) {
			setSelectedShip(Submarine.getSubmarine());
		}
		else if(shipSize == 2) {
			setSelectedShip(Destroyer.getDestroyer());
		}
		else if(shipSize == 3) {
			setSelectedShip(Seaplane.getSeaplane());
		}
		else if(shipSize == 4) {
			setSelectedShip(Cruiser.getCruiser());
		}
		else if(shipSize == 5) {
			setSelectedShip(Battleship.getBattleship());
		}
	}
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
		
		if(getOppositeBoard(currentPlayer)[x][y] == SHIPS.D_WATER.getValue() || getOppositeBoard(currentPlayer)[x][y] < 0) {
			addMessage("This cell was already clicked!");
			return;
		}
		else if(getOppositeBoard(currentPlayer)[x][y] > 0 && getOppositeBoard(currentPlayer)[x][y] < SHIPS.D_WATER.getValue()) {
			addMessage(getPlayerName(currentPlayer) + " hit a " + K.getShipNameBySize(getOppositeBoard(currentPlayer)[x][y]) + "!");
			attackShip(x, y);
		}
		else if(getOppositeBoard(currentPlayer)[x][y] == 0) {			
			addMessage(getPlayerName(currentPlayer) + " missed!");
			attackShip(x, y);
		}
		
		if(currentAttackCount == 3 ) {	
			currentAttackCount = 1;
			nextPlayer();
		}
		else {
			checkResult();
			currentAttackCount++;
			refreshBoard();
		}
	
	}

	
	/* FUNCOES PRIVADAS PARA FASE DE ATAQUES */
	
	private void attackShip(int x, int y) {
		int[][] currentBoard = getOppositeBoard(currentPlayer);
		int currentPlayerPoints = 0;
		
		if(currentBoard[x][y] == 0) {
			currentBoard[x][y] = SHIPS.D_WATER.getValue();
		}
		else if(currentBoard[x][y] > 0) {
			currentPlayerPoints += 1;
			currentBoard[x][y] = -currentBoard[x][y];
			
			if(currentBoard[x][y] == SHIPS.D_SEAPLANE.getValue()) {
				if(checkAndDestroySeaplane(x, y)) {
					addMessage(getPlayerName(currentPlayer) + " sinked a Seaplane !");
				}
			}
			else if(checkIfShipDestroyed(x, y)) {
				destroyShip(x, y);
			}
		}
		
		switch(currentPlayer) {
			case 1: 
				pointsPlayer1 += currentPlayerPoints;
				break;
			case 2: 
				pointsPlayer2 += currentPlayerPoints;
				break;
		}
	}
	private boolean checkIfShipDestroyed(int x, int y) {
		
		int[][] currentBoard = getOppositeBoard(currentPlayer);
		int destroyedCellsNum = 0;
		
		if(currentBoard[x][y] == SHIPS.D_SUBMARINE.getValue()) {
			return true;
		}

		int originalX = x, originalY = y;

		try { 
			//LEFT-RIGHT -> Reach left end and go to right end
			if(currentBoard[x+1][y] < 0) {
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						x--;
					}
				} catch(Exception e) {   }

				//Reached end => sum 1 to x to get back to ship
				x += 1;

				//Beginning left to right check
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						if(currentBoard[x][y] < 0) {
							destroyedCellsNum++;
						}
						x++;
					}
				} catch(Exception e) { }
			}; 
		} catch(Exception e) { }
		try { 
			//LEFT-RIGHT -> Reach left end and go to right end
			if(currentBoard[x-1][y] < 0) {
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						x--;
					}
				} catch(Exception e) { }

				//Reached end => sum 1 to x to get back to ship
				x += 1;

				//Beginning left to right check
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						if(currentBoard[x][y] < 0) {
							destroyedCellsNum++;
						}
						x++;
					}
				} catch(Exception e) { }
			}; 
		} catch(Exception e) { }
		try { 
			//BOTTOM-TOP -> Reach bottom and go to top end
			if(currentBoard[x][y+1] < 0) {
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						y--;
					}
				} catch(Exception e) { }

				//Reached end => sum 1 to y to get back to ship
				y += 1;

				//Beginning bottom to top check
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						if(currentBoard[x][y] < 0) {
							destroyedCellsNum++;
						}
						y++;
					}
				} catch(Exception e) { }
			}; 
		} catch(Exception e) { }	
		try { 
			//BOTTOM-TOP -> Reach bottom and go to top end
			if(currentBoard[x][y-1] < 0) {
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						y--;
					}
				} catch(Exception e) { }

				//Reached end => sum 1 to y to get back to ship
				y += 1;

				//Beginning bottom to top check
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						if(currentBoard[x][y] < 0) {
							destroyedCellsNum++;
						}
						y++;
					}
				} catch(Exception e) { }
			};
		} catch(Exception e) { }
		
		return checkDamage(-currentBoard[originalX][originalY], destroyedCellsNum);
	}
	private boolean checkDamage(int shipSize, int destroyedCellsNum) {
		return shipSize == destroyedCellsNum;
	}
	private void destroyShip(int x, int y) {
		int[][] currentBoard = getOppositeBoard(currentPlayer);

		addMessage(getPlayerName(currentPlayer) + " sinked a " + K.getShipNameBySize(getOppositeBoard(currentPlayer)[x][y]) + "!");
		
		if(currentBoard[x][y] == SHIPS.D_SUBMARINE.getValue()) {
			currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
			return;
		}	

		try { 
			//LEFT-RIGHT -> Reach left end and delete
			if(currentBoard[x+1][y] != 0 && currentBoard[x+1][y] != SHIPS.D_WATER.getValue()) {
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						x--;
					}
				} catch(Exception e) {}

				//Reached end => sum 1 to x to get back to ship
				x += 1;

				//Beginning left to right removal
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						x++;
					}
				} catch(Exception e) {}
			}; 
		} catch(Exception e) {}
		try { 
			//LEFT-RIGHT -> Reach left end and delete
			if(currentBoard[x-1][y] != 0 && currentBoard[x-1][y] != SHIPS.D_WATER.getValue()) {
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						x--;
					}
				} catch(Exception e) {}

				//Reached end => sum 1 to x to get back to ship
				x += 1;

				//Beginning left to right removal
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						x++;
					}
				} catch(Exception e) {}
			}; 
		} catch(Exception e) {}
		try { 
			//BOTTOM-TOP -> Reach bottom end and delete
			if(currentBoard[x][y+1] != 0 && currentBoard[x][y+1] != SHIPS.D_WATER.getValue()) {
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						y--;
					}
				} catch(Exception e) {}

				//Reached end => sum 1 to y to get back to ship
				y += 1;

				//Beginning bottom to top removal
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						y++;
					}
				} catch(Exception e) {}
			}; 
		} catch(Exception e) {}	
		try { 
			//BOTTOM-TOP -> Reach bottom end and delete
			if(currentBoard[x][y-1] != 0 && currentBoard[x][y-1] != SHIPS.D_WATER.getValue()) {
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						y--;
					}
				} catch(Exception e) {}

				//Reached end => sum 1 to y to get back to ship
				y += 1;

				//Beginning bottom to top removal
				try {
					while(currentBoard[x][y] != 0 && currentBoard[x][y] != SHIPS.D_WATER.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						y++;
					}
				} catch(Exception e) {}
			}; 
		} catch(Exception e) {}
	}
	private boolean checkAndDestroySeaplane(int x, int y) {
		int[][] currentBoard = getOppositeBoard(currentPlayer);
		
		try {
			if(currentBoard[x+1][y+1] == SHIPS.D_SEAPLANE.getValue()) {
				//Check if block on middle of Seaplane
				try {
					if(currentBoard[x+1][y-1] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x+1][y+1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x+1][y-1] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
				//Check if block on end of Seaplane
				try {
					if(currentBoard[x][y+2] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x+1][y+1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x][y+2] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
				try {
					if(currentBoard[x+2][y] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x+1][y+1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x+2][y] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
			}
		}catch(Exception e) {}
		
		try {
			if(currentBoard[x+1][y-1] == SHIPS.D_SEAPLANE.getValue()) {
				//Check if block on middle of Seaplane
				try {
					if(currentBoard[x-1][y-1] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x+1][y-1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x-1][y-1] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
				//Check if block on end of Seaplane
				try {
					if(currentBoard[x+2][y] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x+1][y-1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x+2][y] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
				try {
					if(currentBoard[x][y-2] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x+1][y-1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x][y-2] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
			}
		}catch(Exception e) {}
		try {
			if(currentBoard[x-1][y-1] == SHIPS.D_SEAPLANE.getValue()) {
				//Check if block on middle of Seaplane
				try {
					if(currentBoard[x-1][y+1] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x-1][y-1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x-1][y+1] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
				//Check if block on end of Seaplane
				try {
					if(currentBoard[x][y-2] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x-1][y-1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x][y-2] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
				try {
					if(currentBoard[x-2][y] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x-1][y-1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x-2][y] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
			}
		}catch(Exception e) {}
		try {
			if(currentBoard[x-1][y+1] == SHIPS.D_SEAPLANE.getValue()) {
				//Check if block on middle of Seaplane
				try {
					if(currentBoard[x+1][y+1] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x-1][y+1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x+1][y+1] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
				//Check if block on end of Seaplane
				try {
					if(currentBoard[x-2][y] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x-1][y+1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x-2][y] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
				
				try {
					if(currentBoard[x][y+2] == SHIPS.D_SEAPLANE.getValue()) {
						currentBoard[x][y] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x-1][y+1] -= K.DESTROYED_SHIP_LIMIT;
						currentBoard[x][y+2] -= K.DESTROYED_SHIP_LIMIT;
						return true;
					}
				}catch(Exception e) {}
			}
		}catch(Exception e) {}
		
		return false;
		
	}
	private void checkResult() {
		
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
	
	/* LISTA DE MENSAGENS */

	public void addMessage(String message) {
		messages.add(message);
		refreshBoard();
	}
	public void emptyMessagesList() {
		messages.clear();
	}
	
	
	/* METODOS GET E SET */
	
	public PHASE getPhase() {
		return phase;
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
	
	private void setIsValid(boolean validation) {
		isValid = validation;
	}
	private int[][] getOppositeBoard(int playerNum) {
		switch(playerNum) {
			case 1: return board2;
			case 2: return board1;
		}
		return null;
	}
	
	
	/* FUNCOES DO OBSERVER */
	
	@Override
	public void addObserver(IObserver o) {
		for(IObserver ob:lob)
			if(o == ob)
				return;
				
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
		dados[ K.objectValues.IS_VALID.getValue() ] 		= isValid;
		dados[ K.objectValues.CELLS_TO_PAINT.getValue() ] 	= cellsToPaint;
		dados[ K.objectValues.PLAYER_1_NAME.getValue() ] 	= player1;
		dados[ K.objectValues.PLAYER_2_NAME.getValue() ] 	= player2;
		dados[ K.objectValues.MESSAGES.getValue() ] 		= messages;
		
		return dados;
	}
	public void refreshBoard() {
		for(IObserver o:lob)
			o.notify(this);
	}

}
