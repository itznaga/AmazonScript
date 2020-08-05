package testCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import basePackage.BaseClass;

public class AmazonScenario extends BaseClass {

	@DataProvider(name="AmazonData")
	public Object[][] data2() throws InvalidFormatException, IOException
	{
		Object[][] readExcelDP = readExcelDP("./testData/testData.xlsx");
		return readExcelDP;
	}


	@Test(dataProvider= "AmazonData")
	public void testScenario1(String testData) throws InterruptedException, IOException
	{
		driver.get("http://amazon.in");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		List<WebElement> findElements;
		List<String> Suggestions = new ArrayList<String>();
		for(int i=0;i<testData.length();i++)
		{
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys(testData.charAt(i)+"");
			if(testData.charAt(i)=='s')
			{
			break;
			}
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@id,'issDiv')]"))));
			//			wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//div[contains(@id,'issDiv')]")))));
			Thread.sleep(2000);
			findElements = driver.findElements(By.xpath("//div[contains(@id,'issDiv')]"));	
			for(WebElement wb : findElements )
			{
				Thread.sleep(2000);
				wait.until(ExpectedConditions.elementToBeClickable(wb));
				Suggestions.add(wb.getText());
				if(wb.getText().equalsIgnoreCase("books bestsellers english")) 
				{
					driver.findElement(By.xpath("//div[contains(@id,'issDiv')]/span[contains(text(),' bestsellers english')]")).click();	
				}	
				break;
			}
			
		}

		System.out.println(Suggestions.toString());
		writeFile("./Suggestions/suggestions.txt",Suggestions.toString());
		String Price = driver.findElement(By.xpath("(//span[@class='a-price-whole'])[1]")).getText();
		driver.findElement(By.xpath("(//span[@class='a-size-medium a-color-base a-text-normal'])[1]")).click();
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> windows = new ArrayList<String>(windowHandles);
		driver.switchTo().window(windows.get(1));
		String text = driver.findElement(By.xpath("//span[@class='a-size-medium a-color-price inlineBlock-display offer-price a-text-normal price3P']")).getText();
		String replaceAll = text.replaceAll("[^0-9]","");
		String replaceAll2 = replaceAll.replaceAll("0", "");
		if(Price.equalsIgnoreCase(replaceAll2))
		{
		System.out.println("Price is same in both cart and content page ="+replaceAll2);
		}
	}
}
