package analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by wwang on 9/7/16.
 */
public class MergeTool {
    Scanner snFn;
    Scanner snFp;
    File output;
    List<List<SNPdata>> fnData = new ArrayList<List<SNPdata>>() ;
    public void merge(File fn, File fp, File output){
        this.output = output;
        for(int i = 0; i< 24; i++){
            fnData.add(new ArrayList<SNPdata>());
        }
        setScanner(fn, fp);
        readFn();
        readFp();
        writeMegerFile();
        fnData.clear();
    }

    public List<List<SNPdata>> getMergeList(){
        return fnData;
    }

    private void writeMegerFile() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(pw != null){
            for(List<SNPdata> list : fnData){
                for(SNPdata item : list){
                    pw.println(item.data);
                }
            }
        }
        pw.close();
    }

    private void readFp() {
        String line = "";
        //skip header
        while(snFp.hasNext()){
            line = snFp.nextLine();
            if(line.startsWith("#")){
                continue;
            }else {
                break;
            }
        }
        addFpData(line);
        while (snFp.hasNext()){
            addFpData(snFp.nextLine());
        }
    }

    /**
     * Add fp data and remove duplicate snp.
     * @param line
     */
    private void addFpData(String line) {
        SNPdata data = new SNPdata(line);
        List<SNPdata> chrome = fnData.get(data.chrome - 1);
        boolean delete = false;
        int index = 0;
        for(int i = 0; i < chrome.size(); i++){
            SNPdata ref = chrome.get(i);
            if(ref.pos == data.pos){
                if(ref.alt.equals(data.alt)){
                    delete = true;
                    index = i;
                }else {
                    ref.ref = data.alt;
                    return;
                }
            }
        }
        if(delete){
            chrome.remove(index);
        }else {
            chrome.add(data);
        }
    }

   
    private void readFn() {
        String line = "";
        //skip header
        while(snFn.hasNext()){
            line = snFn.nextLine();
            if(line.startsWith("#")){
                continue;
            }else {
                break;
            }
        }
        addFnData(line);
        while (snFn.hasNext()){
            addFnData(snFn.nextLine());
        }
    }


    /**
     * Add all recipient data
     * @param line
     */
    private void addFnData(String line){
        SNPdata data = new SNPdata(line);
        int index = data.chrome - 1;
        fnData.get(index).add(data);

    }

    private void setScanner(File fn, File fp) {
        try {
            snFp = new Scanner(fp);
            snFn = new Scanner(fn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
