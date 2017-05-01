package analysis;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nmdp.Configure;
import nmdp.Sex;

/**
 * Created by wwang on 9/5/16.
 */
public class ProcessVcf {
    public List<Transcript> geneList = new ArrayList<>();
    public List<String> changedYgeneList = new ArrayList<>();
    private Transcript preGene = null;

    public void run(File input){
        Scanner sn = null;
        try {
            sn = new Scanner(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sn.hasNext()){
            processLine(sn.nextLine());
        }
    }

    private void processLine(String s) {
        String[] trans = s.split("\\|,");
        //process the first transcript
        String[] detail = trans[0].split("\\|");
        //Process the chrome information
        String[] basic = detail[0].split("\\t");
        String chromeNumber = basic[0].substring(3);
        int chrome = basic[0].charAt(3) - '0';
        if(chrome == 40){
            //convert X to 24
            chrome = 24;
        } else if(chrome ==41) {
            //convert Y to 25
            chrome = 25;
        }else {
            chrome = Integer.parseInt(chromeNumber);
        }

        int pos = Integer.parseInt(basic[1]);
        String transcript = detail[6];
        //Remember the transcript ID that has SNP in Y chromesome
        if(chrome == 25 && Configure.getSex() == Sex.FM){
        	changedYgeneList.add(transcript);
        }
        Transcript gene;
        if(preGene != null && preGene.transcriptID.equals(transcript)){
            gene = preGene;
        }else {
            gene = new Transcript(transcript);
            gene.setChrome(chrome);
            gene.setChromePos(pos);
            preGene = gene;
            geneList.add(gene);
        }
        gene.setNT(basic[3], basic[4]);
        gene.setGeneID(detail[3]);


        int DNAchange = 0;
        try{
            DNAchange = getNumber(detail[9]);
        }catch (Exception e){
            System.out.println("data is not number "+ detail[9]);
        }
        gene.addPos(DNAchange, gene.getAlt());

        for(int i = 0; i< trans.length ; i++){
            processTrans(trans[i], gene);
        }

    }

    private void processTrans(String tran, Transcript gene) {
        if(tran.length()<4 || !tran.startsWith("ENST")){
            return;
        }
        String[] detail = tran.split("\\|");
        if(detail[1].startsWith("missense")){
            String transcript = detail[6];
            int DNAchange = 0;
            try{
                DNAchange = getNumber(detail[9]);
            }catch (Exception e){
                System.out.println("data is not number "+ detail[9]);
            }

            if(transcript.equals(gene.transcriptID)){
                gene.addPos(DNAchange, gene.getAlt());
            }else{
                //create new transcript
                Transcript newGene = new Transcript(transcript);
                newGene.setInfo(gene);
                newGene.addPos(DNAchange, gene.getAlt());
                geneList.add(newGene);
                preGene = newGene;
            }

        }

    }

    private int getNumber(String s){
        int start = 0;
        while(!Character.isDigit(s.charAt(start))){
            start++;
        }
        int end = start;
        while(Character.isDigit(s.charAt(end))){
            end ++;
        }
        return Integer.parseInt(s.substring(start, end));
    }
}
