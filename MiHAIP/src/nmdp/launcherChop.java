package nmdp;

import clevage.Clevage;
import util.FileHelp;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

/**
 * Created by wwang on 10/19/16.
 */
public class launcherChop {

    public static void main(String[] args) throws URISyntaxException {
        Clevage cl = new Clevage();
        if(args.length != 2){
            System.out.println("Invalid parameter. Need two input files.");
            return;
        }
        try {
            cl.run(new File(args[0]), new File(args[1]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
