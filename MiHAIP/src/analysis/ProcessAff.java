package analysis;

import util.FileHelp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import database.DatabaseUtil;

/**
 * Created by wwang on 10/23/16.
 */
public class ProcessAff {
    ArrayList<String> header = new ArrayList<>();
    Scanner sn;
    PrintWriter pw;
    public void run(File meta, File aff, File output){
    	DatabaseUtil.connectDatabase();
        try {
            sn = new Scanner(aff);
            pw = new PrintWriter(output);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find aff file ");
            e.printStackTrace();
            return;
        }
        loadMetaData(meta);
        skipHeader();
        for(int i = 0; i< header.size(); i++){
            pw.print(header.get(i));
            if(!sn.hasNext()){
                break;
            }else {
            	String[] data = sn.nextLine().split(" +");
            	for(int k = 0; k< data.length; k++){
            		pw.print(data[k]);
            		if(k != data.length-1){
            			pw.print(",");
            		}
            	}
                pw.println();
            }
        }
        sn.close();
        pw.close();
        header.clear();
        DatabaseUtil.cleanUp();
    }

    private String getFileName(File aff){
        String [] names = aff.getName().split("\\.");
        return names[0];

    }

    private void skipHeader() {
        while(sn.hasNext()){
            String line = sn.nextLine();
            if(line.startsWith("  Pos          HLA")){
                break;
            }else {
                continue;
            }
        }
        if(sn.hasNext()){
            //jump the dash line
            sn.nextLine();
        }
    }

    private void loadMetaData(File meta) {
        Scanner sn = null;
        try {
            sn = new Scanner(meta);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find meta file");
            e.printStackTrace();
            return;
        }
        if(sn != null){
            while(sn.hasNext()){
                header.add(addFreqIntoHead(sn.nextLine()));
            }
            sn.close();
        }

    }
    
    private String addFreqIntoHead(String head){
    	String[] data = head.split(",");
    	StringBuilder sb = new StringBuilder();
    	String chrome = data[0];
    	String pos = data[1];
    	String change1 = data[2];
    	String change2 = data[3];
    	ResultSet rs = DatabaseUtil.getFreq(chrome,  pos, change1, change2);
    	try {
			while(rs.next()){
				sb.append(chrome);
				sb.append(",");
				sb.append(pos);
				sb.append(",");
				sb.append(change1);
				sb.append(",");
				sb.append(rs.getString(change1).replace(',', '/'));
				sb.append(",");
				sb.append(change2);
				sb.append(",");
				sb.append(rs.getString(change2).replace(',', '/'));
				sb.append(",");
				sb.append(data[4]);
				sb.append(",");
				sb.append(data[5]);
				sb.append(",");
				if(data.length > 7){
					//print donor or recipient
					sb.append(data[7]);
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(sb.length() == 0){
    		sb.append(chrome);
			sb.append(",");
			sb.append(pos);
			sb.append(",");
			sb.append(change1);
			sb.append(",");
			sb.append("NA");
			sb.append(",");
			sb.append(change2);
			sb.append(",");
			sb.append("NA");
			sb.append(",");
			sb.append(data[4]);
			sb.append(",");
			sb.append(data[5]);
			sb.append(",");
			if(data.length > 7){
				//print donor or recipient
				sb.append(data[7]);
			}
    		
    	}
    	return sb.toString();
    }
}
