package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wwang on 9/5/16.
 */
public class ImportData {
	 static String id = "";
     static String seq = "";
     static String chrome = "";
     static String geneSymbol = "";
     static String description = "";
     
     static String idPattern = ">ENST\\d+\\.\\d";
     static String chromePattern = "GRCh38:(\\d+|\\w+)";
     static String geneTypePattern = "gene_symbol:\\w*";
     static String geneDescription = "description:(\\w|\\s)+";
     
    public static void main(String[] args){
        DatabaseUtil.connectDatabase();
        DatabaseUtil.createSeqTable();
        Scanner sn = null;
      
        try {
             sn = new Scanner(new File("Homo_sapiens.GRCh38.cds.all.fa"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(sn != null){
            id = "";
            seq = "";
            while(sn.hasNext()){
                String line = sn.nextLine();
                if(line.startsWith(">")){
                	
                    if(!id.isEmpty()){
                        DatabaseUtil.insertSeqData(chrome, geneSymbol, description, id, seq);
                        seq = "";
                    }
                    parseInfo(line);
                }else{
                    seq += line;
                }
            }

            //insert the last seq
            DatabaseUtil.insertSeqData(chrome, geneSymbol, description, id, seq);
        }
        DatabaseUtil.cleanUp();
    }
    
    private static void parseInfo(String line){
    	 int index = 1;
         while(line.charAt(index) != ' '){
             index ++;
         }
         id = line.substring(1, index);
         
         //find id 
         Pattern p = Pattern.compile(idPattern);   
         Matcher m = p.matcher(line);
         if (m.find()){
        	 System.out.println(m.group(0));
        	 id = m.group(0).substring(1);
         }
         
         //find chrome number
         p = Pattern.compile(chromePattern);
         m = p.matcher(line);
         if(m.find()){
        	 System.out.println(m.group(0));
        	 String[] ss = m.group(0).split(":");
        	 chrome = m.group(0).split(":")[1];
         }
         
         //find gene type
         p = Pattern.compile(geneTypePattern);
         m = p.matcher(line);
         if(m.find()){
        	 System.out.println(m.group(0));
        	 String[] ss = m.group(0).split(":");
        	 geneSymbol = m.group(0).split(":")[1];
         }
         
         //find gene description
         p = Pattern.compile(geneDescription);
         m = p.matcher(line);
         if(m.find()){
        	 System.out.println(m.group(0));
        	 String[] ss = m.group(0).split(":");
        	 description = m.group(0).split(":")[1];
         }
    }
    
}
