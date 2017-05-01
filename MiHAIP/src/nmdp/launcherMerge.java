package nmdp;

import analysis.GenerateOutput;
import analysis.MergeTool;
import analysis.ProcessVcf;
import database.DatabaseUtil;
import util.FileHelp;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;


/**
 * Created by wwang on 9/5/16.
 */
public class launcherMerge {

	public static void main(String[] args) {

		DatabaseUtil.connectDatabase();

		if(args.length != 2){
			System.out.println("please enter two input files and one output file");
			DatabaseUtil.cleanUp();
			return;
		}
		MergeTool mt = new MergeTool();
		File input1 = new File(args[0]);
		File input2 = new File(args[1]);
		String name = input1.getName();
		if(input1.getName().length() < 3 || input2.getName().length() < 3){
			System.out.println("invalid input file name");
			DatabaseUtil.cleanUp();
			return;
		}
	
		File output = new File(FileHelp.getOutput() + Configure.getPairID()+".txt");
		System.out.println("pair id is : " + Configure.getPairID());
		System.out.println(output.getName());
		mt.merge(input1, input2, output);
		ProcessVcf pv = new ProcessVcf();
		pv.run(output);

		GenerateOutput go = new GenerateOutput();
		go.run(pv.geneList, new ArrayList<String>(),  Configure.getPairID(), true);
	    DatabaseUtil.cleanUp();

	}

}
