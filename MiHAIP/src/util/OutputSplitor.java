package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class OutputSplitor {
	private PrintWriter indexWriter;
	private PrintWriter dataWriter;
	int counter = 1;
	public void split(String input, String fileName) throws FileNotFoundException{
		Scanner scanner = new Scanner(new File(input));
		indexWriter = new PrintWriter(fileName + "_meta.txt");
		dataWriter = new PrintWriter(fileName + ".pep");
		
		while(scanner.hasNextLine()){
			try{
				process(scanner.nextLine(), scanner.nextLine());
			}catch(NoSuchElementException e){
				System.out.println("split finished.");
			}
			
		}
		scanner.close();
		indexWriter.close();
		dataWriter.close();
	}
	private void process(String header, String data) {
		if(header == null || data == null){
			System.out.println("null data");
			return;
		}
		frameShift(header,data, 11);
		frameShift(header, data, 10);
		frameShift(header, data, 9);
		frameShift(header, data, 8);
		
	}
	private void frameShift(String header, String data, int size) {
		for(int i = 0; i<= data.length() - size; i++){
			String shift = data.substring(i, i+size);
			boolean hasLowercase = !shift.equals(shift.toUpperCase());
			if(hasLowercase){
				header = header.replaceAll("\\|", "\t");
				header = header.replaceAll(">", "");
				indexWriter.print(header + "\t"+counter);
				indexWriter.println("\t"+shift);
				dataWriter.println(shift);
				counter++;
			}
		}
		
	}

}
