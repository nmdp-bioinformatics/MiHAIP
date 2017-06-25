package command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Formatter;

import util.*;
import nmdp.Configure;

public class Compare {
	public void run(){
		
		//create command line
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("java -jar %s/RTG.jar vcfeval ", Configure.tool));
		sb.append(" -b ");
		sb.append(Configure.inputD);
		sb.append(" -c ");
		sb.append(Configure.inputR);
		sb.append(" -o ");
		sb.append(FileHelp.getVcfevalPath());
		sb.append(" -t ");
		sb.append(String.format("%s/rtg-tools-3.6.2/GRCh38.sdf", Configure.tool));
		// Switch reference version
		//sb.append(String.format("%s/rtg-tools-3.6.2/hg19.sdf", Configure.tool));
		System.out.println(sb.toString());
		new Executor().executeCommand(sb.toString());
		
	}

}
