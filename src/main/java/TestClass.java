import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TestClass {
    ChromeDriver chromeDriver;
    String input = "abc123.12.23!@#";
    String expect = "123.1223";
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
    public void lesson9_1(){
        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");
        sleep(1000);
        WebElement element = chromeDriver.findElement(By.id("txtInput2"));

        removeAttribute(element, "disabled");
        element.clear();
        element.sendKeys("abcxyz");
        setAttribute(element, "disabled");
    }

    @Test
    public void lesson9_2(){
        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");
        sleep(1000);
        WebElement element = chromeDriver.findElement(By.id("txtInput3"));
        sendText(element, input);
        sleep(100);
        String actual = getValue(element);
        Assert.assertEquals(actual, expect);
    }

    @Test
    public void lesson10(){
        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");
        sleep(1000);
        WebElement element = chromeDriver.findElement(By.id("exampleSelect1"));
        Select select = new Select(element);
        select.selectByVisibleText("Option 4");
        select.selectByValue("value3");
        select.selectByIndex(1);

        String valueByElement = getValue(element);
        System.out.println("get value by select element" + valueByElement);

        WebElement selectedOption = select.getFirstSelectedOption();
        String value = this.getValue(selectedOption);
        String text = this.getText(selectedOption);
        System.out.println("get value by option element "+ value + " --- " + text);
    }

    @Test
    public void lesson10_2(){
        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");
        sleep(1000);
        WebElement element = chromeDriver.findElement(By.id("defaultCheck1"));
        boolean ischecked = element.isSelected();
    }

    @Test
    public void lesson10_3(){
        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");
        sleep(1000);
        WebElement radioChecked = getCheckedElement("exampleRadios");
        System.out.println(getValue(radioChecked));


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
        ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].removeAttribute('" + attribute + "')", element);
    }

    private void setAttribute(WebElement element, String attribute){
        ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].setAttribute('" + attribute + "', '')", element);
    }

    private String getValue(WebElement element){
        return element.getAttribute("value");
    }

    private String getText(WebElement element){
        return element.getText();
    }

    private void sendText(WebElement element, String str){
        element.clear();
        sleep(100);
        element.sendKeys(input);
        sleep(100);
    }

    private void setCheckboxState(WebElement element, boolean expectCheck){
        boolean currentStatus = element.isSelected();
        if (expectCheck != currentStatus){
            element.click();
        }
    }

    private WebElement getCheckedElement(String groupName){
        List<WebElement> webElementList = chromeDriver.findElements(By.name(groupName));
        for (int i = 0; i < webElementList.size(); i++){
            if (webElementList.get(i).isSelected()){
                return webElementList.get(i);
            }
        }
        return null;
    }
}
