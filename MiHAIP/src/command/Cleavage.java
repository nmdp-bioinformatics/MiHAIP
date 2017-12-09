package command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import nmdp.Configure;
import nmdp.Sex;
import util.Executor;
import util.FileHelp;

public class Cleavage {
	public void run(){
		//create command line
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s/netchop-3.1/bin/netChop -s -v 1 ", Configure.tool));
		sb.append(FileHelp.getMergeOutput());
		System.out.println(sb.toString());
		try {
			CommandHelper.runAndSave(sb.toString(), FileHelp.getCleavageFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		//If female to male, cleavage y genes
//		if(Configure.getSex() == Sex.FM){
//			sb = new StringBuilder();
//			sb.append(String.format("%s/netchop-3.1/bin/netChop -s ", Configure.tool));
//			sb.append(FileHelp.getYproteinFile());
//			try {
//				CommandHelper.runAndSave(sb.toString(), FileHelp.getCleavageYFile());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(sb.toString());
//		}
		
		
	}

}
