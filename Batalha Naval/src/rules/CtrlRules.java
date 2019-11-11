package rules;

import java.util.*;

import gui.board.JP_PositioningGrid;
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

	private int board1[][];
	private int board2[][];

	private boolean isValid;

	private Ship selectedShip;
	
	private int cellsToPaint[][];

	List<IObserver> lob = new ArrayList<IObserver>();

	List<String> messages = new ArrayList<String>();
	
	/*
	 * CONSTRUTOR
	 */

	public CtrlRules() {
		newGame();
	}
	
	public void newGame() {
		board1=new int[K.SQUARE_COUNT][K.SQUARE_COUNT];
		board2=new int[K.SQUARE_COUNT][K.SQUARE_COUNT];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				board1[i][j]=0;	
				board2[i][j]=0;	
			}
		}
		currentPlayer = 1;
	}
	
	/*
	 * RESULTADO
	 */
	
	public int checkResult() {
//		
//		Funcao para testar se alguem ganhou
//		
//		
		return 0;
	}
	
	/*
	 * FUNCOES DO TABULEIRO
	 */
	
	public void positionShip(int x, int y, int[][] definedCells) {
		if(selectedShip == null) {
			System.out.println("Selecione um navio.");
			setIsValid(false);
			addMessage("Select a ship");
			return;
		}
				
		checkPos(x, y, definedCells);
				
		if(!selectedShip.getAvailability()) {
			//System.out.println("N�o ha mais navios desse tipo: Pressione R para limpar o grid.");
			addMessage("No more ships of this type");
			for(IObserver o:lob)
				o.notify(this);
			return;
		}
		
		if(!isValid) {
			//System.out.println("Posi��o inv�lida.");
			addMessage("Invalid position");
			for(IObserver o:lob)
				o.notify(this);
			return;
		}
		
		//System.out.printf("Posi��o v�lida. Posicionando a partir do bloco X: %d Y: %d.\n", x+1, y+1);
		addMessage("Positioning ship");
		JP_PositioningGrid.getGrid().paintCells(cellsToPaint);
		JP_ShipOptions.getShipOptions().reduceShipCount(selectedShip);
		
		for(IObserver o:lob)
			o.notify(this);
		
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
		
		checkPosShip(x, y, definedCells);
	}
	
	private void checkPosShip(int x, int y, int[][] definedCells){
				
		cellsToPaint = K.createEmptyGrid();
				
		setIsValid(true);
		
		if(!selectedShip.getAvailability()) {
			setIsValid(false);
			return;
		}
		
		if(selectedShip.orientation == ORIENTATION.TOP) {
			for(int i = selectedShip.shipSize-1; i >= 0; i--) {
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
			for(int i = selectedShip.shipSize-1; i >= 0; i--) {
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
			return;
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
			return;
		}
		if(selectedShip.orientation == ORIENTATION.RIGHT) {
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
			return;
		}
		if(selectedShip.orientation == ORIENTATION.DOWN) {
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
			return;
		}
		if(selectedShip.orientation == ORIENTATION.LEFT) {
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
			return;
		}
		
		if(isValid) {
			setIsValid( checkSurroundingsSeaplane(x, y, definedCells) );
		}
		
	}
	
	private boolean checkSurroundingsShip(int x, int y, int[][] definedCells) {
		
		if(selectedShip.orientation == ORIENTATION.TOP) {
			for(int i = selectedShip.shipSize-1; i >= 0; i--) {
				try {
					if(definedCells[x+1][y-i] != 0) return false;
					if(definedCells[x][y-i+1] != 0) return false;
					if(definedCells[x-1][y-i] != 0) return false;
					if(definedCells[x][y-i-1] != 0) return false;
				}
				catch(Exception e) {}
			}
		}
		else if(selectedShip.orientation == ORIENTATION.RIGHT) {
			for(int i = 0; i < selectedShip.shipSize; i++) {
				try {
					if(definedCells[x+i+1][y] != 0) return false;
					if(definedCells[x+i][y+1] != 0) return false;
					if(definedCells[x+i-1][y] != 0) return false;
					if(definedCells[x+i][y-1] != 0) return false;
				}
				catch(Exception e) {}
			}
		}
		else if(selectedShip.orientation == ORIENTATION.DOWN) {
			for(int i = 0; i < selectedShip.shipSize; i++) {
				try {
					if(definedCells[x+1][y+i] != 0) return false;
					if(definedCells[x][y+i+1] != 0) return false;
					if(definedCells[x-1][y+i] != 0) return false;
					if(definedCells[x][y+i-1] != 0) return false;
				}
				catch(Exception e) {}
			}
		}
		else if(selectedShip.orientation == ORIENTATION.LEFT) {
			for(int i = selectedShip.shipSize-1; i >= 0; i--) {
				try {
					if(definedCells[x-i+1][y] != 0) return false;
					if(definedCells[x-i][y+1] != 0) return false;
					if(definedCells[x-i-1][y] != 0) return false;
					if(definedCells[x-i][y-1] != 0) return false;
				}
				catch(Exception e) {}
			}
		}
		
		return true;
	}
		
	private boolean checkSurroundingsSeaplane(int x, int y, int[][] definedCells) {
		
		try {
			if(definedCells[x+1][y] != 0) return false;
			if(definedCells[x][y+1] != 0) return false;
			if(definedCells[x-1][y] != 0) return false;
			if(definedCells[x][y-1] != 0) return false;
		}
		catch(Exception e) {}
		
		if(selectedShip.orientation == ORIENTATION.TOP) {
			try {
				if(definedCells[x-1+1][y-1] != 0) return false;
				if(definedCells[x-1][y-1+1] != 0) return false;
				if(definedCells[x-1-1][y-1] != 0) return false;
				if(definedCells[x-1][y-1-1] != 0) return false;
				
				if(definedCells[x+1][y-2] != 0) return false;
				if(definedCells[x][y-2+1] != 0) return false;
				if(definedCells[x-1][y-2] != 0) return false;
				if(definedCells[x][y-2-1] != 0) return false;
			}
			catch(Exception e) {}
		}
		else if(selectedShip.orientation == ORIENTATION.RIGHT) {
			try {
				if(definedCells[x+1+1][y-1] != 0) return false;
				if(definedCells[x+1][y-1+1] != 0) return false;
				if(definedCells[x+1-1][y-1] != 0) return false;
				if(definedCells[x+1][y-1-1] != 0) return false;
				
				if(definedCells[x+2+1][y] != 0) return false;
				if(definedCells[x+2][y+1] != 0) return false;
				if(definedCells[x+2-1][y] != 0) return false;
				if(definedCells[x+2][y-1] != 0) return false;
			}
			catch(Exception e) {}
		}
		else if(selectedShip.orientation == ORIENTATION.DOWN) {
			try {
				if(definedCells[x+1+1][y+1] != 0) return false;
				if(definedCells[x+1][y+1+1] != 0) return false;
				if(definedCells[x+1-1][y+1] != 0) return false;
				if(definedCells[x+1][y+1-1] != 0) return false;
				
				if(definedCells[x+1][y+2] != 0) return false;
				if(definedCells[x][y+2+1] != 0) return false;
				if(definedCells[x-1][y+2] != 0) return false;
				if(definedCells[x][y+2-1] != 0) return false;
			}
			catch(Exception e) {}
		}
		else if(selectedShip.orientation == ORIENTATION.LEFT) {
			try {
				if(definedCells[x-1+1][y+1] != 0) return false;
				if(definedCells[x-1][y+1+1] != 0) return false;
				if(definedCells[x-1-1][y+1] != 0) return false;
				if(definedCells[x-1][y+1-1] != 0) return false;
				
				if(definedCells[x-2+1][y] != 0) return false;
				if(definedCells[x-2][y+1] != 0) return false;
				if(definedCells[x-2-1][y] != 0) return false;
				if(definedCells[x-2][y-1] != 0) return false;
			}
			catch(Exception e) {}
		}
		
		return true;
	}
	
	public void shipRotate() {
		selectedShip.rotate();
		for(IObserver o:lob)
			o.notify(this);
	}
	
	/*
	 * LISTA DE MENSAGENS
	 */

	public void addMessage(String message) {
		messages.add(message);
		for(IObserver o:lob)
			o.notify(this);
	}
		
	/*
	 * METODOS GET E SET
	 */
	public void setIsValid(boolean validation) {
		isValid = validation;
		for(IObserver o:lob)
			o.notify(this);
	}
	
	public void setSelectedShip(Ship ship) {
		selectedShip = ship;
    }

	public void unsetSelectedShip() {
    	selectedShip.unselectPreviousShip();
		selectedShip = null;
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
	
	public void setBoard(int jogador) {
		switch(jogador) {
			case 1: board1 = JP_PositioningGrid.getGrid().getFinalGrid();
			case 2: board2 = JP_PositioningGrid.getGrid().getFinalGrid();
		}
	}
	
	public int[][] getBoard(int jogador) {
		switch(jogador) {
			case 1: return board1;
			case 2: return board2;
		}
		return null;
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
	
	public Ship getSelectedShip() {
		
		return selectedShip;
	}
	
	/*
	 * FUNCOES DO OBSERVER
	 */
	
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
		
		//K.printGrid(cellsToPaint);
		
		Object dados[] = new Object[ K.objectValues.values().length ];
		
		dados[ K.objectValues.BOARD_1.getValue() ] 			= board1;
		dados[ K.objectValues.BOARD_2.getValue() ] 			= board2;
		dados[ K.objectValues.CURRENT_PLAYER.getValue() ] 	= new Integer(currentPlayer);
		dados[ K.objectValues.RESULT.getValue() ] 			= new Integer(checkResult());
		dados[ K.objectValues.MESSAGES.getValue() ] 		= messages;
		dados[ K.objectValues.IS_VALID.getValue() ] 		= new Boolean(isValid);
		dados[ K.objectValues.CELLS_TO_PAINT.getValue() ] 	= cellsToPaint;
		
		return dados;
	}

}
