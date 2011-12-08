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
	
	public static void log(String logText){
		
		File logFile;
		logFile = new File(logFileUrl);
		
		if(!logFile.exists()){
			try {
				logFile.createNewFile();
				write("Logger instantiated", logFile);
			} catch (IOException ex) {
				java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		write(logText, logFile);
	}
	
	private static void write(String logText, File logFile) {		
		String hour = Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		String minute = Integer.toString(Calendar.getInstance().get(Calendar.MINUTE));
		hour = hour.length() < 2 ? 0 + hour : hour;
		minute = minute.length() < 2 ? 0 + minute : minute;
		
		try {
			Writer writer; 
			writer = new BufferedWriter(new FileWriter(logFile));
			writer.append(hour + ":" + minute + " " + logText + "\n");
			writer.close();
		} catch (IOException ex) {
			
		}
	}
	
	public static void clearLogs() {
		
		File dir = new File("logs");		
		File[] logs = dir.listFiles();
		
		for (File file : logs) {
			String fileExtension = file.toString().substring(file.toString().lastIndexOf("."));
			if (fileExtension.equals(".txt")) {
				file.delete();
			}
		}
	}
	
}
