package gui.victory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import main.K;

@SuppressWarnings("serial")
public class JF_Victory extends JFrame{
	
	static JF_Victory victoryFrame;
    
    public static JF_Victory getVictoryFrame(String winner, String looser) {
        if(victoryFrame == null)
        	victoryFrame = new JF_Victory(winner, looser);
        
        return victoryFrame;
        
    }

	private JF_Victory(String winner, String looser) {
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
		
		getContentPane().add(new JP_Victory(winner, looser));
		
		setTitle("VICTORY!");
	}

}
