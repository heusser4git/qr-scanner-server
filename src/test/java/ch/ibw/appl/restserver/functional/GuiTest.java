package ch.ibw.appl.restserver.functional;

import ch.ibw.appl.restserver.functional.shared.guitest.PersonalPo;
import ch.ibw.appl.restserver.functional.shared.guitest.SeleniumHelper;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

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
/*
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
        Thread.sleep(200);
        WebElement title = driver.findElement(PersonalPo.addModalDivSelector()).findElement(PersonalPo.h3TagSelector());
        assertEquals("Neuen Benutzer hinzufügen", title.getAttribute("innerHTML"));
    }

    @Test
    public void testDeleteButtonAndItsModalWindow() throws InterruptedException {
        WebDriver driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToAdministrationPageByButton(driver);
        PersonalPo.selectedObjectByRowNumber(driver,2);
        WebElement deleteButton = driver.findElement(PersonalPo.deleteItemButtonSelector());
        Thread.sleep(200);
        deleteButton.click();
        WebElement title = driver.findElement(PersonalPo.deleteModalDivSelector()).findElement(PersonalPo.h3TagSelector());
        assertEquals("Benutzer Löschen", title.getAttribute("innerHTML"));
    }

    @Test
    public void testQrButtonAndItsModalWindow() throws InterruptedException {
        WebDriver driver = SeleniumHelper.setUpWebDriver();
        PersonalPo.navigateToAdministrationPageByButton(driver);
        PersonalPo.selectedObjectByRowNumber(driver,2);
        WebElement qrButton = driver.findElement(PersonalPo.qrItemButtonSelector());
        Thread.sleep(200);
        qrButton.click();
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
        Thread.sleep(200);
        cancelButton.click();
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
        Thread.sleep(200);
        notActiveRadio.click();
        WebElement updateButton = divEditModal.findElement(PersonalPo.primaryButtonSelector());
        updateButton.click();
        Thread.sleep(200);
        WebElement statusObejct2 = driver.findElement(PersonalPo.statusObjectByIdSelector(2));
        assertEquals("Nicht-Aktiv",statusObejct2.getText());
    }

    @Test
    public void testSearchOnList() throws InterruptedException {
        driver = SeleniumHelper.setUpWebDriver();
        WebElement adminButton = driver.findElement(PersonalPo.adminButtonSelector());
        adminButton.click();
        WebElement searchField = driver.findElement(PersonalPo.searchInputSelector());
        searchField.sendKeys("perko");
        Thread.sleep(2000);
        String tdFamiliyName = driver.findElement(By.xpath("/html/body/main/div[1]/table/tbody/tr/td[2]")).getAttribute("innerHTML");
        //System.out.println(tdFamiliyName.getLocation());
        //WebElement tdFamiliyName = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[1]/table/tbody/tr/td[2]"));
        //WebElement tdFirstname = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[1]/table/tbody/tr/td[3]"));
        //WebElement tdBirthday = driver.findElement(By.xpath("/html/body/main/div[1]/table/tbody/tr/td[4]"));
        //WebElement tdStatus = driver.findElement(By.xpath("/html/body/main/div[1]/table/tbody/tr/td[5]"));

        assertEquals("Perko ", tdFamiliyName);
        //assertEquals("Mitja", tdFirstname.getText());
        //assertEquals("Perko", tdFamiliyName.getText());
        //assertEquals("Perko", tdFamiliyName.getText());
        //assertEquals("Perko", tdFamiliyName.getText());
    }*/

//    @Test
//    public void testSearchOnList() {
//        WebDriver driver = SeleniumHelper.setUpWebDriver();
//        PersonalPo.navigateToAdministrationPageByButton(driver);
//        WebElement searchField = driver.findElement(PersonalPo.searchInputSelector());
//        searchField.sendKeys("perko");
//
//        WebElement listTable = driver.findElement(PersonalPo.listTableSelector());
//        List<WebElement> listOfTdElements = listTable.findElements(PersonalPo.tdTagSelector());
//        for(WebElement td: listOfTdElements) {
//            assertEquals("Perko", td.getText());
//        }
//    }

    @AfterClass
    public static void tearDown(){
        if (driver != null) {
            SeleniumHelper.tearDownWebDriver(driver);
        }
    }
}
