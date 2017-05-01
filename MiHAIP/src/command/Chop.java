package command;

import java.io.File;
import java.io.FileNotFoundException;

import clevage.Clevage;
import nmdp.Configure;
import util.Executor;
import util.FileHelp;

public class Chop {
	public void run(){
		  Clevage cl = new Clevage();
	        try {
	            cl.run(new File(FileHelp.getMetaData()), new File(FileHelp.getCleavageFile()));
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		
	}
}
