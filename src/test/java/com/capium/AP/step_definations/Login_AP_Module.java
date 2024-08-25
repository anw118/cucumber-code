package com.capium.AP.step_definations;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.capium.AP.utility.Log;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Login_AP_Module {
	
  public static WebDriver driver;
	
  
  String URL="https://app.capium.com/sign-in.aspx?redir=%2F";
  String username="anwar.udayagiri@capium.com";
  String password="Qa@1234567890";
  String filepath="D:\\Capium\\selenium-AP-project\\src\\test\\resources\\configue\\client data.xlsx";
  String CT_name="anwar118";
  private static Logger Log = Logger.getLogger(Log.class.getName());
	@Given("openApplication")
	public void open_application() {
		//DOMConfigurator.configure("log4j.xml");
		//browser launch
		String browser = System.getProperty("browser", "chrome");
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            Log.info("Chrome browser launched sucessfully");
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            Log.info("Firefox browser launched sucessfully");
        } 
        else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            Log.info("Edge browser launched sucessfully");
        } 
        else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
           
        }
        driver.get("https://www.google.com/");
        driver.navigate().to(URL);
        Log.info("capium Application opened");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
       
	}

	@Given("enter username and password click login")
	public void enter_username_and_password_click_login() throws InterruptedException  {
		//DOMConfigurator.configure("log4j.xml");
		// login details
		driver.findElement(By.xpath("//input[@name='txtusername']")).sendKeys(username);
		Log.info("username entered"+username);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='txtpassword']")).sendKeys(password);
		Log.info("password entered"+password);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Login']")).click();
		Log.info("login buttin clicked");
		Log.info("login sucessfully into capium Application");
	   
	}

	@Given("redirect to Accounts production home page")
	public void redirect_to_accounts_production_home_page() throws InterruptedException {
		//DOMConfigurator.configure("log4j.xml");
		//redirecting to AP module page
		Thread.sleep(2000);
		driver.findElement(By.xpath("//img[@alt='Capium']//parent::i//parent::a[@title='Modules']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='Accounts Production']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[contains(@id,'sidebar')]")).click();
		Log.info("redirected to AP module sucessfully");
		
	}

	

	@When("enter required data to create client")
	public void enter_required_data_to_create_client() throws IOException, InterruptedException {
		//DOMConfigurator.configure("log4j.xml");
		
		String name="";
		String address1="";
		String address2="";
		String city="";
		int postpin; 
		String Country="";
		String emailid="";
		int mobilenum;
		String website="";
		int urtno;
		
		FileInputStream fp=new FileInputStream(filepath);
		XSSFWorkbook wb=new XSSFWorkbook(fp);
		XSSFSheet sheet=wb.getSheet("Sheet1");
		int rowcount=sheet.getLastRowNum()-sheet.getFirstRowNum();
		System.out.println(rowcount);
		
for(int i=1; i<=rowcount-1; i++) {
	Thread.sleep(3000);
	driver.findElement(By.xpath("//button[text()='Client']")).click();
			XSSFRow row=sheet.getRow(i);
			//excel data
			 name=row.getCell(0).getStringCellValue();
			 address1 =row.getCell(1).getStringCellValue();
			 address2=row.getCell(2).getStringCellValue();
			 city=row.getCell(3).getStringCellValue();
		     postpin=(int)row.getCell(4).getNumericCellValue();
		     Country=row.getCell(5).getStringCellValue();
			 emailid=row.getCell(6).getStringCellValue();
			 mobilenum=(int)row.getCell(7).getNumericCellValue();
		     website=row.getCell(8).getStringCellValue();
			 urtno=(int)row.getCell(9).getNumericCellValue();

          
		//client data
		driver.findElement(By.id("Name")).sendKeys(name);
		driver.findElement(By.name("Address1")).sendKeys(address1);
		driver.findElement(By.name("Address2")).sendKeys(address2);
		driver.findElement(By.name("Address3")).sendKeys(city);
		driver.findElement(By.id("Zip")).sendKeys(String.valueOf(postpin));
		driver.findElement(By.name("State")).sendKeys(Country);
		driver.findElement(By.name("EMail")).sendKeys(emailid);
		driver.findElement(By.name("Phone")).sendKeys(String.valueOf(mobilenum));
		driver.findElement(By.id("Website")).sendKeys(website);
		driver.findElement(By.id("TaxRefNo")).sendKeys(String.valueOf(urtno));
		Thread.sleep(3000);
		 driver.findElement(By.xpath("//button[text()='Save']")).click();
		 
		 Log.info("Client created :"+i+"sucessfully");
		// Store the current window handle
		 String mainWindowHandle = driver.getWindowHandle();

		 // Get all window handles
		 Set<String> windowHandles = driver.getWindowHandles();

		 // Switch to the new window
		 for (String windowHandle : windowHandles) {
		     if (!windowHandle.equals(mainWindowHandle)) {
		         driver.switchTo().window(windowHandle);
		         break;
		     }
		 }	
      }   
          
	}

	@And("close")
	public void close() {
	   driver.close();
	}
	

}
