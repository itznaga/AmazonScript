package basePackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.annotations.BeforeTest;

import dataLibrary.ReadExcel;

public class BaseClass extends ReadExcel {
	
	@BeforeTest
	public void launchBrowser()
	{
		driver = new org.openqa.selenium.chrome.ChromeDriver();
	}
	
	public static void writeFile(String location,String str) throws IOException
	{
		File fl = new File(location);
		if(fl.exists())
		{
			fl.delete();
			fl.createNewFile();
		}
		BufferedWriter br = new BufferedWriter(new FileWriter(fl));
		br.write(str);
		System.out.println("New Text File with Suggestions created");
		br.close();
	}
}
