package ch.ibw.appl.restserver.functional;

import ch.ibw.appl.restserver.functional.shared.guitest.PersonalPo;
import ch.ibw.appl.restserver.functional.shared.guitest.SeleniumHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
        WebElement adminButton = driver.findElement(By.cssSelector("button[id='button-admin']"));
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
        List<WebElement> listOfTdElements = driver.findElements(PersonalPo.tdTagSelector());

        assertEquals("Perko", listOfTdElements.get(1).getText());
    }

//    @Test
//    public void testAddOnButtonAndItsModalWindow() throws InterruptedException {
//        WebElement addButton = driver.findElement(PersonalPo.addItemButtonSelector());
//        addButton.click();
//        Thread.sleep(200);
//        WebElement addWindow = driver.findElement(PersonalPo.modalAddWindowSelector());
//        System.out.println(addWindow.getText());
//        WebElement title = driver.findElement(PersonalPo.modalAddWindowGeburtstagInput());
//        System.out.println(title.getAttribute("placeholder"));
//
//        assertEquals("Neuen Benutzer hinzufügen", title.getText());
//    }

    @AfterClass
    public static void tearDown(){
        if (driver != null) {
            SeleniumHelper.tearDownWebDriver(driver);
        }
    }
}
