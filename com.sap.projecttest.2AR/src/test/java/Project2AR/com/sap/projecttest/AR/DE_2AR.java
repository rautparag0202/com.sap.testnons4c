package Project2AR.com.sap.projecttest.AR;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;

import junit.framework.Assert;


public class DE_2AR {


	public static WebDriver driver=null;
	public static Properties p=new Properties();
	public static String browser1="chrome";
	public static String flexstr="";
	public static Actions act=null;
	public static String bp_no="";
	public static String Businesspartner="ab cd bdv rg";

	/* ************************Function to get Extent Report for .html files **************************** */




	/* ************************Function to get config(properties) file data **************************** */

	public static void configfile() throws Exception
	{

		File f=new File("config.properties");
		FileInputStream fis=new FileInputStream(f);
		p.load(fis);

	}

	/* ************************Function to get todays date **************************** */

	public static String date() throws Exception
	{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date dateobj = new Date();
		String date=df.format(dateobj).toString();
		return date;
	}

	/* ************************Function to get flexible string (alphanumeric) data **************************** */

	public static String flexstring() throws Exception
	{
		Random rno=new Random();
		int flexnum=rno.nextInt(100);
		DateFormat df = new SimpleDateFormat("s");
		Date d = new Date();
		flexstr=df.format(d.getSeconds());
		return (flexstr+flexnum);
	}

	/* ************************Function to get Screen shot  ********************************************* */

	public void ScreenShot() throws Exception
	{
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest=new File(System.getProperty("user.dir")+ "\\com.sap.projecttest.2AR\\target\\screenshots"+ date());
		FileUtils.copyFile(src, dest);
	}	

	/* ************************Function to Launch a browser (check the browser path in properties file) **************************** */

	public static void Launch(String browser)
	{

		if(browser1.equalsIgnoreCase("chrome"))
		{

			System.setProperty("webdriver.chrome.driver", p.getProperty("path1"));
			driver=new ChromeDriver();
		}
		if(browser1.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", p.getProperty("path2"));
			driver=new FirefoxDriver();
		}
		if(browser1.equalsIgnoreCase("IE"))
		{
			System.setProperty("webdriver.ie.driver", p.getProperty("path3"));
			driver=new InternetExplorerDriver();
		}
	}

	/* ************************Function to Create a Business Partner - from 2AR  **************************** */

	@Test
	public void CreateBP() throws Exception
	{
		configfile();
		Launch(browser1);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 

		driver.get(p.getProperty("url")+"BusinessPartner-maintain?sap-ui-tech-hint=GUI");
		driver.findElement(By.xpath("//input[@id='USERNAME_FIELD-inner']")).sendKeys(p.getProperty("username1"));
		driver.findElement(By.xpath("//input[@id='PASSWORD_FIELD-inner']")).sendKeys(p.getProperty("password"),Keys.ENTER);

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(2000);

		WebElement frm1=driver.findElement(By.id("application-BusinessPartner-maintain")); 
		driver.switchTo().frame(frm1);

		WebDriverWait fw=new WebDriverWait(driver, 10000);
		fw.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@title='Business Partner Number']")));

		driver.findElement(By.xpath("//div[@title='Create Person (F5)']")).click();
		fw.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//input[@title='BP Role for Screen Usage']"))));

		driver.findElement(By.xpath("//input[@title='BP Role for Screen Usage']")).click();
		driver.findElement(By.xpath("//div[text()='Contract Partner']")).click();
		driver.findElement(By.xpath("//div[@title='Create ']")).click();

		Thread.sleep(4000);

		fw.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@title='Business Partner Number']")));		
		driver.findElement(By.xpath("//input[@title='Business Partner Number']")).click();
		driver.findElement(By.xpath("//input[@title='Business Partner Number']")).sendKeys("BP_DE"+flexstring());
		driver.findElement(By.xpath("//span[text()='Business Partner Grouping']/following::td[1]")).click();
		driver.findElement(By.xpath("//div[text()='External alpha-numeric numbering']")).click();

		Thread.sleep(3000);	

		driver.findElement(By.xpath("//*[contains(@title, 'Last Name of')]")).sendKeys("Test"+flexstring());
		driver.findElement(By.xpath("//div[text()='Payment Transactions'][@role='tab']")).click();
		driver.findElement(By.xpath("//div[@title='Bank details ID']/following::input[1]")).sendKeys("01",Keys.TAB,"DE",Keys.TAB,"10020030",
				Keys.TAB,"12445079");
		driver.findElement(By.xpath("//div[@title='Bank details ID']/following::div[@title='Define IBAN '][1]")).click();

		Thread.sleep(3000);

		try {
			driver.findElement(By.xpath("//div[@title='Change Documents (F6)']/preceding::div[@title='Continue (Enter)']")).click();
			Thread.sleep(3000);
		}
		catch(Exception e)
		{
			driver.findElement(By.xpath("//div[@title='Continue (Enter)']")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@title='Continue (Enter)']")).click();
			Thread.sleep(3000);
		}


		WebElement payid=driver.findElement(By.xpath("//div[@title='Card Number']/following::span[@name='InputField'][1]"));

		Thread.sleep(2000);

		payid.click();

		driver.findElement(By.xpath("//div[@title='Card Number']/following::input[@name='InputField'][1]")).sendKeys("ID001", Keys.ENTER);
		Thread.sleep(8000);

		ArrayList<String> wind=new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(wind.get(1));


		driver.findElement(By.xpath("//input[@id='c-cardnumber']")).sendKeys("378282246310005",Keys.TAB,"Test",Keys.TAB,"j",Keys.TAB,"2",Keys.TAB,"420");
		Select s=new Select(driver.findElement(By.id("c-exyr")));
		s.selectByIndex(4);

		driver.findElement(By.xpath("//input[@id='submit-btn']")).click();
		fw.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Close")));
		driver.findElement(By.linkText("Close")).click();

		driver.switchTo().window(wind.get(0));
		driver.switchTo().frame(frm1);
		fw.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@title='Payment cards: Issuing bank']")));
		driver.findElement(By.xpath("//input[@title='Payment cards: Issuing bank']")).sendKeys("Test");
		fw.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Continue (Enter)']")));
		driver.findElement(By.xpath("//div[@title='Continue (Enter)']")).click();

		Thread.sleep(4000);
		fw.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@title,'Cancel ')]")));

		driver.findElement(By.xpath("//div[@title='Save (Ctrl+S)']")).click();
		fw.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@title,'Business partner BP')]")));

		try {
			WebElement BP=driver.findElement(By.xpath("//span[contains(@title,'Business partner BP')]"));
			String Businesspartner=BP.getText().toString();
			System.out.println(Businesspartner);
			String[] bparr=Businesspartner.split(" ");
			for(int i=0;i<bparr.length;i++)
			{
				bp_no=bparr[2];

			}
		}
		catch(Exception ex)
		{
			WebElement BP=driver.findElement(By.xpath("//span[contains(text(),'Business partner BP')][@role='presentation']"));
			String Businesspartner=BP.getText().toString();
			System.out.println(Businesspartner);
			String[] bparr=Businesspartner.split(" ");
			for(int i=0;i<bparr.length;i++)
			{
				bp_no=bparr[2];
			}
		}
		this.ScreenShot();
		driver.close();
	}

	/* ************************Function to Create a Contract Account - from 2AR  **************************** */

	@Test(dependsOnMethods = { "CreateBP" })
	public void CreateCA() throws Exception
	{

		configfile();
		Launch(browser1);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.get(p.getProperty("url")+"ContractAccount-manage?sap-ui-tech-hint=GUI");
		driver.findElement(By.xpath("//input[@id='USERNAME_FIELD-inner']")).sendKeys(p.getProperty("username1"));
		driver.findElement(By.xpath("//input[@id='PASSWORD_FIELD-inner']")).sendKeys(p.getProperty("password"),Keys.ENTER);

		Thread.sleep(5000);
		WebDriverWait ww=new WebDriverWait(driver, 100);

		WebElement frm2=driver.findElement(By.id("application-ContractAccount-manage"));
		driver.switchTo().frame(frm2);

		driver.findElement(By.xpath("//div[@title='Create (F5)']")).click();
		Thread.sleep(2000);
		ww.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@title='Contract Account Number']")));
		driver.findElement(By.xpath("//input[@title='Contract Account Number']")).sendKeys("CA_DE"+flexstring());
		ww.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@title='Business Partner Number']")));
		driver.findElement(By.xpath("//input[@title='Business Partner Number']")).sendKeys(bp_no,Keys.TAB,"Y1");
		driver.findElement(By.xpath("//input[@title='Validity Date of Changes']")).sendKeys(date(),Keys.ENTER);
		ww.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Cont Acct Name']/following::input[@title='Contract Account Name']")));
		driver.findElement(By.xpath("//span[text()='Cont Acct Name']/following::input[@title='Contract Account Name']")).sendKeys(flexstring());
		driver.findElement(By.xpath("//input[contains(@title,'Relationship of Business Pa')]")).click();
		driver.findElement(By.xpath("//input[contains(@title,'Relationship of Business Pa')]")).sendKeys(Keys.ARROW_DOWN,Keys.TAB);
		driver.findElement(By.xpath("//input[contains(@title,'Tolerance group for contra')]")).click();
		driver.findElement(By.xpath("//input[contains(@title,'Tolerance group for contra')]")).sendKeys(Keys.ARROW_DOWN,Keys.TAB);
		driver.findElement(By.xpath("//input[@title='Payment Condition']")).sendKeys("YN01",Keys.TAB,"Y1",Keys.TAB);

		driver.findElement(By.xpath("//div[text()='Payments/Taxes'][@role='tab']")).click();
		ww.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@title='Company Code Group']")));
		driver.findElement(By.xpath("//input[@title='Company Code Group']")).sendKeys("1010",Keys.TAB,"1010",Keys.TAB);
		driver.findElement(By.xpath("//div[@title='Save (Ctrl+S)']")).click();
		ww.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Continue (Enter)']")));
		
		this.ScreenShot();
		WebElement cn_no=driver.findElement(By.xpath("//span[contains(text(),'Contract account')][contains(@title,'Contract account')]"));
		
		System.out.println(cn_no.toString());
		
		driver.findElement(By.linkText("text"));
	}

}
