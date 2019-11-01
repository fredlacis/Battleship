package gui.shipSelection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import gui.JP_Utilities;
import gui.ships.Ship;
import main.K;
import rules.designPatterns.Facade;

@SuppressWarnings("serial")
public class JF_ShipSelection extends JFrame implements KeyListener{

	public JF_ShipSelection() {
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

		getContentPane().add(new JP_ShipOptions());
		getContentPane().add(new JP_ShipPlacement());
		getContentPane().add(new JP_Utilities());
		
		addKeyListener(this);
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
			
			Ship selectedShip = Facade.getFacade().selectedShip();
			
			if(selectedShip == null) return;
			
			selectedShip.setColor(selectedShip.getOriginalColor());
			selectedShip.setBorderColor(selectedShip.getOriginalColor().darker());
			selectedShip.repaint();
			
			Facade.getFacade().unsetSelectedShip();
		}
	}

}