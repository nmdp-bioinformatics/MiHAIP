package command;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class CommandHelper {
	 public static void runAndSave(String command, String file) throws IOException {

		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(command);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		File output = new File(file);
		PrintWriter pw = new PrintWriter(output);

		// read the output from the command
		String s = null;
		while ((s = stdInput.readLine()) != null) {
//			System.out.println(s);
			pw.println(s);
		}

		pw.close();
	}
	 
	public static void runBash(File tempScript){
		try {
	        ProcessBuilder pb = new ProcessBuilder("bash", tempScript.toString());
	        pb.inheritIO();
	        Process process = pb.start();
	        process.waitFor();
	    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        tempScript.delete();
	    }
	}

}
