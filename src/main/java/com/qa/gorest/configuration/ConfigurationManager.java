package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkException.APIFrameworkException;

public class ConfigurationManager {

	private Properties prop;
	private FileInputStream ip;
	
	public Properties initProp()  {
		//maven; cmd line argument
		//mvn clean install -Denv="qa"

		String envName = System.getProperty("env");
		System.out.println("Running tests on env :"+ envName);
		
		if(envName == null) {
			System.out.println("no env is given ...hemce running testst on qa env...");
			try {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Running tests on env : "+ envName);
			try {
			switch (envName.toLowerCase().trim()) {
			case "qa":  
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "stage":
				ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;		
			case "prod":
				ip = new FileInputStream("./src/test/resources/config/prod.config.properties");
				break;	
			default:
				System.out.println("please pass the right envirment name ..."+ envName);
				throw new APIFrameworkException("No ENV is given");
				
			}
			}catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		try {
			prop.load(ip);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return prop;
	}
	
	
}
