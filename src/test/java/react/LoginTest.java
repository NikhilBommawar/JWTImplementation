package react;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginTest {

  @Test
  public void testLogin(){


    WebDriver driver = new ChromeDriver();
    driver.get("http://localhost:3000/");
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



    String actualUrl="http://localhost:3000/calc";

    Assert.assertEquals(actualUrl,driver.getCurrentUrl());


  }

  @Test
  public void testLogin2(){

    WebDriver driver = new ChromeDriver();
    driver.get("http://localhost:3000/");

    WebElement username = driver.findElement(By.name("username"));
    WebElement password = driver.findElement(By.name("password"));

    username.sendKeys("nikhil");
    password.sendKeys("nikhi");

    WebElement loginButton = driver.findElement(By.id("loginBtn"));


    loginButton.click();

    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));

    String actualUrl="http://localhost:3000/";

    Assert.assertEquals(actualUrl,driver.getCurrentUrl());

  }
}
