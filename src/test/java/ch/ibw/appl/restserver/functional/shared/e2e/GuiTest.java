package ch.ibw.appl.restserver.functional.shared.e2e;


import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class GuiTest {
    private static WebDriver driver;

    // Testing Starting Page
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
        PersonalPo.navigateToAdministrationPageByButton(driver);
        WebElement adminTitle = driver.findElement(PersonalPo.h1TagSelector());
        assertEquals("Administration", adminTitle.getText());
    }

    @Test
    public void testNavigationToAdministrationByPicture(){
        driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToAdministrationPageByPicture(driver);
        WebElement adminTitle = driver.findElement(PersonalPo.h1TagSelector());
        assertEquals("Administration", adminTitle.getText());
    }

    @Test
    public void testNavigationToScannerByButton(){
        driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToScannerPageByButton(driver);
        WebElement scannerTitle = driver.findElement(PersonalPo.h1TagSelector());
        assertEquals("Scanner", scannerTitle.getText());
    }

    @Test
    public void testNavigationToScannerByPicture(){
        driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToScannerPageByPicture(driver);
        WebElement scannerTitle = driver.findElement(PersonalPo.h1TagSelector());
        assertEquals("Scanner", scannerTitle.getText());
    }

    // Testing admin Page
    @Test
    public void testSearchOnList(){
        WebDriver driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToAdministrationPageByButton(driver);
        WebElement searchField = driver.findElement(PersonalPo.searchInputSelector());
        searchField.sendKeys("perko");
        WebElement p = driver.findElement(PersonalPo.pTagVornameSelector());
        assertEquals("Mitja", p.getText());
    }

    @Test
    public void testSelectItemOnList(){
        WebDriver driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToAdministrationPageByButton(driver);
        PersonalPo.selectedObjectByRowNumber(driver,2);
        WebElement resultPtag = driver.findElement(PersonalPo.selectedPtagSelector());
        WebElement span = resultPtag.findElement(PersonalPo.spanTagSelector());
        assertEquals("1 item selected", span.getText());
    }

    @Test
    public void testAddOnButtonAndItsModalWindow() throws InterruptedException {
        WebDriver driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToAdministrationPageByButton(driver);
        WebElement addButton = driver.findElement(PersonalPo.addItemButtonSelector());
        addButton.click();
        new WebDriverWait(driver, Duration.ofMillis(200))
                .until(ExpectedConditions.elementToBeSelected(driver.findElement(PersonalPo.addModalDivSelector()).findElement(PersonalPo.h3TagSelector())));
        WebElement title = driver.findElement(PersonalPo.addModalDivSelector()).findElement(PersonalPo.h3TagSelector());
        assertEquals("Neuen Benutzer hinzufügen", title.getAttribute("innerHTML"));
    }

    @Test
    public void testDeleteButtonAndItsModalWindow() throws InterruptedException {
        WebDriver driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToAdministrationPageByButton(driver);
        PersonalPo.selectedObjectByRowNumber(driver,2);
        WebElement deleteButton = driver.findElement(PersonalPo.deleteItemButtonSelector());
        new WebDriverWait(driver, Duration.ofMillis(200))
                .until(ExpectedConditions.elementToBeClickable(deleteButton));
        WebElement title = driver.findElement(PersonalPo.deleteModalDivSelector()).findElement(PersonalPo.h3TagSelector());
        assertEquals("Benutzer Löschen", title.getAttribute("innerHTML"));
    }

    @Test
    public void testQrButtonAndItsModalWindow() throws InterruptedException {
        WebDriver driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToAdministrationPageByButton(driver);
        PersonalPo.selectedObjectByRowNumber(driver,2);
        WebElement qrButton = driver.findElement(PersonalPo.qrItemButtonSelector());
        new WebDriverWait(driver, Duration.ofMillis(200))
                .until(ExpectedConditions.elementToBeClickable(qrButton));
        WebElement title = driver.findElement(PersonalPo.qrModalDivSelector()).findElement(PersonalPo.h3TagSelector());
        assertEquals("User-QR Code", title.getAttribute("innerHTML"));
    }

    @Test
    public void testEditButtonAndItsModalWindow() throws InterruptedException {
        WebDriver driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToAdministrationPageByButton(driver);
        PersonalPo.selectedObjectByRowNumber(driver,2);
        PersonalPo.navigateToEditModal(driver);
        WebElement title = driver.findElement(PersonalPo.editModalDivSelector()).findElement(PersonalPo.h3TagSelector());
        assertEquals("Bestehenden Benutzer bearbeiten", title.getAttribute("innerHTML"));
    }

    @Test
    public void testHeaderChangeAfterClickCancelBySelectedObject() throws InterruptedException {
        WebDriver driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToAdministrationPageByButton(driver);
        PersonalPo.selectedObjectByRowNumber(driver,2);
        WebElement cancelButton = driver.findElement(PersonalPo.cancelItemButtonSelector());
        new WebDriverWait(driver, Duration.ofMillis(200))
                .until(ExpectedConditions.elementToBeClickable(cancelButton));
        WebElement resultPtag = driver.findElement(PersonalPo.selectedPtagSelector());
        WebElement span = resultPtag.findElement(PersonalPo.spanTagSelector());
        assertEquals("0 items selected", span.getText());
    }

    @Test
   public void testChangeStatusOnObjectByEdit() throws InterruptedException {
        WebDriver driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToAdministrationPageByButton(driver);
        PersonalPo.selectedObjectByRowNumber(driver,2);
        PersonalPo.navigateToEditModal(driver);
        WebElement divEditModal = driver.findElement(PersonalPo.editModalDivSelector());
        WebElement notActiveRadio = divEditModal.findElement(PersonalPo.notActiveRadioByEditSelector());
        new WebDriverWait(driver, Duration.ofMillis(200))
                .until(ExpectedConditions.elementToBeClickable(notActiveRadio));
        WebElement updateButton = divEditModal.findElement(PersonalPo.primaryButtonSelector());
        updateButton.click();
        new WebDriverWait(driver, Duration.ofMillis(200))
                .until(ExpectedConditions.elementToBeSelected(driver.findElement(PersonalPo.statusObjectByIdSelector(2))));
        WebElement statusObejct2 = driver.findElement(PersonalPo.statusObjectByIdSelector(2));
        assertEquals("Nicht-Aktiv",statusObejct2.getText());
    }


    @AfterClass
    public static void tearDown(){
        if (driver != null) {
            SeleniumHelper.tearDownWebDriver(driver);
        }
    }
}
