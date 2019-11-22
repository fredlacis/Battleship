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
			String filename = fd.getFile();
			if (filename == null) {
				System.out.println("You cancelled the choice");
				return;
			}
			else {
				System.out.println("You chose " + filename);
			}
			
			FileOutputStream f = new FileOutputStream(new File(filename));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(ctrl);

			o.close();
			f.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
    }
    
    public void Load() {
    	try {
        	FileInputStream fi = new FileInputStream(new File("savefile.txt"));
    		ObjectInputStream oi = new ObjectInputStream(fi);

    		// Read objects
    		CtrlRules ctrl = (CtrlRules) oi.readObject();

    		System.out.println(ctrl.toString());

    		oi.close();
    		fi.close();

    	} catch (FileNotFoundException e) {
    		System.out.println("File not found");
    	} catch (IOException e) {
    		System.out.println("Error initializing stream");
    	} catch (ClassNotFoundException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
}
