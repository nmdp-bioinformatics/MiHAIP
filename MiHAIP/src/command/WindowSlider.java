package command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.FileHelp;

public class WindowSlider {
	PrintWriter metaWriter;
	PrintWriter dataWriter;
	public void run() throws FileNotFoundException{
		File refProtein = new File(FileHelp.getDonorProtein());
		File altProtein = new File(FileHelp.getRecipientProtein());
		setPrinters();
		processFile(refProtein, "Donor");
		processFile(altProtein, "Recipient");
		metaWriter.close();
		dataWriter.close();
	}
	
	private void setPrinters() throws FileNotFoundException {
		metaWriter = new PrintWriter(FileHelp.getChopMetaData());
		dataWriter = new PrintWriter(FileHelp.getProteinSlice());
	}

	private void processFile(File input, String header) throws FileNotFoundException{
		Scanner sn = new Scanner(input);
		Scanner metaDataScanner = new Scanner(new File(FileHelp.getMetaData()));
		while(sn.hasNextLine()){
			//skip fasta header
			sn.nextLine();
			String Header = metaDataScanner.nextLine();
			//skip location line
			metaDataScanner.nextLine();
			Header += ","+header + ",";
			String seq = sn.nextLine();
			List<String> slices = slide(seq, 8);
			for(String data : slices){
				metaWriter.println(Header);
				dataWriter.println(data);
			}
			slices = slide(seq, 9);
			for(String data : slices){
				metaWriter.println(Header);
				dataWriter.println(data);
			}
			slices = slide(seq, 10);
			for(String data : slices){
				metaWriter.println(Header);
				dataWriter.println(data);
			}
			slices = slide(seq, 11);
			for(String data : slices){
				metaWriter.println(Header);
				dataWriter.println(data);
			}
		}
		sn.close();
		metaDataScanner.close();
	}
	
	public List<String> slide(String seq, int windowSize){
		List<String> result = new ArrayList<String>();
		if(windowSize > seq.length()){
			result.add(seq);
		}else{
			for(int i = 0; i <= seq.length() - windowSize; i++){
				String slice = seq.substring(i,i+windowSize);
				if(containsVar(slice)){
					result.add(slice);
				}
				
			}
		}
		return result;
	}

	private boolean containsVar(String slice) {
		String changeCase = slice.toUpperCase();
		return !slice.equals(changeCase);
	}

}
