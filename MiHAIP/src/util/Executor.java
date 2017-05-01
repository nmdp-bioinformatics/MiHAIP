package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Executor {
	
	public void executeCommand(String command) {

		System.out.println(command);

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
