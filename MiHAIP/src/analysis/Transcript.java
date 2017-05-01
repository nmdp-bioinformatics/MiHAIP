package analysis;

import java.util.HashMap;

/**
 * Created by wwang on 9/5/16.
 */
public class Transcript {
    public int chrome;
    public int pos;
    String geneID;
    String ref;
    String alt;
    String transcriptID;
    public HashMap<Integer, String> change = new HashMap<>();

    public Transcript(String transcriptID){
        this.transcriptID = transcriptID;
    }

    public void setInfo(Transcript t){
        this.chrome = t.chrome;
        this.pos = t.pos;
        this.ref = t.ref;
        this.alt = t.alt;
    }

    public void addPos(int pos, String NT){
        change.put(pos, NT);
    }

    public void setGeneID(String geneID){
        this.geneID = geneID;
    }

    public void setNT(String ref, String alt){
        this.ref = ref;
        this.alt = alt;
    }
    public String getAlt(){
        return alt;
    }
    public void setChrome(int i){
        chrome = i;
    }
    public void setChromePos(int i){
        pos = i;
    }
    public String getInfo(){
    	if(chrome == 24){
    		return ">chr"+"X" + "|" + pos + "|"+ geneID;
    	}else if(chrome == 25){
    		return ">chr"+"Y" + "|" + pos + "|"+ geneID;
    	}else{
    		return ">chr"+chrome + "|" + pos + "|"+ geneID;
    	}
        
    }
    public String getChrome(){
    	if(chrome == 24){
    		return "chrX";
    	}else if (chrome == 25){
    		return "chrY";
    	}else{
    		return "chr"+chrome;
    	}
        
    }
}
