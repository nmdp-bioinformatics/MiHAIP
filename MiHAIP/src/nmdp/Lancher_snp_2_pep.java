package nmdp;

import java.io.File;

import analysis.GenerateOutput;
import analysis.ProcessVcf;
import database.DatabaseUtil;

public class Lancher_snp_2_pep {
	
	 public static void main(String[] args) throws Exception {
		DatabaseUtil.connectDatabase();
		ProcessVcf pv = new ProcessVcf();
		pv.run(new File(args[0]));
		System.out.println("process vcf finished");
		GenerateOutput go = new GenerateOutput();
		go.run(pv.geneList, pv.changedYgeneList, "snp_2_pep", true);
		System.out.println("generate output finished");
		DatabaseUtil.cleanUp();
	 }
	
}
