package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ImportFreqData {

	public static void main(String[] args) throws SQLException {
		DatabaseUtil.connectDatabase();
        DatabaseUtil.createFreqTable();
        Scanner sn = null;
//        ResultSet rs = DatabaseUtil.getFreq("chr1","10000555","a","t");
//        while(rs.next()){
//        	System.out.println(rs.getString("A"));
//        	System.out.println(rs.getString("T"));
//        }
//      
        try {
             sn = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sn.hasNext()){
        	String line = sn.nextLine();
        	String [] data = line.split("\t");
        	freqData freq = new freqData(data[4].trim(), data[6].trim());
        	DatabaseUtil.insertFreqData(data[0], data[2], freq.getAfreq(), freq.getTfreq(), freq.getCfreq(), freq.getGfreq());
        }
        DatabaseUtil.cleanUp();
	}
	

}
