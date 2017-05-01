package translate;


import java.util.HashMap;
import java.util.List;

/**
 * Created by Will on 6/5/16.
 */
public class Translator {
    private static final String TAG = Translator.class.getSimpleName();
    private HashMap<String, String> map = new HashMap<>();

    public Translator(){
        map.put("TCA", "S");
        map.put("TCC", "S");
        map.put("TCG", "S");
        map.put("TCT", "S");

        map.put("TTC", "F");
        map.put("TTT", "F");

        map.put("TTA", "L");
        map.put("TTG", "L");

        map.put("TAC", "Y");
        map.put("TAT", "Y");

        map.put("TAA", "*");
        map.put("TAG", "*");

        map.put("TGC", "C");
        map.put("TGT", "C");

        map.put("TGA", "*");
        map.put("TGG", "W");

        map.put("CTA", "L");
        map.put("CTC", "L");
        map.put("CTG", "L");
        map.put("CTT", "L");

        map.put("CCA", "P");
        map.put("CCC", "P");
        map.put("CCT", "P");

        map.put("CAC", "H");
        map.put("CAT", "H");

        map.put("CAA", "Q");
        map.put("CAG", "Q");

        map.put("CGA", "R");
        map.put("CGC", "R");
        map.put("CGC", "R");
        map.put("CGG", "R");
        map.put("CGT", "R");


        map.put("ATA", "I");
        map.put("ATC", "I");
        map.put("ATT", "I");


        map.put("ATG", "M");

        map.put("ACA", "T");
        map.put("ACC", "T");
        map.put("ACG", "T");
        map.put("ACT", "T");

        map.put("AAC", "N");
        map.put("AAT", "N");

        map.put("AAA", "K");
        map.put("AAG", "K");

        map.put("AGC", "S");
        map.put("AGT", "S");

        map.put("AGA", "R");
        map.put("AGG", "R");

        map.put("GTA", "V");
        map.put("GTC", "V");
        map.put("GTG", "V");
        map.put("GTT", "V");

        map.put("GCA", "A");
        map.put("GCC", "A");
        map.put("GCG", "A");
        map.put("GCT", "A");

        map.put("GAC", "D");
        map.put("GAT", "D");

        map.put("GAA", "E");
        map.put("GAG", "E");

        map.put("GGA", "G");
        map.put("GGC", "G");
        map.put("GGG", "G");
        map.put("GGT", "G");

    }

    /**
     * frame start with 0
     * @param dna
     * @param frame
     * @return
     */
    public String translate(String dna, int frame){
        StringBuilder sb = new StringBuilder();
        for(int i= frame; i+2 < dna.length(); i = i+3){
            sb.append(dna2aa(dna.substring(i, i+3)));
        }

        return sb.toString();
    }

    public String translate(String dna, int frame, List<Integer> lowcase){
        StringBuilder sb = new StringBuilder();
        for(int i= frame; i+2 < dna.length(); i = i+3){
            if(!lowcase.isEmpty() && sb.length() == lowcase.get(0)){
                sb.append(dna2aa(dna.substring(i, i+3)).toLowerCase());
                lowcase.remove(0);
            }else {
                sb.append(dna2aa(dna.substring(i, i+3)));
            }

        }

        return sb.toString();
    }

    public String dna2aa(String dna){
        if(dna == null || dna.length() != 3){
            System.out.println(TAG + "The DNA length must be 3 for translation! But it is  "+ dna);
            return "*";
        }
        dna = dna.toUpperCase();
        if(map.containsKey(dna)){
            return map.get(dna);
        }else if(dna.startsWith("GC")){return "A";}
        else if(dna.startsWith("GG")){return "G";}
        else if(dna.startsWith("CC")){return "P";}
        else if(dna.startsWith("AC")){return "T";}
        else if(dna.startsWith("GT")){return "V";}
        else if(dna.startsWith("CG")){return "R";}
        else if(dna.startsWith("TC")){return "S";}
        else{ return"X";}

    }

}
