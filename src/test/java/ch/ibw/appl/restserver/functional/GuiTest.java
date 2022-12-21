package ch.ibw.appl.restserver.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

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
            driver.navigate().to("https://www.malans.ch");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(120));
    }

    @Test
    public void testOne() throws InterruptedException {
        WebElement header = driver.findElement(By.className("bx--header__name"));
        assertTrue(header.getText().contains("QR-Scanner"));

//        WebElement searchField = driver.findElement(By.className("icms-quicksearch-input"));
//        searchField.sendKeys("landquart");
//        searchField.submit();
//
//        WebElement resultCount = driver.findElement(By.className("icms-search-results-count"));
//        System.out.println(resultCount.getText());
//
//        assertTrue(resultCount.getText().contains("Ergebnisse"));
    }

    @AfterClass
    public static void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}
