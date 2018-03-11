package command;

import java.io.File;
import java.io.FileNotFoundException;

import analysis.GenerateOutput;
import analysis.MergeTool;
import analysis.ProcessAff;
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
	
	public static void main(String[] args) throws FileNotFoundException{

		DatabaseUtil.connectDatabase();
        Configure.setPairID("555");
		
//		MergeTool mt = new MergeTool();
//		File input1 = new File("/Users/wwang/Desktop/MiHAIP_validation_2018/555/555_fn_ann_msv.vcf");
//		File input2 = new File("/Users/wwang/Desktop/MiHAIP_validation_2018/555/555_fn_ann_msv.vcf");
//		String name = input1.getName();
//		if(input1.getName().length() < 3 || input2.getName().length() < 3){
//			System.out.println("invalid input file name");
//			DatabaseUtil.cleanUp();
//			return;
//		}
//	
//		File output = new File("/Users/wwang/Desktop/MiHAIP_validation_2018/2/2.txt");
//		System.out.println(output.getName());
//		mt.merge(input1, input2, output);
//		System.out.println("merge finished");
//		ProcessVcf pv = new ProcessVcf();
//		pv.run(output);
//		System.out.println("process vcf finished");
//		GenerateOutput go = new GenerateOutput();
//		go.run(pv.geneList, pv.changedYgeneList, "555", true);
//		System.out.println("generate output finished");
//	    DatabaseUtil.cleanUp();
	    //new WindowSlider().run();
	    ProcessAff pa = new ProcessAff();
	    pa.run(new File("/Users/wwang/MiHAIP_org/MiHAIP/output/chopMeta/555.txt"), new File("/Users/wwang/Desktop/MiHAIP_validation_2018/2/6_HLA-A0201HM.txt"), new File("/Users/wwang/Desktop/MiHAIP_validation_2018/2/output.txt"));
	}
}
