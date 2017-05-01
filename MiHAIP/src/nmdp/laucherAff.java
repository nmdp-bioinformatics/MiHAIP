package nmdp;
import analysis.ProcessAff;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

/**
 * Created by wwang on 10/23/16.
 */
public class laucherAff {
    public static void main(String[] args) throws URISyntaxException {
        ProcessAff pa = new ProcessAff();
        if(args.length != 3){
            System.out.println("Invalid parameter. Need two input files.");
            return;
        }
        	pa.run(new File(args[0]), new File(args[1]), new File(args[2]));
    }
}
