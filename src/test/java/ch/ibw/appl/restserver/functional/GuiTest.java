package ch.ibw.appl.restserver.functional;

import ch.ibw.appl.restserver.functional.shared.guitest.PersonalPo;
import ch.ibw.appl.restserver.functional.shared.guitest.SeleniumHelper;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        WebElement adminTitle = driver.findElement(PersonalPo.h1TagSelector());
        assertEquals("Administration", adminTitle.getText());
    }

    @Test
    public void testNavigationToAdministrationByPicture(){
        driver = SeleniumHelper.setUpWebDriver();
        WebElement adminPicture = driver.findElement(PersonalPo.imgAdministrationSelector());
        adminPicture.click();
        WebElement adminTitle = driver.findElement(PersonalPo.h1TagSelector());
        assertEquals("Administration", adminTitle.getText());
    }

    @Test
    public void testNavigationToScannerByButton(){
        driver = SeleniumHelper.setUpWebDriver();
        WebElement scannerButton = driver.findElement(PersonalPo.scannerButtonSelector());
        scannerButton.click();
        WebElement scannerTitle = driver.findElement(PersonalPo.h1TagSelector());

        assertEquals("Scanner", scannerTitle.getText());
    }

    @Test
    public void testNavigationToScannerByPicture(){
        driver = SeleniumHelper.setUpWebDriver();
        WebElement scannerPicture = driver.findElement(PersonalPo.imgScannerSelector());
        scannerPicture.click();
        WebElement scannerTitle = driver.findElement(PersonalPo.h1TagSelector());
        assertEquals("Scanner", scannerTitle.getText());
    }

    @Test
    public void testSearchOnList() {
        driver = SeleniumHelper.setUpWebDriver();
        WebElement adminButton = driver.findElement(PersonalPo.adminButtonSelector());
        adminButton.click();
        WebElement searchField = driver.findElement(PersonalPo.searchInputSelector());
        searchField.sendKeys("perko");
        WebElement tdFamiliyName = driver.findElement(By.xpath("/html/body/main/div[1]/table/tbody/tr/td[2]"));
        //System.out.println(tdFamiliyName.getLocation());
        //WebElement tdFamiliyName = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[1]/table/tbody/tr/td[2]"));
        //WebElement tdFirstname = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[1]/table/tbody/tr/td[3]"));
        //WebElement tdBirthday = driver.findElement(By.xpath("/html/body/main/div[1]/table/tbody/tr/td[4]"));
        //WebElement tdStatus = driver.findElement(By.xpath("/html/body/main/div[1]/table/tbody/tr/td[5]"));

        assertEquals("Perko", tdFamiliyName.getText());
        //assertEquals("Mitja", tdFirstname.getText());
        //assertEquals("Perko", tdFamiliyName.getText());
        //assertEquals("Perko", tdFamiliyName.getText());
        //assertEquals("Perko", tdFamiliyName.getText());
    }

    @Test
    public void testAddOnButtonAndItsModalWindow() throws InterruptedException {
        driver = SeleniumHelper.setUpWebDriver();
        WebElement adminButton = driver.findElement(PersonalPo.adminButtonSelector());
        adminButton.click();
        WebElement addButton = driver.findElement(PersonalPo.addItemButtonSelector());
        addButton.click();
        Thread.sleep(200);
        WebElement title = driver.findElement(PersonalPo.addModalTitleSelector());
        assertEquals("Neuen Benutzer hinzufügen", title.getAttribute("innerHTML"));
    }
    @AfterClass
    public static void tearDown(){
        if (driver != null) {
            SeleniumHelper.tearDownWebDriver(driver);
        }
    }
}
