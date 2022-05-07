import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TestClass {
    ChromeDriver chromeDriver;
    @BeforeMethod
    public void setup(){
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
    }

    @Test
    public void run(){
        chromeDriver.get("https://www.google.com.vn/?hl=vi");
        sleep(5000);
        chromeDriver.navigate().refresh();
        chromeDriver.navigate().back();
        chromeDriver.navigate().forward();

        chromeDriver.executeScript("", new Object()); //thực thi javascript

        chromeDriver.getMouse(); //get con trỏ chuột

        chromeDriver.getPageSource(); //get source code html

        chromeDriver.getTitle(); //get ra tiêu đề của trang

        chromeDriver.switchTo().alert();//di chuyển đến 1 phần tử trên trang web, ví dụ như
        //alert, frame, new windows
    }

    @Test
    public void lesson7(){
        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");

        WebElement button1 = chromeDriver.findElement(By.id("btnExample1"));
        button1.click();
        sleep(3000);
        List<WebElement> btnList = chromeDriver.findElements(By.className("btn-success"));
        btnList.forEach(btn -> {
            btn.click();
            sleep(2000);
        });
    }

    @Test
    public void lesson8_1(){
        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");
        sleep(3000);
        //web element only support some method, u must use Actions object of element to use other action
        WebElement button1 = chromeDriver.findElement(By.id("btnExample1"));

        button1.sendKeys( "test");
        button1.click();
        //del value in text box
        button1.clear();

        button1.submit();

        Actions actions = new Actions(chromeDriver);

        //move to element
        actions.moveToElement(button1);

        //left click
        actions.click(button1).build().perform();

        //right click
        actions.contextClick(button1).build().perform();

        //double click
        actions.doubleClick(button1).build().perform();

        //drag drop
        actions.dragAndDrop(button1, button1).build().perform();

        //************* all element can send key ****************
        actions.sendKeys(button1, "abc").build().perform();

    }

    @Test
    public void lesson8_2(){
        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");
        sleep(3000);
        //web element only support some method, u must use Actions object of element to use other action
        WebElement button1 = chromeDriver.findElement(By.id("btnExample1"));
        button1.click();
        WebElement lbStatusButton = chromeDriver.findElement(By.id("lbStatusButton"));
        String lbStatusButtonValue = lbStatusButton.getText();
        Assert.assertEquals(lbStatusButtonValue, "Click on Button 1");

        WebElement input1 = chromeDriver.findElement(By.id("txtInput1"));
        String inputValue = input1.getAttribute("value");
        Assert.assertEquals(inputValue, "Default value of input");

        String newValue = "New value of input";

        input1.clear();
        input1.sendKeys(newValue);
        String newInputValue = input1.getAttribute("value");
        Assert.assertEquals(newInputValue, newValue);
    }

    @Test
    public void lesson9(){
        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");
        sleep(3000);
        WebElement element = chromeDriver.findElement(By.id("txtInput2"));

    }

    @AfterMethod
    public void cleanUp(){
        System.out.println("After");
//        chromeDriver.quit();
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeAttribute(WebElement element, String attribute){
        ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].removeAtrribute('" + attribute + "')", element);
    }

}
