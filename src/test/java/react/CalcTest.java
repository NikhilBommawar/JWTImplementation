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

public class CalcTest {

    @Test
    public void testCalc1() throws InterruptedException {

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

        // calculator test cases

        WebElement oneBtn = driver.findElement(By.id("num0"));
        WebElement twoBtn = driver.findElement(By.id("num1"));
        WebElement threeBtn = driver.findElement(By.id("num2"));

        WebElement additionBtn = driver.findElement(By.id("op0"));
        WebElement equalBtn = driver.findElement(By.xpath("//button[@id='op4']"));



        // case for 12+32
        oneBtn.click(); // 1 clicked
        twoBtn.click(); // 2 clicked
        additionBtn.click(); // + clicked
        threeBtn.click();  // 3 clicked
        twoBtn.click();  // 2 clicked


        equalBtn.click();  // = clicked
        WebElement display = driver.findElement(By.xpath("//input[@id='inputBox']"));

        String actualResult="44";

        Assert.assertEquals(actualResult,display.getAttribute("value"));

    }

    @Test
    public void testCalc2() throws InterruptedException {

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

        // calculator test cases

        WebElement oneBtn = driver.findElement(By.id("num0"));
        WebElement twoBtn = driver.findElement(By.id("num1"));
        WebElement threeBtn = driver.findElement(By.id("num2"));

        WebElement additionBtn = driver.findElement(By.id("op0"));
        WebElement equalBtn = driver.findElement(By.xpath("//button[@id='op4']"));



        // case for 12+32
        oneBtn.click(); // 1 clicked
        twoBtn.click(); // 2 clicked
        additionBtn.click(); // + clicked
        threeBtn.click();  // 3 clicked
        twoBtn.click();  // 2 clicked


        equalBtn.click();  // = clicked
        WebElement display = driver.findElement(By.xpath("//input[@id='inputBox']"));

        String actualResult="42";

        Assert.assertFalse(actualResult.equals(display.getAttribute("value")));

    }

}
