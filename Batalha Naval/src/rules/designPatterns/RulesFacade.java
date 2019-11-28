package rules.designPatterns;

import gui.ships.Ship;
import main.K.PHASE;
import rules.CtrlRules;

public class RulesFacade {
    CtrlRules ctrl;
    static RulesFacade rulesFacade=null;
    
    private RulesFacade() {
        ctrl=new CtrlRules();
    }
    public static RulesFacade getRules() {
        if(rulesFacade==null)
            rulesFacade=new RulesFacade();
        
        return rulesFacade;    
    }
    
    public void selfDestroy() {
    	rulesFacade = null;
    }
    
    public void overrideCtrl(CtrlRules newCtrl) {
        if(newCtrl==null) {
        	return;
        }
           
        ctrl = newCtrl;
    }
    
	public void resetGame() {
		ctrl.resetGame();
	}
    
    /* POSICIONAMENTO DO TABULEIRO */
    
    public void shipRotate() {
		ctrl.shipRotate();
	}
    public void positionShip(int x, int y, int[][] definedCells) {
		ctrl.positionShip(x, y, definedCells);
	}
    public void repositionShip(int x, int y, int[][] definedCells) {
    	ctrl.repositionShip(x, y, definedCells);
    }
    public void resetGrid() {
		ctrl.resetGrid();
	}
    public void checkPos(int x, int y, int[][] definedCells) {
		ctrl.checkPos(x, y, definedCells);
	}
    
    
    /* FASE DE ATAQUES */
    
    public void startGame() {
    	ctrl.startGame();
    }
    public void nextPlayer() {
    	ctrl.nextPlayer();
    }
    public void attack(int x, int y) {
		ctrl.attack(x, y);
	}
	
    
    /* GET E SET */
    
    public PHASE getPhase() {
    	return ctrl.getPhase();
    }
    public void setSelectedShip(Ship ship) {
    	ctrl.setSelectedShip(ship);
    }
    public void unsetSelectedShip() {
    	ctrl.unsetSelectedShip();
    }
    public int getCurrentPlayer() {
		return ctrl.getCurrentPlayer();
	}
    public int getNextPlayer() {
		return ctrl.getNextPlayer();
	}
	public void setBoard(int player) {
		ctrl.setBoard(player);
	}
	public String getPlayerName(int playerNum) {
		return ctrl.getPlayerName(playerNum);
	}
    public void setPlayerName(int playerNumber, String playerName) {
		ctrl.setPlayerName(playerNumber, playerName);
	}
    public Ship getSelectedShip() {
    	return ctrl.getSelectedShip();
    }
	
    
    /* LISTA DE MENSAGENS */
    
	public void addMessage(String message) {
		ctrl.addMessage(message);
	}
	public void emptyMessagesList() {
		ctrl.emptyMessagesList();
	}
	
	
	/* FUNCOES OBSERVER */
	
    public void register(IObserver o) {
        ctrl.addObserver(o);
    }
    
    
    /* SAVE/LOAD */
    
    public CtrlRules getCtrl() {
    	return ctrl;
    }

}
