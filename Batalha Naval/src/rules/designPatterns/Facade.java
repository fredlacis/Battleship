package rules.designPatterns;

import gui.ships.Ship;
import rules.CtrlRules;

public class Facade {
    CtrlRules ctrl;
    static Facade f=null;
    
    private Facade() {
        ctrl=new CtrlRules();
    }
    
    public static Facade getFacade() {
        if(f==null)
            f=new Facade();
        
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
    	return ctrl.selectedShip();
    }
    
    public Object[] checkPos(int x, int y) {
		return ctrl.checkPos(x, y);
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
