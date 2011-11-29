/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.logging.Level;

/**
 *
 * @author Laurens
 */
public class Logger {
	
	private static String logFileUrl = "logs/" + Calendar.getInstance().getTime().getTime() + "_log.txt";
	private static boolean logCreated = false;
	
	public static void log(String logText){
		File logFile;
		logFile = new File(logFileUrl);
		if(!logFile.exists()){
			try {
				logFile.createNewFile();
			} catch (IOException ex) {
				java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		try {
			Writer writer;
			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(logText + "\n");
			writer.close();
		} catch (IOException ex) {
			
		}
		
	}
}
