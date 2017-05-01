package command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

import nmdp.Configure;
import util.Executor;
import util.FileHelp;

public class test {
public void run() throws IOException{
		
		//create command line
		StringBuilder sb = new StringBuilder();
		sb.append(" echo hello");
		PrintStream out = null;
		try {
			
			
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(sb.toString());
			BufferedReader stdInput = new BufferedReader(new 
				     InputStreamReader(proc.getInputStream()));
            PrintWriter pw = new PrintWriter("output.txt");

				// read the output from the command
				System.out.println("Here is the standard output of the command:\n");
				String s = null;
				while ((s = stdInput.readLine()) != null) {
				    System.out.println(s);
				    pw.println(s);
				}

				pw.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
