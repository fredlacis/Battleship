package rules.designPatterns;

import gui.ships.Ship;
import rules.CtrlRules;

public class RulesFacade {
    CtrlRules ctrl;
    static RulesFacade f=null;
    
    private RulesFacade() {
        ctrl=new CtrlRules();
    }
    
    public static RulesFacade getRules() {
        if(f==null)
            f=new RulesFacade();
        
        return f;    
    }
    
    public void setSelectedShip(Ship ship) {
    	ctrl.setSelectedShip(ship);
    }
    
    public void unsetSelectedShip() {
    	ctrl.unsetSelectedShip();
    }
    
    public void positionShip(int x, int y, int[][] definedCells) {
		ctrl.positionShip(x, y, definedCells);
	}
    
    public Ship getSelectedShip() {
    	return ctrl.getSelectedShip();
    }
    
    public void checkPos(int x, int y, int[][] definedCells) {
		ctrl.checkPos(x, y, definedCells);
	}
    
    public void resetGrid() {
		ctrl.resetGrid();
	}
    
    public void setCurrentPlayer(int jogador) {
    	ctrl.setCurrentPlayer(jogador);
    }
    
	public String getCurrentPlayer() {
		return ctrl.getCurrentPlayerName();
	}
	
	public void setPlayer(int playerNumber, String playerName) {
		ctrl.setPlayer(playerNumber, playerName);
	}
	
	public int getNextPlayer() {
		return ctrl.getNextPlayer();
	}
    
	public int getJogadorAtual() {
		return ctrl.getCurrentPlayer();
	}
	
	public void setTabuleiro(int jogador) {
		ctrl.setBoard(jogador);
	}
	
	public int[][] getTabuleiro(int jogador) {
		return ctrl.getBoard(jogador);
	}
	
	public void addMessage(String message) {
		ctrl.addMessage(message);
	}

	public void shipRotate() {
		ctrl.shipRotate();
	}
//    
//    public void novoJogo() {
//        ctrl.novoJogo();
//    }
//        
//    public int getVez() {
//        return ctrl.getVez();
//    }
//    
//    public void celulaClicked(int i,int j) {
//        ctrl.celulaClicked(i,j);
//    }
//    
//    public int testaResultado() {
//        return ctrl.testaResultado();
//    }
//    
    public void register(IObserver o) {
        ctrl.addObserver(o);
    }
}
