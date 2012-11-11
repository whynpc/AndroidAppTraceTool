package edu.ucla.cs.tracetool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import android.os.Environment;

public class FileUtility {

	/*File APIs*/
	public static boolean deleteFile(String fileName) {
    	try{
	    	File file = new File(Environment.getExternalStorageDirectory(), fileName);
	    	boolean deleted = file.delete();
	    	return deleted;
	    		  
    	}catch(Exception e){

    		e.printStackTrace(System.err);
    	}
		return false;	
	}
	
    public static void writeLineToFile(String fileName, String input, Boolean append) {
     	
    	try{
    		if (append == false) {
    			deleteFile(fileName);
    		}    		
	    	File file = new File(Environment.getExternalStorageDirectory(), fileName);
	    	FileWriter fw = new FileWriter(file,true);
	    		  
	    	fw.append(input);
	    	fw.append("\n");
	    	fw.flush();
	    	fw.close();
    	}catch(Exception e){

    		e.printStackTrace(System.err);
    	}
    }
    
    public static void writeDataToFile(String fileName, String input, Boolean append) {
     	
    	try{
    		if (append == false) {
    			deleteFile(fileName);
    		}    		
	    	File file = new File(Environment.getExternalStorageDirectory(), fileName);
	    	FileWriter fw = new FileWriter(file,true);
	    		  
	    	fw.append(input);
	    	fw.flush();
	    	fw.close();
    	}catch(Exception e){

    		e.printStackTrace(System.err);
    	}
    }
    
    public static String readAllLinesFromFile(String fileName) {
    	String output = "";
    	try{    		
	    	File rfile = new File(Environment.getExternalStorageDirectory(), fileName);
	    	if (!rfile.exists())
	    		return null;
	    	
	    	FileInputStream fileIS = new FileInputStream(rfile);	    	 
	    	BufferedReader buf = new BufferedReader(new InputStreamReader(fileIS));
	    	
	    	String temp = buf.readLine();
	    	
	    	while(temp != null){
	    		output += temp;
	    		temp = buf.readLine();
	    	};
	    	
	    	return output;
    		    		
    	}catch(Exception e){
    		e.printStackTrace();
    		String a = e.toString();
    		System.out.println(a);
    		return null;
    	}
    }    
}