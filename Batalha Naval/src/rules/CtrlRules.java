package rules;

import java.util.*;

import gui.board.JP_PositioningGrid;
import gui.shipSelection.JP_SelectionUtilities;
import gui.shipSelection.JP_ShipOptions;
import gui.ships.Ship;
import main.K;
import main.K.ORIENTATION;
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
	
	private String player1;
	private String player2;
	
	private int tabuleiro1[][];
	private int tabuleiro2[][];
	
	private int turn;
	
	private boolean isValid;
	
	private Ship selectedShip;
	
	List<IObserver> lob = new ArrayList<IObserver>();
	
	List<String> messages = new ArrayList<String>();
	
	public CtrlRules() {
		newGame();
	}
	
	public void newGame() {
		turn=-1;
		tabuleiro1=new int[K.SQUARE_COUNT][K.SQUARE_COUNT];
		tabuleiro2=new int[K.SQUARE_COUNT][K.SQUARE_COUNT];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				tabuleiro1[i][j]=0;	
				tabuleiro2[i][j]=0;	
			}
		}
		currentPlayer = 1;
	}
	
	public void cellClicked(int i,int j) {
		
	}
	
	public int testaResultado() {
//		
//		Funcao para testar se alguem ganhou
//		
//		
		return 0;
	}
	
	public void setJogadorAtual(int jogador) {
		this.currentPlayer = jogador;
	}
	
	public int getJogadorAtual() {
		return currentPlayer;
	}
	
	public int getNextPlayer() {
		if(currentPlayer == 1) {
			return currentPlayer = 2;
		}
		else {
			return currentPlayer = 1;
		}
	}
	
	public void setTabuleiro(int jogador) {
		switch(jogador) {
			case 1: tabuleiro1 = JP_PositioningGrid.getGrid().getFinalGrid();
			case 2: tabuleiro2 = JP_PositioningGrid.getGrid().getFinalGrid();
		}
	}
	
	public int[][] getTabuleiro(int jogador) {
		switch(jogador) {
			case 1: return tabuleiro1;
			case 2: return tabuleiro2;
		}
		return null;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int vez) {
		this.turn = vez;
	}
	
	public void setSelectedShip(Ship ship) {
		selectedShip = ship;
    }
	
	public void unsetSelectedShip() {
    	selectedShip = null;
    }
	
	public String getPlayer(int player) {
		switch(player) {
			case 1: return player1;
			case 2: return player2;
		}
		return null;
	}
	
	public void setPlayer(int playerNumber, String playerName) {
		switch(playerNumber) {
		case 1: player1 = playerName;
		case 2: player2 = playerName;
		}
	}
	
	public void positionShip(int x, int y) {
		if(selectedShip == null) {
			System.out.println("Selecione um navio.");
			addMessage("Select a ship");
			return;
		}
		
		Object[] pair = new Object[2];
		int cellsToPaint[][];
		
		pair = checkPos(x, y);
		
		isValid = (boolean)pair[0];
		cellsToPaint = (int[][])pair[1];
		
		if(!selectedShip.getAvailability()) {
			System.out.println("N�o ha mais navios desse tipo: Pressione R para limpar o grid.");
			addMessage("No more ships of this type");
			return;
		}
		
		if(!isValid) {
			System.out.println("Posi��o inv�lida.");
			addMessage("Invalid position");
			return;
		}
		
		System.out.printf("Posi��o v�lida. Posicionando a partir do bloco X: %d Y: %d.\n", x+1, y+1);
		addMessage("Positioning ship");
		JP_PositioningGrid.getGrid().paintCells(cellsToPaint);
		JP_ShipOptions.getShipOptions().reduceShipCount(selectedShip);
		
	}
	
	public Ship getSelectedShip() {
		return selectedShip;
    }
	
	private Object[] checkPosShip(int x, int y){
		
		Object[] pair = new Object[2];
		
		int[][] definedCells = JP_PositioningGrid.getGrid().getFinalGrid();
		int cellsToPaint[][] = K.createEmptyGrid();
		boolean validPos = true;
		
		if(selectedShip.orientation == ORIENTATION.TOP) {
			for(int i = selectedShip.shipSize-1; i >= 0; i--) {
				try {
					if(definedCells[x][y-i] != 0) validPos = false;
					cellsToPaint[x][y-i] = selectedShip.shipSize;
				}
				catch(Exception e) {
					validPos = false;
				}
			}
		}
		if(selectedShip.orientation == ORIENTATION.RIGHT) {
			for(int i = 0; i < selectedShip.shipSize; i++) {
				try {
					if(definedCells[x+i][y] != 0) validPos = false;
					cellsToPaint[x+i][y] = selectedShip.shipSize;
				}
				catch(Exception e) {
					validPos = false;
				}
			}
		}
		if(selectedShip.orientation == ORIENTATION.DOWN) {
			for(int i = 0; i < selectedShip.shipSize; i++) {
				try {
					if(definedCells[x][y+i] != 0) validPos = false;
					cellsToPaint[x][y+i] = selectedShip.shipSize;
				}
				catch(Exception e) {
					validPos = false;
				}
			}
		}
		if(selectedShip.orientation == ORIENTATION.LEFT) {
			for(int i = selectedShip.shipSize-1; i >= 0; i--) {
				try {
					if(definedCells[x-i][y] != 0) validPos = false;
					cellsToPaint[x-i][y] = selectedShip.shipSize;
				}
				catch(Exception e) {
					validPos = false;
				}
			}
		}
		
		if(!selectedShip.getAvailability()) {
			validPos = false;
		}
				
		pair[0] = validPos;
		pair[1] = cellsToPaint;
		return pair;
	}
	
	private Object[] checkPosSeaplane(int x, int y){
		Object[] pair = new Object[2];
		
		int[][] definedCells = JP_PositioningGrid.getGrid().getFinalGrid();
		int cellsToPaint[][] = K.createEmptyGrid();
		boolean validPos = true;
		
		if(selectedShip.orientation == ORIENTATION.TOP) {
			if(definedCells[x][y] != 0) validPos = false;
			cellsToPaint[x][y] = selectedShip.shipSize;
			try {
				if(definedCells[x-1][y-1] != 0) validPos = false;
				cellsToPaint[x-1][y-1] = selectedShip.shipSize;
			}
			catch (Exception e){
				validPos = false;
			}
			try {
				if(definedCells[x][y-2] != 0) validPos = false;
				cellsToPaint[x][y-2] = selectedShip.shipSize;
			}
			catch (Exception e){
				validPos = false;
			}
		}
		if(selectedShip.orientation == ORIENTATION.RIGHT) {
			if(definedCells[x][y] != 0) validPos = false;
			cellsToPaint[x][y] = selectedShip.shipSize;
			try {
				if(definedCells[x+1][y-1] != 0) validPos = false;
				cellsToPaint[x+1][y-1] = selectedShip.shipSize;
			}
			catch (Exception e){
				validPos = false;
			}
			try {
				if(definedCells[x+2][y] != 0) validPos = false;
				cellsToPaint[x+2][y] = selectedShip.shipSize;
			}
			catch (Exception e){
				validPos = false;
			}
		}
		if(selectedShip.orientation == ORIENTATION.DOWN) {
			if(definedCells[x][y] != 0) validPos = false;
			cellsToPaint[x][y] = selectedShip.shipSize;
			try {
				if(definedCells[x+1][y+1] != 0) validPos = false;
				cellsToPaint[x+1][y+1] = selectedShip.shipSize;
			}
			catch (Exception e){
				validPos = false;
			}
			try {
				if(definedCells[x][y+2] != 0) validPos = false;
				cellsToPaint[x][y+2] = selectedShip.shipSize;
			}
			catch (Exception e){
				validPos = false;
			}
		}
		if(selectedShip.orientation == ORIENTATION.LEFT) {
			if(definedCells[x][y] != 0) validPos = false;
			cellsToPaint[x][y] = selectedShip.shipSize;
			try {
				if(definedCells[x-1][y+1] != 0) validPos = false;
				cellsToPaint[x-1][y+1] = selectedShip.shipSize;
			}
			catch (Exception e){
				validPos = false;
			}
			try {
				if(definedCells[x-2][y] != 0) validPos = false;
				cellsToPaint[x-2][y] = selectedShip.shipSize;
			}
			catch (Exception e){
				validPos = false;
			}
		}
		
		if(!selectedShip.getAvailability()) {
			validPos = false;
		}
		
		pair[0] = validPos;
		pair[1] = cellsToPaint;
		return pair;
	}
	
	public Object[] checkPos(int x, int y) {
		
		if(selectedShip == null) {
			return null;
		}
				
		if(selectedShip.getClass().getName() == "gui.ships.Seaplane") {
			return checkPosSeaplane(x, y);
		}
		return checkPosShip(x, y);
	}
	
	public void resetGrid() {
		System.out.println("Limpando Grid");
		addMessage("Reseting Grid");
		
		JP_PositioningGrid grid = JP_PositioningGrid.getGrid();
		grid.reset();
		
		JP_ShipOptions shipOptions = JP_ShipOptions.getShipOptions();
		shipOptions.resetShipCount();
		
		unsetSelectedShip();
	}
	
	public void addMessage(String message) {
		messages.add(message);
		for(IObserver o:lob)
			o.notify(this);
	}
	
	public List<String> getMessages() {
		return messages;
	}
	
	public boolean getIsValid() {
		return isValid;
	}

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
//		Object dados[]=new Object[5];
//		
//		dados[0]=tabuleiro;
//		dados[1]=new Integer(vez);
//		dados[2]=new Integer(testaResultado());
//		
//		return dados;
		return null;
	}

}
