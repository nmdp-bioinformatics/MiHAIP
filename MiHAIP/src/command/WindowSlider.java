package command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.FileHelp;

public class WindowSlider {
	public void run() throws FileNotFoundException{
		File refProtein = new File(FileHelp.getRefProtein());
		File altProtein = new File(FileHelp.getAltProtein());
		
		processFile(refProtein, new File(FileHelp.getRefSliceMeta()), new File(FileHelp.getRefSlice()));
		processFile(altProtein, new File(FileHelp.getAltProteinSliceMeta()), new File(FileHelp.getRefSlice()));
		
	}
	
	private void processFile(File input, File meta, File output) throws FileNotFoundException{
		Scanner sn = new Scanner(input);
		PrintWriter metaWriter = new PrintWriter(meta);
		PrintWriter dataWriter = new PrintWriter(output);
		while(sn.hasNextLine()){
			String Header = sn.nextLine();
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
		metaWriter.close();
		dataWriter.close();
	}
	
	public List<String> slide(String seq, int windowSize){
		List<String> result = new ArrayList<String>();
		if(windowSize > seq.length()){
			result.add(seq);
		}else{
			for(int i = 0; i <= seq.length() - windowSize; i++){
				result.add(seq.substring(i,i+windowSize));
			}
		}
		return result;
	}

}
