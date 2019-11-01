package rules;

import java.util.*;

import gui.ships.Ship;
import main.K;
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
	
	private String jogador1;
	private int tabuleiro1[][];
	
	private String jogador2;
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
	
	public String getJogador1() {
		return jogador1;
	}

	public void setJogador1(String jogador1) {
		this.jogador1 = jogador1;
	}

	public String getJogador2() {
		return jogador2;
	}

	public void setJogador2(String jogador2) {
		this.jogador2 = jogador2;
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
		
		if(checkPos(x, y)) {
			System.out.println("Posição válida. Posicionar Navio.");
		}
		System.out.println("Posição inválida.");
	}
	
	public Ship selectedShip() {
		return selectedShip;
    }
	
	public boolean checkPos(int x, int y) {
		String shipType = selectedShip.getClass().getName();
		System.out.println(shipType);
		return false;
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
