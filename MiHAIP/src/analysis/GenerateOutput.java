package analysis;

import database.DatabaseUtil;
import nmdp.Configure;
import nmdp.Sex;
import translate.Translator;
import util.FileHelp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wwang on 9/5/16.
 */
public class GenerateOutput {

    private String fileName;
    private Translator translator = new Translator();
    private PrintWriter refDNA;
    private PrintWriter altDNA;
    private PrintWriter protienWriter;
    //private PrintWriter metaDataWriter;
    private boolean replaceTwo = false;

    public void run(List<Transcript> geneList, List<String> yGeneList, String fileName) {
        this.fileName = fileName;
        try {
            setPrinters();
        } catch (FileNotFoundException | java.net.URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        for (Transcript gene : geneList) {
            processGene(gene);
        }
        if(Configure.getSex() == Sex.FM && !yGeneList.isEmpty()){
        	//Add transcrip in Y chromesome that not changed
        	processYgenes(yGeneList);
        }
        cleanup();
    }

    private void processYgenes(List<String> yGeneList) {
		List<String> idList = DatabaseUtil.getYtransIDs();
		Translator translator = new Translator(); 
		//remove duplicate id
		for(String item : yGeneList){
			if(idList.contains(item)){
				idList.remove(item);
			}
		}
		try {
			PrintWriter yPrinter = new PrintWriter(new File(FileHelp.getYproteinFile()));
			for(String id : idList){
				yPrinter.println(">chrome Y |"+ id+ "|");
				String seq = DatabaseUtil.getSequence(id);
				yPrinter.println(translator.translate(seq, 0));
			}
			yPrinter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void run(List<Transcript> geneList, List<String> yGeneList, String fileName, boolean replaceTwo){
        this.replaceTwo = replaceTwo;
        run(geneList, yGeneList, fileName);
    }

    private void cleanup() {
        refDNA.close();
        altDNA.close();
        protienWriter.close();
        //metaDataWriter.close();
    }

    private void setPrinters() throws FileNotFoundException, URISyntaxException {
    	File refDNAFile = new File(fileName + "_refDNA.txt");
        refDNA = new PrintWriter(refDNAFile);
        
        File altDNAFile = new File(fileName + "_altDNA.txt");
        altDNA = new PrintWriter(altDNAFile);
        
        File mergeOutput = new File(fileName + "_protein.txt");
        protienWriter = new PrintWriter(mergeOutput);
        
        //File metaFile = new File(FileHelp.getMetaData());
        //metaDataWriter = new PrintWriter(metaFile);

    }

    private void processGene(Transcript gene) {
        String dna = DatabaseUtil.getSequence(gene.transcriptID);
        Iterator<Integer> it = gene.change.keySet().iterator();
        StringBuilder sbRef = new StringBuilder(dna);
        StringBuilder sbAlt = new StringBuilder(dna);
        StringBuilder changePos = new StringBuilder();
        List<Integer> aachangePos = new ArrayList<>();
        List<Integer> DNAchangePos = new ArrayList<>();
        changePos.append(" ,");
        while (it.hasNext()) {
            int i = it.next();
            if(i < dna.length()){
                String mid = dna.substring(i-1, i);
                if(replaceTwo){
                    sbRef.replace(i-1, i, gene.ref.toLowerCase());
                }else {
                    sbRef.replace(i-1, i, mid.toLowerCase());
                }
                sbAlt.replace(i-1, i, gene.alt.toLowerCase());
                changePos.append(i);
                DNAchangePos.add(i);
                changePos.append(",");
                aachangePos.add((i-1)/3);
            }

        }

        refDNA.print(gene.getInfo());
        refDNA.print(" " + gene.transcriptID);
        refDNA.print(changePos.toString());
        refDNA.println(sbRef.toString());

        altDNA.print(gene.getInfo());
        altDNA.print(" " + gene.transcriptID);
        altDNA.print(changePos.toString());
        altDNA.println(sbAlt.toString());




        StringBuilder fastaHeader = new StringBuilder();
        fastaHeader.append(gene.getInfo());
        fastaHeader.append("|" + gene.transcriptID + "|");
        String refProtein = translator.translate(sbRef.toString(), 0, new ArrayList<Integer>(aachangePos));
        String altProtein = translator.translate(sbAlt.toString(), 0, new ArrayList<Integer>(aachangePos));
        for(int i = 0; i< aachangePos.size(); i++){
            protienWriter.print(fastaHeader);
            protienWriter.println(aachangePos.get(i)+1);
            int start = getStartIndex(aachangePos.get(i));
            int end = getEndIndex(refProtein, aachangePos.get(i));
            
            
            protienWriter.println(refProtein.substring(start, end + 1));

            protienWriter.print(fastaHeader);
            protienWriter.println(aachangePos.get(i)+1);
            protienWriter.println(altProtein.substring(start, end + 1));

            //print the meta data
//            metaDataWriter.print(gene.getChrome());
//            metaDataWriter.print(",");
//            metaDataWriter.print(gene.pos);
//            metaDataWriter.print(",");
//            metaDataWriter.print(sbRef.toString().charAt(DNAchangePos.get(i)-1));
//            metaDataWriter.print(",");
//            metaDataWriter.print(sbAlt.toString().charAt(DNAchangePos.get(i)-1));
//            metaDataWriter.print(",");
//            metaDataWriter.print(DNAchangePos.get(i));
//            metaDataWriter.print(",");
//            metaDataWriter.print(gene.transcriptID);
//            metaDataWriter.println(",");
//
//            if(end - start == 40){
//                metaDataWriter.println(20);
//            }else {
//                if(start == 0){
//                    metaDataWriter.println(aachangePos.get(i));
//                }else {
//                    metaDataWriter.println(aachangePos.get(i)-start);
//                }
//            }
//
        }


    }

    private int getEndIndex(String refProtein, int i) {
        int end = i + 10;
        if(end >= refProtein.length()){
            return refProtein.length() - 1;
        }else {
            return end;
        }
    }

    private int getStartIndex(int i) {
        int start = i -10;
        if(start < 0){
            return 0;
        }else {
            return start;
        }
    }


    private String getProtienChange(List<Integer> change){
        StringBuilder changePos = new StringBuilder();
        for(int i : change){
            changePos.append(i+1);
            changePos.append(",");
        }
        if(changePos.length() >1){
            changePos.deleteCharAt(changePos.length()-1);
        }

        return changePos.toString();
    }
}