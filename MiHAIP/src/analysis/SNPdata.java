package analysis;

/**
 * Created by wwang on 9/7/16.
 */
public class SNPdata {
    public int chrome;
    public int pos;
    public String ref;
    public String alt;
    public String data;

    public SNPdata(String s){
        String[] trans = s.split(",");
        //process the first transcript
        String[] detail = trans[0].split("\\|");
        //Process the chrome information
        String[] basic = detail[0].split("\\t");
        char chr = basic[0].charAt(3);
        if(chr == 'x'|| chr == 'X'){
            this.chrome = 23;
        }else if (chr == 'y'|| chr == 'Y'){
            this.chrome = 24;
        }else {
            this.chrome = Integer.parseInt(basic[0].substring(3));
        }
        this.pos = Integer.parseInt(basic[1]);
        this.ref = basic[3];
        this.alt = basic[4];
        data = s;
    }
}
