package com.css.stube.Aadhaar.SimulationStub.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;





public  class  LoadData {
	
	
	static HashMap<String,String>  aadhaarcache = new HashMap<String,String>();
	static HashMap<String,String>  referencecache = new HashMap<String,String>();
	
	public static String load(String filename) {
		
	try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String str;
			while((str=br.readLine(	))!=null)
			{
				String line[] = str.split(",");
				aadhaarcache.put(line[0], line[1]);
				referencecache.put(line[1], line[0]);
			}
			br.close();
			return "SUCCESS";
		}
		catch(Exception e)
		{
			System.out.println("I/O Exception");
			return "FAILED";
		}
		
	}

}
