package saveload;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import gui.attack.JF_Attack;
import gui.attack.JP_AttackUtilities;
import gui.initialScreen.JF_InitialFrame;
import rules.CtrlRules;
import rules.designPatterns.RulesFacade;

public class SaveLoadManager {

	static SaveLoadManager s = null;
	
    private SaveLoadManager() {}
    
    public static SaveLoadManager get() {
        if(s==null)
            s=new SaveLoadManager();
        
        return s;
    }
    
    public void Save() {
    	CtrlRules ctrl = RulesFacade.getRules().getCtrl();
    	
		try {
			JF_Attack attackFrame = JF_Attack.getAttackFrame();
			
			FileDialog fd = new FileDialog(attackFrame, "Choose a file", FileDialog.SAVE);
			fd.setDirectory("./");
			fd.setFile("save.txt");
			fd.setVisible(true);
			String fileName = fd.getFile();
			if (fileName == null) {
				System.out.println("You cancelled the choice");
				return;
			}
			else {
				System.out.println("You chose " + fileName);
			}
			
			FileOutputStream f = new FileOutputStream(new File(fileName));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(ctrl);

			o.close();
			f.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
    		System.out.println(e.getMessage());
    		System.out.println("Error initializing stream");
		}
    }
    
    public void Load() {
    	try {
    		JF_InitialFrame initialFrame = JF_InitialFrame.getInitialFrame();
    		
    		FileDialog fd = new FileDialog(initialFrame, "Choose a file", FileDialog.LOAD);
    		fd.setDirectory("./");
    		fd.setVisible(true);
    		String fileName = fd.getFile();
			if (fileName == null) {
				System.out.println("You cancelled the choice");
				return;
			}
			else {
				System.out.println("You chose " + fileName);
			}
    		
        	FileInputStream fi = new FileInputStream(new File(fileName));
    		ObjectInputStream oi = new ObjectInputStream(fi);

    		// Read objects
    		CtrlRules ctrl = (CtrlRules) oi.readObject();
    		
    		RulesFacade.getRules().overrideCtrl(ctrl);
    		
    		JF_Attack.getAttackFrame().setVisible(true);
    		initialFrame.setVisible(false);
    		
    		ctrl.refreshBoard();
    		
    		JP_AttackUtilities.getAttackUtilites().buttonEnable();

    		oi.close();
    		fi.close();

    	} catch (FileNotFoundException e) {
    		System.out.println("File not found");
    	} catch (IOException e) {
    		System.out.println(e.getMessage());
    		System.out.println("Error initializing stream");
    	} catch (ClassNotFoundException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
}
