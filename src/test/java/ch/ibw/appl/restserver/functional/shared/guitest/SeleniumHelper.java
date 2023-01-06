package ch.ibw.appl.restserver.functional.shared.guitest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class SeleniumHelper {
    public static WebDriver setUpWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);
        if(System.getenv().containsKey("CLIENT_URL")) {
            driver.navigate().to(System.getenv("CLIENT_URL"));
        } else {
            driver.navigate().to("http://localhost:5000");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(120));
        return driver;
    }

    public static void tearDownWebDriver(WebDriver driver) {
        driver.quit();
    }
}
