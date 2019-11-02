package gui.shipSelection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gui.JP_Utilities;
import gui.board.JP_PositioningGrid;
import gui.ships.Ship;
import main.K;
import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public class JF_ShipSelection extends JFrame implements KeyListener, MouseListener{

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

		getContentPane().add(JP_ShipOptions.getShipOptions());
		getContentPane().add(new JP_ShipPlacement());
		getContentPane().add(new JP_Utilities());
		
		addKeyListener(this);
		addMouseListener(this);
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
			
			Ship selectedShip = RulesFacade.getRules().selectedShip();
			
			if(selectedShip == null) return;
			
			selectedShip.setColor(selectedShip.getOriginalColor());
			selectedShip.setBorderColor(selectedShip.getOriginalColor().darker());
			selectedShip.repaint();
			
			RulesFacade.getRules().unsetSelectedShip();
		}
		
		if(k.getKeyChar() == 'r') {
			RulesFacade.getRules().resetGrid();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!SwingUtilities.isRightMouseButton(e)) return;
		
		Ship selectedShip = RulesFacade.getRules().selectedShip();
		if(selectedShip == null) return;
		
		selectedShip.rotate();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}