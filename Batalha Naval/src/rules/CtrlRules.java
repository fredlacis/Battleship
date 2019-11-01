package rules;

import java.awt.geom.Rectangle2D;
import java.util.*;

import gui.board.Cell;
import gui.ships.Ship;
import main.K;
import main.K.ORIENTATION;
import rules.designPatterns.IObservable;
import rules.designPatterns.IObserver;

public class CtrlRules implements IObservable{
		
	enum Ships{
		//NAME    -> Alive cell of ship
		//D_NAME  -> Destroyed cell of ship
		BATTLESHIP(1),
		D_BATTLESHIP(-1),
		CRUISER(2),
		D_CRUISER(-2),
		DESTROYER(3),
		D_DESTROYER(-3),
		SUBMARINE(4),
		D_SUBMARINE(-4),
		SEAPLANE(5),
		D_SEAPLANE(-5);
		
		private final int value;

        Ships(final int newValue) {
            value = newValue;
        }

        public int getValue() { return value; }
	}
	
	private int jogadorAtual;
	
	private int tabuleiro1[][];
	private int tabuleiro2[][];
	
	private int turn;
	
	private Ship selectedShip;
	
	List<IObserver> lob = new ArrayList<IObserver>();
	
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
		setJogadorAtual(1);
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
		this.jogadorAtual = jogador;
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
		String shipType = selectedShip.getClass().getName();
		System.out.printf("Selecionando navio: %s\n", shipType);
    }
	
	public void unsetSelectedShip() {
		String shipType = selectedShip.getClass().getName();
		System.out.printf("Deselecionando navio: %s\n", shipType);
    	selectedShip = null;
    }
	
	public void positionShip(int x, int y) {
		if(selectedShip == null) {
			System.out.println("Selecione um navio.");
			return;
		}
		
		if(checkPos(x, y) != null) {
			System.out.println("Posição válida. Posicionar Navio.");
		}
		
		System.out.println("Posição inválida.");
	}
	
	public Ship selectedShip() {
		return selectedShip;
    }
	
	private Object[] checkPosShip(int x, int y){
		
		Object[] pair = new Object[2];
		int tabuleiroAtual[][] = getTabuleiro(jogadorAtual);
		
		int cellsToPaint[][] = K.cloneGrid(tabuleiroAtual);
		boolean validPos = true;
		
		if(selectedShip.orientation == ORIENTATION.TOP) {
			for(int i = selectedShip.shipSize-1; i >= 0; i--) {
				try {
					if(cellsToPaint[x][y-i] != 0) validPos = false;
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
					if(cellsToPaint[x+i][y] != 0) validPos = false;
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
					if(cellsToPaint[x][y+i] != 0) validPos = false;
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
					if(cellsToPaint[x-i][y] != 0) validPos = false;
					cellsToPaint[x-i][y] = selectedShip.shipSize;
				}
				catch(Exception e) {
					validPos = false;
				}
			}
		}
		
		pair[0] = validPos;
		pair[1] = cellsToPaint;
		return pair;
	}
	
	private Object[] checkPosSeaplane(int x, int y){
		return null;
	}
	
	
	public Object[] checkPos(int x, int y) {
		
		if(selectedShip == null) {
			System.out.println("Selecione um navio.");
			return null;
		}
		
		System.out.printf("Positioning ship from position X: %d Y: %d\n", x, y);
		
		if(selectedShip.getClass().getName() == "gui.ships.Seaplane") {
			return checkPosSeaplane(x, y);
		}
		return checkPosShip(x, y);
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
