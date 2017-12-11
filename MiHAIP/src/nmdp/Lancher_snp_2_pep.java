package nmdp;

import java.io.File;

import analysis.GenerateOutput;
import analysis.ProcessVcf;
import database.DatabaseUtil;
import util.FileHelp;
import util.OutputSplitor;

public class Lancher_snp_2_pep {
	
	 public static void main(String[] args) throws Exception {
		DatabaseUtil.connectDatabase();
		ProcessVcf pv = new ProcessVcf();
		pv.run(new File(args[0]));
		System.out.println("process vcf finished");
		GenerateOutput go = new GenerateOutput();
		String fileName = "snp_2_pep";
		go.run(pv.geneList, pv.changedYgeneList, fileName, true);
		System.out.println("generate output finished");
		OutputSplitor os = new OutputSplitor();
		os.split(FileHelp.getRefProtienFile(fileName), "Ref_"+fileName);
		os.split(FileHelp.getAltProtienFile(fileName), "Alt_"+fileName);
		System.out.println("finished");
		DatabaseUtil.cleanUp();
		 
	 }
	
}
