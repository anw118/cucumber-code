package com.capium.AP.step_definations;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



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
	@Given("openApplication")
	public void open_application() {
		String browser = System.getProperty("browser", "chrome");
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } 
        else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } 
        else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        driver.get("https://www.google.com/");
        driver.navigate().to(URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
       
	}

	@Given("enter username and password click login")
	public void enter_username_and_password_click_login() throws InterruptedException  {
		driver.findElement(By.xpath("//input[@name='txtusername']")).sendKeys(username);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='txtpassword']")).sendKeys(password);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Login']")).click();
	   
	}

	@Given("redirect to Accounts production home page")
	public void redirect_to_accounts_production_home_page() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//img[@alt='Capium']//parent::i//parent::a[@title='Modules']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='Accounts Production']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[contains(@id,'sidebar')]")).click();
		
	}

	@Given("close")
	public void close() {
	   driver.close();
	}

	@When("enter required data to create client")
	public void enter_required_data_to_create_client() throws IOException, InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Client']")).click();

		FileInputStream fp=new FileInputStream(filepath);
		XSSFWorkbook wb=new XSSFWorkbook(fp);
		XSSFSheet sheet=wb.getSheet("Sheet1");
		int rowcount=sheet.getLastRowNum();
		System.out.println(rowcount);
		
for(int i=1; i<=rowcount; i++) {
			
			XSSFRow row=sheet.getRow(i);
			Thread.sleep(2000);
			String name=row.getCell(0).getStringCellValue();
			String address1 =row.getCell(1).getStringCellValue();
			String address2=row.getCell(2).getStringCellValue();
			String city=row.getCell(3).getStringCellValue();
		    int postpin=(int)row.getCell(4).getNumericCellValue();
			String Country=row.getCell(5).getStringCellValue();
			String emailid=row.getCell(6).getStringCellValue();
			int mobilenum=(int)row.getCell(7).getNumericCellValue();
			String website=row.getCell(8).getStringCellValue();
			int urtno=(int)row.getCell(9).getNumericCellValue();

          
		
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
		
}   
          
	}

	@When("click on save")
	public void click_on_save() {
		 driver.findElement(By.xpath("//button[text()='Save']")).click();
	}
	
	@When("check whether the employee is created or not")
	public void check_whether_the_employee_is_created_or_not() throws InterruptedException {
		WebElement client=	driver.findElement(By.id("txtSearch"));
		client.sendKeys(CT_name);
		Thread.sleep(4000);
			
			driver.close();
	}

}
