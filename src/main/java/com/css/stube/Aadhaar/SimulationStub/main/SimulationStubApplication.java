package com.css.stube.Aadhaar.SimulationStub.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;




@SpringBootApplication
@EnableSwagger2
public class SimulationStubApplication {
	
	public static String filename="";	
	
	public static void main(String[] args) {
		
		if(args.length<1)
		{
			System.out.println("Specify the data file path");
			System.exit(0);
		}
		filename = args[0];
		System.out.println("file name:" + args[0]);
		
		
		LoadData.load(filename);
		
		SpringApplication.run(SimulationStubApplication.class, args);
	}
	

}
