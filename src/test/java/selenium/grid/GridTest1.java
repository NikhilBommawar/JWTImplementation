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
            String nodeURL = "http://192.168.1.8:4444/";

            System.out.println("Chrome Browser Initiated");

            WebDriverManager.chromedriver().setup();

            DesiredCapabilities capabilities =  new DesiredCapabilities();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");

//            capabilities.setBrowserName(chromeOptions.getBrowserName());
            driver = new RemoteWebDriver(new URL(nodeURL),chromeOptions);

        }
//
//    @Test
//    public void googleSite() throws InterruptedException
//    {
//        String url = "https://www.google.com/";
//        String nodeURL = "http://192.168.1.8:4444/";
//
//        driver.get(url);
//        WebElement googleInput = driver.findElement(By.xpath("//textarea[@id='APjFqb']"));
//        googleInput.sendKeys("nikhil bommawar");
//        Assert.assertEquals(url,driver.getCurrentUrl());
//    }
//    @Test
//    public void appleSite() throws InterruptedException
//    {
//        String url = "https://www.apple.com/";
//        String nodeURL = "http://192.168.1.8:4444/";
//        driver.get(url);
//        Assert.assertEquals(url,driver.getCurrentUrl());
//    }
//
//    @Test
//    public void yahooSite() throws InterruptedException
//    {
//        String url = "https://www.yahoo.com/";
//        String nodeURL = "http://192.168.1.8:4444/";
//        driver.get(url);
//        Assert.assertEquals(url,driver.getCurrentUrl());
//    }

    @Test
    public void testLogin(){

        WebDriver driver = new ChromeDriver();

        driver.get("http://192.168.1.6:3000/");

        System.out.println("================= getting username password elements");
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));

        System.out.println("================= setting username password fields");
        username.sendKeys("nikhil");
        password.sendKeys("nikhil");

        WebElement loginButton = driver.findElement(By.id("loginBtn"));

        System.out.println("================= logging by click");

        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        wait.until(ExpectedConditions.invisibilityOf(loginButton));

        String actualUrl="http://192.168.1.8:3000/calc";

        junit.framework.Assert.assertEquals(actualUrl,driver.getCurrentUrl());

        driver.quit();


    }

    @Test
    public void testLogin2(){

        WebDriver driver = new ChromeDriver();
        driver.get("http://192.168.1.6:3000/");

        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));

        username.sendKeys("nikhil");
        password.sendKeys("nikhi");

        WebElement loginButton = driver.findElement(By.id("loginBtn"));

        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));

        String actualUrl="http://192.168.1.8:3000/";

        junit.framework.Assert.assertEquals(actualUrl,driver.getCurrentUrl());
        driver.quit();

    }
    @Test
    public void testLogin3(){

        WebDriver driver = new ChromeDriver();
        driver.get("http://192.168.1.8:3000/");
        System.out.println("================= getting username password elements");
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        System.out.println("================= setting username password fields");

        username.sendKeys("nikhil");
        password.sendKeys("nikhil");

        WebElement loginButton = driver.findElement(By.id("loginBtn"));
        System.out.println("================= logging by click");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        wait.until(ExpectedConditions.invisibilityOf(loginButton));

        String actualUrl="http://192.168.1.8:3000/calc";

        junit.framework.Assert.assertEquals(actualUrl,driver.getCurrentUrl());
        driver.quit();


    }

//    @Test
//    public void testLogin4(){
//
//        WebDriver driver = new ChromeDriver();
//        driver.get("http://192.168.1.8:3000/");
//
//        WebElement username = driver.findElement(By.name("username"));
//        WebElement password = driver.findElement(By.name("password"));
//
//        username.sendKeys("nikhil");
//        password.sendKeys("nikhi");
//
//        WebElement loginButton = driver.findElement(By.id("loginBtn"));
//
//        loginButton.click();
//
//        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
//
//        String actualUrl="http://192.168.1.8:3000/";
//
//        junit.framework.Assert.assertEquals(actualUrl,driver.getCurrentUrl());
//        driver.quit();
//
//    }
//    @Test
//    public void testLogin5(){
//
//
//        WebDriver driver = new ChromeDriver();
//        driver.get("http://192.168.1.8:3000/");
//        System.out.println("================= getting username password elements");
//        WebElement username = driver.findElement(By.name("username"));
//        WebElement password = driver.findElement(By.name("password"));
//        System.out.println("================= setting username password fields");
//
//        username.sendKeys("nikhil");
//        password.sendKeys("nikhil");
//        WebElement loginButton = driver.findElement(By.id("loginBtn"));
//
//        System.out.println("================= logging by click");
//
//        loginButton.click();
//
//        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
//        wait.until(ExpectedConditions.invisibilityOf(loginButton));
//
//        String actualUrl="http://192.168.1.8:3000/calc";
//
//        junit.framework.Assert.assertEquals(actualUrl,driver.getCurrentUrl());
//        driver.quit();
//
//
//    }
//
//    @Test
//    public void testLogin6(){
//
//        WebDriver driver = new ChromeDriver();
//        driver.get("http://192.168.1.8:3000/");
//
//        WebElement username = driver.findElement(By.name("username"));
//        WebElement password = driver.findElement(By.name("password"));
//
//        username.sendKeys("nikhil");
//        password.sendKeys("nikhi");
//
//        WebElement loginButton = driver.findElement(By.id("loginBtn"));
//
//
//        loginButton.click();
//
//        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
//
//        String actualUrl="http://192.168.1.8:3000/";
//
//        junit.framework.Assert.assertEquals(actualUrl,driver.getCurrentUrl());
//        driver.quit();
//
//    }

}
