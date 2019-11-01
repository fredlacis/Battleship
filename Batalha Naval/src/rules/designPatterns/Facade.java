package rules.designPatterns;

import rules.CtrlRules;

public class Facade {
    CtrlRules ctrl;
    static Facade f=null;
    
    private Facade() {
        ctrl=new CtrlRules();
    }
    
    public static Facade getFachada() {
        if(f==null)
            f=new Facade();
        
        return f;    
    }
    
    public void cellClicked(int i,int j) {
    	ctrl.cellClicked(i, j);
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
