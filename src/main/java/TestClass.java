import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.time.Duration;
import java.util.List;

public class TestClass {
    ChromeDriver chromeDriver;
    String input = "abc123.12.23!@#";
    String expect = "123.1223";
    @BeforeMethod
    public void setup(){
        //C:\Users\Admin\Desktop\chromedriver_win32
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Desktop\\chromedriver_win32\\chromedriver.exe");

//        ChromeOptions options = new ChromeOptions();
        ChromeDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
    }

    @Test
    public void case1() throws AWTException {

        chromeDriver.manage().window().maximize();

        chromeDriver.get("https://app.asana.com/-/login");

        chromeDriver.findElement(By.xpath("//*[@id=\"Login-appRoot\"]/div/div[1]/div[2]/form/div[1]/div/div/input")).sendKeys("haudtquicktest@gmail.com");
        sleep(300);

        chromeDriver.findElement(By.xpath("//*[@id=\"Login-appRoot\"]/div/div[1]/div[2]/form/div[2]")).click();
        sleep(300);
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("LoginPasswordForm-passwordInput"))).sendKeys("123456789a@");

        sleep(300);
        chromeDriver.findElement(By.xpath("//*[@id=\"Login-appRoot\"]/div/div[1]/form/div[2]")).click();
        sleep(10000);
        chromeDriver.findElement(By.className("TopbarPageHeaderGlobalActions-omnibuttonWithoutQuestionMenu")).click();
        wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Omnibutton-task")));
        chromeDriver.findElement(By.id("Omnibutton-task")).click();
        sleep(300);

        WebElement element = chromeDriver.switchTo().activeElement();
        element.sendKeys("Demo");
        sleep(1000);
        element = chromeDriver.findElement(By.xpath("//*[@id=\"asana_main_page\"]/div[2]/div/div/div[2]/div/div[1]/div[1]/div/div/div[3]/div[2]"));
        element.sendKeys("tieu dua hau test description");
        sleep(2000);
        element = chromeDriver.findElement(By.xpath("//*[@id=\"asana_main_page\"]/div[2]/div/div/div[2]/div/div[1]/div[1]/div/div/div[2]/div[2]"));
        sleep(2000);
        element.click();//open dropdown list
        sleep(2000);
        //select Hau Test Project
        List <WebElement> elements = chromeDriver.findElements(By.className("TypeaheadItemStructure-label"));
        for (int i = 0; i < elements.size(); i++){
            if (elements.get(i).getText().equals("Sample Project")){
                sleep(2000);
                new Actions(chromeDriver).moveToElement(wait.until(ExpectedConditions.elementToBeClickable(elements.get(i)))).click().build().perform();

                break;
            }
        }
        sleep(5000);
        //Priority
        chromeDriver.findElement(By.className("CustomPropertyEnumValueInput")).click();
        elements = chromeDriver.findElements(By.className("MenuItem-label"));
        for (int i = 0; i < elements.size(); i++){
            if (elements.get(i).getText().equals("Medium")){
                sleep(1000);
                new Actions(chromeDriver).moveToElement(wait.until(ExpectedConditions.elementToBeClickable(elements.get(i)))).click().build().perform();
            break;
            }
        }

        //create task
        sleep(200);
        chromeDriver.findElement(By.className("QuickAddTaskToolbar-createButton")).click();
        sleep(2000);

        //case 2
        case2();
        //case 3
        case3();

    }

    void case2() throws AWTException {
        chromeDriver.findElement(By.xpath("//*[@id=\"asana_sidebar\"]/div/nav/a[2]")).click();
        sleep(2000);

        WebElement elementForDrag = chromeDriver.findElement(By.xpath("//*[@id=\"asana_main_page\"]/div[2]/div/div/div/div/div[1]/div[1]/div[3]/div/div/div/div/div/div[1]/div/div/div[2]/div[2]/div/div/div[2]"));
        sleep(1000);
        removeAttribute(elementForDrag, "draggable");
        enableElement(elementForDrag, "draggable");
        WebElement targetElement = chromeDriver.findElement(By.xpath("//*[@id=\"asana_main_page\"]/div[2]/div/div/div/div/div[1]/div[1]/div[3]/div/div/div/div/div/div[3]/div/div/div[2]"));

        removeAttribute(targetElement, "droppable");
        enableElement(targetElement, "droppable");

        System.out.println(elementForDrag.getText());
        System.out.println(targetElement.getText());
        Point coordinates = elementForDrag.getLocation();
        Point coordinatesa = targetElement.getLocation();
        Robot robot = new Robot();
        robot.mouseMove(coordinates.getX(), coordinates.getY() + 120);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseMove(coordinatesa.getX() + 100, coordinatesa.getY() + 130);
        sleep(500);
        robot.mouseMove(coordinatesa.getX() + 80, coordinatesa.getY() + 130);
        robot.delay(2000);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
    void case3(){
        chromeDriver.findElement(By.xpath("//*[@id=\"asana_sidebar\"]/div/nav/a[2]")).click();
        sleep(5000);
        WebElement element = chromeDriver.findElement(By.xpath("//*[@id=\"asana_main_page\"]/div[2]/div/div/div/div/div[1]/div[1]/div[3]/div/div/div/div/div/div[1]/div/div/div[2]/div[2]/div/div/div[1]"));
        element.click();
        sleep(5000);

        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(2));
        WebElement createBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("SubtaskIcon")));
        sleep(300);
        createBtn.click();
        element = chromeDriver.switchTo().activeElement();
        element.sendKeys("This is sub task");
        sleep(1000);
        //click on comment icon
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("CommentLineIcon"))).click();
        sleep(1000);
        //click on text comment area
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("SingleTaskPaneSpreadsheet-commentComposer")));
        element.click();

        element = chromeDriver.switchTo().activeElement();
        element.sendKeys("This is comment");

        //Comment button
        element = chromeDriver.findElement(By.className("CommentComposerEditor-submitButton"));
        element.click();

        sleep(1000);
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"asana_main_page\"]/div[2]/div/div/div/div/div[2]/article/div[2]/div[1]/div/div/div[6]/div/div[3]/div/div[1]/div[2]/div[1]/div[2]/div")));

        element.click();
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

    private void enableElement(WebElement element, String attribute){
        ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].setAttribute('" + attribute + "', 'true')", element);
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
