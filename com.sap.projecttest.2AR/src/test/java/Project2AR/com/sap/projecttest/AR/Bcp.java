package Project2AR.com.sap.projecttest.AR;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class Bcp
{

	@Test
	public void launch() throws Exception
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\i501021\\eclipse-workspace\\com.sap.projecttest.2AR\\target\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://support.wdf.sap.corp/sap(bD1lbiZjPTAwMSZkPW1pbg==)/crm_logon/default.htm");
		Thread.sleep(2000);
		driver.findElement(By.linkText("ZCSSINTPROCE-Development / IMS")).click();
		Thread.sleep(3000);
		driver.switchTo().frame(0);
		driver.switchTo().frame(1);
		driver.findElement(By.linkText("Incident 2018")).click();
		
	}
}
