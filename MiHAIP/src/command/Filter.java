package command;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import util.Executor;
import util.FileHelp;

/**
 * The class to extract the missense variant from annotated vcf.
 *
 */
public class Filter {
	public void run(){
		filter(FileHelp.getAnnotateOutputFp(), FileHelp.getMissenseFp());
		filter(FileHelp.getAnnotateOutputFn(), FileHelp.getMissenseFn());
		
	}
	
	private void filter(String input, String output){
		try{
			Scanner sn = new Scanner(new File(input));
			File outputFile = new File(output);
			PrintWriter pw = new PrintWriter(outputFile);
			while(sn.hasNext()){
				String line = sn.nextLine();
				if(line.contains("missense_variant")){
					pw.println(line);
				}
			}
			sn.close();
			pw.close();
		}catch(IOException e){
			
		}
		
	}
}
