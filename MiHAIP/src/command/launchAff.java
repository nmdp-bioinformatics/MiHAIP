package command;

import java.io.File;

import analysis.ProcessAff;
import nmdp.Configure;
import util.Executor;
import util.FileHelp;

public class launchAff {
	public void run(){
		
		ProcessAff pa = new ProcessAff();
		
		Executor executor = new Executor();
		//create command line
		StringBuilder sb = new StringBuilder();
		if(Configure.hasTwoHla_a()){
			pa.run(new File(FileHelp.getChopMetaData()), new File(FileHelp.getAffFile_A1()), new File(FileHelp.getPredictFileA1()));
			pa.run(new File(FileHelp.getChopMetaData()), new File(FileHelp.getAffFile_A2()), new File(FileHelp.getPredictFileA2()));
		}else{
			pa.run(new File(FileHelp.getChopMetaData()), new File(FileHelp.getAffFile_A1()), new File(FileHelp.getPredictFileA1()));
		}
		
		if(Configure.hasTwoHla_b()){
			pa.run(new File(FileHelp.getChopMetaData()), new File(FileHelp.getAffFile_B1()), new File(FileHelp.getPredictFileB1()));
			pa.run(new File(FileHelp.getChopMetaData()), new File(FileHelp.getAffFile_B2()), new File(FileHelp.getPredictFileB2()));
		}else{
			pa.run(new File(FileHelp.getChopMetaData()), new File(FileHelp.getAffFile_B1()), new File(FileHelp.getPredictFileB1()));
		}
		
		if(Configure.hasTwoHla_c()){
			pa.run(new File(FileHelp.getChopMetaData()), new File(FileHelp.getAffFile_C1()), new File(FileHelp.getPredictFileC1()));
			pa.run(new File(FileHelp.getChopMetaData()), new File(FileHelp.getAffFile_C2()), new File(FileHelp.getPredictFileC2()));
		}else{
			pa.run(new File(FileHelp.getChopMetaData()), new File(FileHelp.getAffFile_C1()), new File(FileHelp.getPredictFileC1()));
		}	
		
	}
}
