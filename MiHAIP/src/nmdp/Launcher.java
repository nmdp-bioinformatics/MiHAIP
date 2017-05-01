package nmdp;




import org.xml.sax.SAXException;

import command.Chop;
import command.Cleavage;
import command.Compare;
import command.Filter;
import command.Merge;
import command.NetMHCpan;
import command.SnpEff;
import command.launchAff;
import command.test;
import util.FileHelp;

import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.HashMap;

public class Launcher {
    private static final String INPUT_D = "-d";
    private static final String INPUT_R = "-r";
    private static final String PID = "-pid";
    private static final String HLA ="-hla";
    private static final String TOOL_FOLDER = "-t";
    private static final String OUTPUT= "-o";
    private static final String STEP = "-s";
    private static final String SEX = "-sex";
   
   
    private static HashMap<String, String> paramMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        
        try{
            getParameters(args);
        }catch (IndexOutOfBoundsException e){
            System.out.println("parameter is missing. program stopped");
            return;
        }
        
        Configure.inputD = paramMap.get(INPUT_D);
        Configure.inputR = paramMap.get(INPUT_R);
        Configure.setPairID(paramMap.get(PID));
        if(paramMap.containsKey(TOOL_FOLDER)){
        	 Configure.tool = paramMap.get(TOOL_FOLDER);
        }
        Configure.setOutputFolder(paramMap.get(OUTPUT));
        try {
			Configure.setHLA(paramMap.get(HLA));
		} catch (Exception e) {
			System.out.println("HLA is not set up properly.");
			return;
		}
        FileHelp.makeFolders();
        Configure.setSex(paramMap.get(SEX));
        if(paramMap.containsKey(STEP)){
        	int step = Integer.parseInt(paramMap.get(STEP));
        	switch(step){
        		case 1:
        			new Compare().run();
        			break;
        		case 2:
        			new SnpEff().run();
        			break;
        		case 3:
        			new Filter().run();
        			break;
        		case 4:
        			new Merge().run();
        			break;
        		case 5:
        			new Cleavage().run();
        			break;
        		case 6:
        			new Chop().run();
        			break;
        		case 7:
        			new NetMHCpan().run();
        			break;
        		case 8:
        			new launchAff().run();
        			break;
        		
        	}
        }else{
        	new Compare().run();
            new SnpEff().run();
            new Filter().run();
            new Merge().run();
            new Cleavage().run();
            new Chop().run();
            new NetMHCpan().run();
            new launchAff().run();
        }
       
    }



    private static void getParameters(String[] args) {
        //get input d
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(INPUT_D)) {
                paramMap.put(INPUT_D, args[i+1]);
                break;
            }
        }
        //get input r
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(INPUT_R)) {
                paramMap.put(INPUT_R, args[i+1]);
                break;
            }
        }

        //get pid
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(PID)) {
                paramMap.put(PID, args[i+1]);
                break;
            }
        }
        
      //get hla
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(HLA)) {
                paramMap.put(HLA, args[i+1]);
                break;
            }
        }
        
      //get tool folder 
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(TOOL_FOLDER)) {
                paramMap.put(TOOL_FOLDER, args[i+1]);
                break;
            }
        }
        
      //get output folder
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(OUTPUT)) {
                paramMap.put(OUTPUT, args[i+1]);
                break;
            }
        }
        
        //get step
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(STEP)) {
                paramMap.put(STEP, args[i+1]);
                break;
            }
        }
        
        //get Sex
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(SEX)) {
                paramMap.put(SEX, args[i+1]);
                break;
            }
        }
 
        
    }

}
