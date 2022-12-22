package ch.ibw.appl.restserver.functional;

import ch.ibw.appl.restserver.functional.shared.guitest.PersonalPo;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GuiTest {
    private static WebDriver driver;
    @BeforeClass
    public static void setUp()
    {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        if(System.getenv().containsKey("CLIENT_URL")) {
//            System.out.println(System.getenv("CLIENT_URL"));
            driver.navigate().to(System.getenv("CLIENT_URL"));
        } else {
            driver.navigate().to("http://localhost:5000");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(120));
    }

    @Test
    public void testHeaderText() {
        WebElement header = driver.findElement(PersonalPo.headerTagSelector());
        header.findElement(PersonalPo.aTagSelector());
        assertEquals("QR-Scanner", header.getText());
    }

    @Test
    public void testSearchOnList() {
        WebElement searchField = driver.findElement(PersonalPo.searchInputSelector());
        searchField.sendKeys("perko");

        WebElement listTable = driver.findElement(PersonalPo.listTableSelector());
        List<WebElement> listOfTdElements = listTable.findElements(PersonalPo.tdTagSelector());

        assertEquals("Perko", listOfTdElements.get(1).getText());
        assertEquals("Mitja", listOfTdElements.get(2).getText());
        assertEquals("16.5.1993", listOfTdElements.get(3).getText());
        assertEquals("Aktiv", listOfTdElements.get(4).getText());
    }

    @Test
    public void testAddOnButtonAndItsModalWindow() throws InterruptedException {
//        WebElement addButton = driver.findElement(PersonalPo.addItemButtonSelector());
//        addButton.click();
//        Thread.sleep(200);
//        WebElement addWindow = driver.findElement(PersonalPo.modalAddWindowSelector());
//        System.out.println(addWindow.getText());
//        WebElement title = driver.findElement(PersonalPo.modalAddWindowGeburtstagInput());
//        System.out.println(title.getAttribute("placeholder"));
//
//        assertEquals("Neuen Benutzer hinzufügen", title.getText());
    }

    @AfterClass
    public static void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}
