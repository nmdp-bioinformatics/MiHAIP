package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class filterFreqData {

	public static void main(String[] args) {
		String fileName = args[0];
		Scanner sn =  null;
		PrintWriter pw = null;
		try {
			sn = new Scanner(new File (fileName));
			pw = new PrintWriter(new File("filteredFile.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(sn.hasNext()){
			String line = sn.nextLine();
			String[] columns = line.split("\t");
			if(columns.length < 25 || !columns[11].equals("single") || columns[24].isEmpty()){
				continue;
			}else{
				pw.print(columns[1]);
				pw.print("\t");
				pw.print(columns[2]);
				pw.print("\t");
				pw.print(columns[3]);
				pw.print("\t");
				pw.print(columns[4]);
				pw.print("\t");
				pw.print(columns[9]);
				pw.print("\t");
				pw.print(columns[11]);
				pw.print("\t");
				if(columns[24].startsWith("maf")){
					pw.print(columns[23]);
				}else{
					pw.print(columns[24]);
				}
				pw.println();
				
			}
		}
		sn.close();
		pw.close();

	}

}
