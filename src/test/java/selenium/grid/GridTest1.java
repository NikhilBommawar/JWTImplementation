package selenium.grid;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

//import org.junit.jupiter.api.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class GridTest1 {

       WebDriver driver;
        String nodeURL;

    public GridTest1() throws MalformedURLException {
    }


        @BeforeMethod()
        public void setUp() throws MalformedURLException {

            String nodeURL = "http://192.168.1.7:4444/";
             System.out.println("Chrome Browser Initiated");

            WebDriverManager.chromedriver().setup();

            DesiredCapabilities capabilities =  new DesiredCapabilities();

            ChromeOptions chromeOptions = new ChromeOptions();

//            chromeOptions.addArguments("--remote-allow-origins=*");
//            chromeOptions.addArguments("--headless=new");

            capabilities.setBrowserName(chromeOptions.getBrowserName());

            driver = new RemoteWebDriver(new URL(nodeURL),capabilities);

        }


    @Test
    public void testLogin(){

//        WebDriver driver = new ChromeDriver();

        driver.get("http://192.168.1.3:3000/");

        System.out.println("================= getting username password elements");
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));

        System.out.println("================= setting username password fields");
        username.sendKeys("admin");
        password.sendKeys("admin");

        WebElement loginButton = driver.findElement(By.className("loginbutton"));

        System.out.println("================= logging by click");

        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        wait.until(ExpectedConditions.invisibilityOf(loginButton));

        String actualUrl="http://192.168.1.3:3000/profile";

        junit.framework.Assert.assertEquals(actualUrl,driver.getCurrentUrl());

        driver.quit();


    }

    @Test
    public void testLogin23(){

//        WebDriver driver = new ChromeDriver();

        driver.get("http://192.168.1.3:3000/");

        System.out.println("================= getting username password elements");
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));

        System.out.println("================= setting username password fields");
        username.sendKeys("admin");
        password.sendKeys("admi");

        WebElement loginButton = driver.findElement(By.className("loginbutton"));

        System.out.println("================= logging by click");

        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        wait.until(ExpectedConditions.invisibilityOf(loginButton));

        String actualUrl="http://192.168.1.3:3000/profile";

        junit.framework.Assert.assertNotSame(actualUrl,driver.getCurrentUrl());

        driver.quit();


    }
    @Test
    public void testLogin2(){

//        WebDriver driver = new ChromeDriver();
        driver.get("http://192.168.1.7:3000/");

        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));

        username.sendKeys("nikhil");
        password.sendKeys("nikhi");

        WebElement loginButton = driver.findElement(By.xpath("//button[@id='loginBtn']"));

        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));

        String actualUrl="http://192.168.1.7:3000/";

        junit.framework.Assert.assertEquals(actualUrl,driver.getCurrentUrl());
        driver.quit();

    }
    @Test
    public void testLogin3(){

//        WebDriver driver = new ChromeDriver();
        driver.get("http://192.168.1.7:3000/");
        System.out.println("================= getting username password elements");
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        System.out.println("================= setting username password fields");

        username.sendKeys("nikhil");
        password.sendKeys("nikhil");

        WebElement loginButton = driver.findElement(By.xpath("//button[@id='loginBtn']"));
        System.out.println("================= logging by click");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        wait.until(ExpectedConditions.invisibilityOf(loginButton));

        String actualUrl="http://192.168.1.7:3000/calc";

        junit.framework.Assert.assertEquals(actualUrl,driver.getCurrentUrl());
        driver.quit();


    }



}
