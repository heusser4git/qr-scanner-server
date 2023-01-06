package ch.ibw.appl.restserver.functional;

import ch.ibw.appl.restserver.functional.shared.guitest.PersonalPo;
import ch.ibw.appl.restserver.functional.shared.guitest.SeleniumHelper;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GuiTest {
    private static WebDriver driver;


    @Test
    public void testHeaderText() {
        driver = SeleniumHelper.setUpWebDriver();
        WebElement header = driver.findElement(PersonalPo.headerTagSelector());
        header.findElement(PersonalPo.aTagSelector());
        assertEquals("QR-Scanner", header.getText());
    }

    @Test
    public void testNavigationToAdministrationByButton(){
        driver = SeleniumHelper.setUpWebDriver();
        WebElement adminButton = driver.findElement(PersonalPo.adminButtonSelector());
        adminButton.click();
        WebElement adminTitel = driver.findElement(By.tagName("h1"));

        assertEquals("Administration", adminTitel.getText());
    }

    @Test
    public void testNavigationToAdministrationByPicture(){
        driver = SeleniumHelper.setUpWebDriver();
        WebElement adminPicture = driver.findElement(By.cssSelector("img[src='img/administration.png']"));
        adminPicture.click();
        WebElement adminTitel = driver.findElement(By.tagName("h1"));

        assertEquals("Administration", adminTitel.getText());
    }

    @Test
    public void testNavigationToScannerByButton(){
        driver = SeleniumHelper.setUpWebDriver();
        WebElement scannerButton = driver.findElement(By.cssSelector("button[id='button-scanner']"));
        scannerButton.click();
        WebElement scannerTitel = driver.findElement(By.tagName("h1"));

        assertEquals("Scanner", scannerTitel.getText());
    }

    @Test
    public void testNavigationToScannerByPicture(){
        driver = SeleniumHelper.setUpWebDriver();
        WebElement scannerPicture = driver.findElement(By.cssSelector("img[src='img/scanner.jpg']"));
        scannerPicture.click();
        WebElement scannerTitel = driver.findElement(By.tagName("h1"));

        assertEquals("Scanner", scannerTitel.getText());
    }

    @Test
    public void testSearchOnList() {
        driver = SeleniumHelper.setUpWebDriver();
        WebElement adminButton = driver.findElement(By.cssSelector("button[id='button-admin']"));
        adminButton.click();
        WebElement searchField = driver.findElement(PersonalPo.searchInputSelector());
        searchField.sendKeys("perko");
        WebElement mainContent = driver.findElement(By.cssSelector("main[id='main-content']"));
        WebElement table = mainContent.findElement(By.tagName("table"));
        List<WebElement> tds = table.findElements(By.tagName("td"));
        String result = "";
        for (WebElement td : tds) {
            if(td.getText().contains("Perko")) {
                result = td.getText();
            }
        }

        assertEquals("Perko", result);
    }


    @Test
    public void testAddOnButtonAndItsModalWindow() throws InterruptedException {
        driver = SeleniumHelper.setUpWebDriver();
        WebElement adminButton = driver.findElement(By.cssSelector("button[id='button-admin']"));
        adminButton.click();
        WebElement addButton = driver.findElement(PersonalPo.addItemButtonSelector());
        addButton.click();
        Thread.sleep(200);
        WebElement title = driver.findElement(By.xpath("/html/body/main/div[3]/div/div[1]/h3"));
        assertEquals("Neuen Benutzer hinzufügen", title.getAttribute("innerHTML"));
    }
    @AfterClass
    public static void tearDown(){
        if (driver != null) {
            //SeleniumHelper.tearDownWebDriver(driver);
        }
    }
}
