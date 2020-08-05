package extentReports;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Reports {

public static RemoteWebDriver driver;
	
	public static ExtentHtmlReporter reporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	public String testcaseName, author;
	
	
	
	@BeforeSuite
	public void startReport() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
	}
	
    @BeforeClass
	public void report() throws IOException {
    	String name = this.getClass().getName();
    	reporter = new ExtentHtmlReporter("./reports/"+name+".html");
//    	if(extent==null)
//		{
		extent   = new ExtentReports();
		extent.attachReporter(reporter);
//		}
		 
    	
	}
    public static String takeScreenshot(String name)
    {
    	String encodedBase64 = null ;
    	try {
			FileHandler.copy(driver.getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+name+".jpg"));
			FileInputStream fs =new FileInputStream(new File("./reports/images/"+name+".jpg"));
	        byte[] bytes =new byte[(int)new File("./reports/images/"+name+".jpg").length()];
	        fs.read(bytes);
	        encodedBase64 = new String(Base64.encodeBase64(bytes));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return encodedBase64;
    }
    
    public static void reportLogWithSS(String dec, String status ) throws IOException {
    	String ss ="Screenshot"+Math.random();
    	MediaEntityModelProvider img = null;
			String encoded = takeScreenshot(ss);
				img = MediaEntityBuilder.createScreenCaptureFromBase64String(encoded).build();
    	if(status.equalsIgnoreCase("pass")) {
    		test.pass(dec, img);
    	} else if(status.equalsIgnoreCase("fail")) {
    		test.fail(dec, img); 
    	}
    }
    
    public static void reportLogWithoutSS(String desc,String status)
    {
    	if(status.equalsIgnoreCase("pass")) {
    		test.pass(desc);
    	}
    	else
    	{
    		test.fail(desc);
    	}
    }

    
    @AfterSuite
    public void stopReport() {
    	extent.flush();
    }
}

