package gui.shipSelection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import gui.JP_Utilities;
import gui.ships.*;

public class JF_ShipSelection extends JFrame{

	final int LARG_DEFAULT = 1024;
	final int ALT_DEFAULT = 768;

	public JF_ShipSelection(String playerName) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int sl = screenSize.width;
		int sa = screenSize.height;
		int x = sl/2-LARG_DEFAULT/2;
		int y = sa/2-ALT_DEFAULT/2;
		setBounds(x,y,LARG_DEFAULT,ALT_DEFAULT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		getContentPane().setBackground(new Color(250, 250, 250));

		getContentPane().add(new JP_ShipOptions());
		getContentPane().add(new JP_ShipPlacement());
		getContentPane().add(new JP_Utilities());

		//getContentPane().add(new Destroyer());
		//getContentPane().add(new JP_MouseBoard());
		System.out.println(playerName);

	}

}