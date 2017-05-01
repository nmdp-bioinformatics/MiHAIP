package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import translate.Translator;

public class createYpasta {

	public static void main(String[] args) throws FileNotFoundException, SQLException {
		DatabaseUtil.connectDatabase();
//		ResultSet rs = DatabaseUtil.getYdata();
//		PrintWriter pw = new PrintWriter(new File("y.fasta"));
//		Translator translate = new Translator();
//		while(rs.next()){
//			String transId = rs.getString("TRANSCRIPT_ID");
//			String seq = rs.getString("SEQ");
//			pw.println(">chrome Y |" + transId + "|");
//			pw.println(translate.translate(seq, 0));
//		}
//		pw.close();
		chopY cy = new chopY();
		cy.chop();
		DatabaseUtil.cleanUp();
	}

}
