package analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wwang on 9/5/16.
 */
public class Gene {
    public int chrome;
    public int pos;
    String geneID;
    String ref;
    String alt;
    HashMap<String, Transcript> transcripts = new HashMap<>();

    public Gene(int chrome, int pos){
        this.chrome = chrome;
        this.pos = pos;
    }

    public void setGeneID(String geneID){
        this.geneID = geneID;
    }

    public void setNT(String ref, String alt){
        this.ref = ref;
        this.alt = alt;
    }

    public void addTranscript(String key, int pos){
        if(transcripts.containsKey(key)){
            transcripts.get(key).addPos(pos, alt);
        }else {
            Transcript t = new Transcript(key);
            t.addPos(pos, alt);
            transcripts.put(key, t);
        }
    }

    public String getInfo(){
        return "chr"+chrome + " " + pos + " "+ geneID;
    }

}
