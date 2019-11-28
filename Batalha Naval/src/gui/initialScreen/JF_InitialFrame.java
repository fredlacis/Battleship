package gui.initialScreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import main.K;

@SuppressWarnings("serial")
public class JF_InitialFrame extends JFrame{

	
	static JF_InitialFrame initialFrame;
    
    public static JF_InitialFrame getInitialFrame() {
        if(initialFrame == null)
        	initialFrame = new JF_InitialFrame();
        
        return initialFrame;
        
    }
    
    public void selfDestroy() {
    	initialFrame = null;
    }
	
	private JF_InitialFrame() {
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
		
		getContentPane().add(new JP_InitialScreen());
		
		setTitle("Batalha Naval");
	}

}
