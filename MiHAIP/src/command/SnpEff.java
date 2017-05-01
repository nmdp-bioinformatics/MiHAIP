package command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import nmdp.Configure;
import util.Executor;
import util.FileHelp;


public class SnpEff {
	public void run(){
		//create command line
		StringBuilder sb = new StringBuilder();
		//create annotated output fp
		sb.append(String.format("java -jar %s/snpEff/snpEff.jar GRCh38.82 ", Configure.tool));
		sb.append(FileHelp.getSnpeffInputFp());
		sb.append(" -t -canon -onlyProtein ");
		
		try {
			CommandHelper.runAndSave(sb.toString(), FileHelp.getAnnotateOutputFp());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create annotated output fn
		sb = new StringBuilder();
		sb.append(String.format("java -jar %s/snpEff/snpEff.jar GRCh38.82 ", Configure.tool));
		sb.append(FileHelp.getSnpeffInputFn());
		sb.append(" -t -canon -onlyProtein ");
		try {
			CommandHelper.runAndSave(sb.toString(),FileHelp.getAnnotateOutputFn());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
