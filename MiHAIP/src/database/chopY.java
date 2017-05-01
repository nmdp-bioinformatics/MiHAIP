package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import translate.Translator;

public class chopY {
	String patter = "\\d+\\schrome-Y_0";
	Pattern p = Pattern.compile(patter);
	List<String> idList;
	int index = 0;
	PrintWriter pw;
	public void chop() throws FileNotFoundException, SQLException{
		//generate trans ID list
		getTransIdList();
		Scanner sn = new Scanner(new File("yChopped.txt"));
		pw = new PrintWriter(new File("y.chop"));
		while(sn.hasNext()){
			String line = sn.nextLine();
			Matcher m = p.matcher(line);
			if(m.matches()){
				findSeq(sn);
			}
		}
		pw.close();
	}
	  private List<String> getTransIdList() throws SQLException {
		  ResultSet rs = DatabaseUtil.getYdata();
		  idList= new ArrayList();
			while(rs.next()){
				String transId = rs.getString("TRANSCRIPT_ID");
				idList.add(transId);
			}
			return idList;
		
	}
	private void findSeq(Scanner sn) {
		StringBuilder seq = new StringBuilder();
		StringBuilder cle = new StringBuilder();
		while(true){
			String line = sn.nextLine();
			if(line.startsWith("-----")){
				break;
			}
			seq.append(line.trim());
			cle.append(sn.nextLine().trim());
		}
		List<String> fragments = clevage(seq.toString(), cle.toString());
		if(fragments.isEmpty()){
			index++;
		}else{
			
			for(String fragment: fragments){
				pw.println(">chrome_Y | "+ idList.get(index));
				pw.println(fragment);
			}
		}
		
		
	}
	private List<String> clevage(String seq1, String cle1) {
	        List<String> result = new ArrayList<>();
	        List<Integer> maker = new ArrayList<>();
	        for(int i = 0 ; i < cle1.length(); i++){
	            if(cle1.charAt(i) == 'S'){
	                maker.add(i);
	            }
	        }
	        for(int i = 0; i< maker.size(); i++){
	            for(int j = i+1; j< maker.size(); j++){
	                int size = maker.get(j) - maker.get(i) + 1;
	                if(size >= 8 && size <= 11){
	                    result.add(seq1.substring(maker.get(i), maker.get(j)+1));
	                }else {
	                    break;
	                }
	            }
	        }
	        return result;
	    }

}
