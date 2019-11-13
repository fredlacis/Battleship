package rules.designPatterns;

import gui.ships.Ship;
import main.K.PHASE;
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
      
    public PHASE getPhase() {
    	return ctrl.getPhase();
    }
    
    public void nextPlayer() {
    	ctrl.nextPlayer();
    }
    
	public String getPlayerName(int playerNum) {
		return ctrl.getPlayerName(playerNum);
	}
	
	public void setPlayerName(int playerNumber, String playerName) {
		ctrl.setPlayerName(playerNumber, playerName);
	}
	
	public int getCurrentPlayer() {
		return ctrl.getCurrentPlayer();
	}
	
	public int getNextPlayer() {
		return ctrl.getNextPlayer();
	}
	
	public void setTabuleiro(int player) {
		ctrl.setBoard(player);
	}
	
	public int[][] getTabuleiro(int player) {
		return ctrl.getBoard(player);
	}
	
	public void addMessage(String message) {
		ctrl.addMessage(message);
	}

	public void shipRotate() {
		ctrl.shipRotate();
	}
	
	public void startGame() {
    	ctrl.startGame();
    }
	   
	public void attack(int x, int y) {
		ctrl.attack(x, y);
	}
	
    public void register(IObserver o) {
        ctrl.addObserver(o);
    }
}
