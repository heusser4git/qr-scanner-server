import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.table.TableRowSorter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GuiTest {
    @Test
    public void testOne() throws InterruptedException {
        String path = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", path + "\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.malans.ch");

        WebElement searchMenu = driver.findElement(By.className("search"));
        searchMenu.click();

        WebElement searchField = driver.findElement(By.className("icms-quicksearch-input"));
        searchField.sendKeys("landquart");
        searchField.submit();

        WebElement resultCount = driver.findElement(By.className("icms-search-results-count"));
        System.out.println(resultCount.getText());

        assertTrue(resultCount.getText().contains("Ergebnisse"));

        Thread.sleep(5000);
        driver.quit();
    }
}
