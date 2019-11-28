package gui.shipPositioning;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import gui.JP_Title;
import main.K;
import rules.designPatterns.IObservable;
import rules.designPatterns.IObserver;
import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public class JF_ShipSelection extends JFrame implements KeyListener, IObserver{
	
	JP_Title titlePanel = new JP_Title("");
		
	static JF_ShipSelection shipSelection;
    
    public static JF_ShipSelection getShipSelection() {
        if(shipSelection == null)
        	shipSelection = new JF_ShipSelection();
        
        return shipSelection;    
        
    }
    
	public void selfDestroy() {
		shipSelection = null;
	}
	
	private JF_ShipSelection() {
		RulesFacade.getRules().register(this);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int sl = screenSize.width;
		int sa = screenSize.height;
		int x = sl/2-K.LARG_DEFAULT/2;
		int y = sa/2-K.ALT_DEFAULT/2;
		setBounds(x,y,K.LARG_DEFAULT,K.ALT_DEFAULT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		getContentPane().setBackground(new Color(250, 250, 250));
			
		setFocusable(true);
		
		getContentPane().add(titlePanel);
		getContentPane().add(JP_ShipOptions.getShipOptions());
		getContentPane().add(new JP_ShipPlacement());
		getContentPane().add(JP_SelectionUtilities.getSelectionUtilites());
		
		int currentPlayerNum = RulesFacade.getRules().getCurrentPlayer();
		setTitle("Ship Selection - " + RulesFacade.getRules().getPlayerName(currentPlayerNum));
		
		addKeyListener(this);
	}
	
	public void setTitle(String title) {
		titlePanel.setText(title);
	}
	
	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent k) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent k) {
		int VK_ESCAPE = 27;
		
		if((int)k.getKeyChar() == VK_ESCAPE) {			
			RulesFacade.getRules().unsetSelectedShip();
		}
		
		if(k.getKeyChar() == 'r') {
			RulesFacade.getRules().resetGrid();
		}		
	}
	
	@Override
	public void notify(IObservable o) {
		// TODO Auto-generated method stub
		Object lob[] = (Object []) o.get();
		
		int currentPlayerNum = (Integer) lob[K.objectValues.CURRENT_PLAYER.getValue()];
		
		setTitle("Ship Selection - " + RulesFacade.getRules().getPlayerName(currentPlayerNum));
	}

}