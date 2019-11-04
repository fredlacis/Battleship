package rules.designPatterns;

import java.util.List;

import gui.board.JP_PositioningGrid;
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
    
    public void cellClicked(int i,int j) {
    	ctrl.cellClicked(i, j);
    }
    
    public void setSelectedShip(Ship ship) {
    	ctrl.setSelectedShip(ship);
    }
    
    public void unsetSelectedShip() {
    	ctrl.unsetSelectedShip();
    }
    
    public void positionShip(int x, int y) {
		ctrl.positionShip(x, y);
	}
    
    public Ship selectedShip() {
    	return ctrl.getSelectedShip();
    }
    
    public Object[] checkPos(int x, int y) {
		return ctrl.checkPos(x, y);
	}
    
    public void resetGrid() {
		ctrl.resetGrid();
	}
    
    public void setJogadorAtual(int jogador) {
    	ctrl.setJogadorAtual(jogador);
    }
    
	public String getPlayer(int player) {
		return ctrl.getPlayer(player);
	}
	
	public void setPlayer(int playerNumber, String playerName) {
		ctrl.setPlayer(playerNumber, playerName);
	}
	
	public int getNextPlayer() {
		return ctrl.getNextPlayer();
	}
    
	public int getJogadorAtual() {
		return ctrl.getJogadorAtual();
	}
	
	public void setTabuleiro(int jogador) {
		ctrl.setTabuleiro(jogador);
	}
	
	public int[][] getTabuleiro(int jogador) {
		return ctrl.getTabuleiro(jogador);
	}
	
	public void addMessage(String message) {
		ctrl.addMessage(message);
	}
	
	public List<String> getMessages() {
		return ctrl.getMessages();
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
