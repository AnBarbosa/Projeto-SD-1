package util;

import java.io.OutputStream;

public class MsgUtils {
	public static boolean isDebugging = true;
	public static java.io.PrintStream errStream = System.err;
	public static java.io.PrintStream outStream = System.out;
	 
	 
	public static void setErrStream(java.io.PrintStream s) {
		errStream = s;
	}
	
	public static void setOutStream(java.io.PrintStream s) {
		outStream = s;
	}
	
	public static void errorPrintln(String msg) {
		if(isDebugging){
			errStream.println(msg);
		}
	}
	
	public static void errorPrintf(String msg, Object ...objects) {
		if(isDebugging){
			errStream.printf(msg, objects);
		}
	}
	
	public static void println(String msg) {
		outStream.println(msg);
	}
	
	public static void printf(String msg, Object...objects) {
		outStream.printf(msg, objects);
	}
}
