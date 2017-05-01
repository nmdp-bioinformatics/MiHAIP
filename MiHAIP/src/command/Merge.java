package command;

import java.io.File;

import analysis.GenerateOutput;
import analysis.MergeTool;
import analysis.ProcessVcf;
import database.DatabaseUtil;
import nmdp.Configure;
import util.Executor;
import util.FileHelp;

public class Merge {
	public void run(){
		//create command line
//		StringBuilder sb = new StringBuilder();
//		sb.append(String.format("java -jar %s/merge.jar ", Configure.tool));
//		sb.append(FileHelp.getMissenseFp());
//		sb.append(" ");
//		sb.append(FileHelp.getMissenseFn());
//		new Executor().executeCommand(sb.toString());
		

		DatabaseUtil.connectDatabase();

		
		MergeTool mt = new MergeTool();
		File input1 = new File(FileHelp.getMissenseFp());
		File input2 = new File(FileHelp.getMissenseFn());
		String name = input1.getName();
		if(input1.getName().length() < 3 || input2.getName().length() < 3){
			System.out.println("invalid input file name");
			DatabaseUtil.cleanUp();
			return;
		}
	
		File output = new File(FileHelp.getOutput() + Configure.getPairID() +".txt");
		System.out.println("pair id is : " + Configure.getPairID());
		System.out.println(output.getName());
		mt.merge(input1, input2, output);
		System.out.println("merge finished");
		ProcessVcf pv = new ProcessVcf();
		pv.run(output);
		System.out.println("process vcf finished");
		GenerateOutput go = new GenerateOutput();
		go.run(pv.geneList, pv.changedYgeneList, Configure.getPairID(), true);
		System.out.println("generate output finished");
	    DatabaseUtil.cleanUp();
		
	}
}
