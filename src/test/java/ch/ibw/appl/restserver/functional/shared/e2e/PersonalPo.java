package ch.ibw.appl.restserver.functional.shared.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PersonalPo {
    public static By adminButtonSelector(){
        return By.cssSelector("button[id='button-admin']");
    }
    public static By scannerButtonSelector(){
        return By.cssSelector("button[id='button-scanner']");
    }
    public static By headerTagSelector() {
        return By.tagName("header");
    }
    public static By h1TagSelector(){
        return By.tagName("h1");
    }
    public static By h3TagSelector() {return By.tagName("h3");}
    public static By aTagSelector() {
        return By.tagName("a");
    }
    public static By imgAdministrationSelector(){
        return By.cssSelector("img[src='img/administration.png']");
    }
    public static By imgScannerSelector(){
        return By.cssSelector("img[src='img/scanner.jpg']");
    }
    public static By tdTagSelector() {
        return By.tagName("td");
    }
    public static By searchInputSelector() {
        return By.cssSelector(".bx--search-input");
    }
    public static By pTagVornameSelector(){return By.cssSelector(".vorname");}
    public static By spanTagSelector(){return By.tagName("span");}
    public static By selectedPtagSelector(){return By.cssSelector(".bx--batch-summary__para");}
    public static By dataRowByNumberSelector(int rowNumber){return By.cssSelector("tr[data-row='"+rowNumber+"'");}
    public static By dataRowSelactableSpanSelector(){return By.cssSelector(".bx--radio-button__appearance");}

    public static By statusObjectByIdSelector(int id){
        return By.cssSelector("p[id='status"+id+"'");
    }
    
    public static By listTableSelector() {
        return By.cssSelector(".bx--data-table");
    }
    public static By addItemButtonSelector() {
        return By.cssSelector(".addButton");
    }
    public static By cancelItemButtonSelector(){
        return By.cssSelector(".bx--batch-summary__cancel");
    }
    public static By editItemButtonSelector(){return By.cssSelector(".editButton");}
    public static By deleteItemButtonSelector(){return By.cssSelector(".deleteButton");}
    public static By qrItemButtonSelector(){return By.cssSelector(".qrButton");}
    public static By addModalDivSelector(){
        return By.cssSelector("div[aria-label='Neuen Benutzer hinzufügen']");
    }
    public static By deleteModalDivSelector(){
        return By.cssSelector("div[aria-label='Benutzer Löschen']");
    }
    public static By qrModalDivSelector(){
        return By.cssSelector("div[aria-label='User-QR Code']");
    }
    public static By editModalDivSelector(){
        return By.cssSelector("div[aria-label='Bestehenden Benutzer bearbeiten']");
    }
    public static By notActiveRadioByEditSelector(){
        return By.xpath("div[2]/div/div/div[2]/div[3]/fieldset/div[2]/label");
    }
    public static By primaryButtonSelector(){
        return By.cssSelector(".bx--btn--primary");
    }
    public static By modalAddWindowSelector() {
        return By.cssSelector("div[class='bx--modal-container bx--modal-container--sm']");
    }
    public static By modalAddWindowHeaderSelector() {
        return By.cssSelector("h3[id='bx--modal-header__heading--modal-ccs-0.p614lnntvy']");
    }
    public static By modalAddWindowHeaderDivSelector() {
        return By.cssSelector("div[class='bx--modal-header']");

    }
    public static By modalAddWindowBirthdayInput() {
        return By.cssSelector("input#ccs-0.jdw02byeuba");
    }

    public static void navigateToAdministrationPageByButton(WebDriver driver){
        WebElement adminButton = driver.findElement(PersonalPo.adminButtonSelector());
        adminButton.click();
    }

    public static void navigateToAdministrationPageByPicture(WebDriver driver){
        WebElement adminPicture = driver.findElement(PersonalPo.imgAdministrationSelector());
        adminPicture.click();
    }

    public static void navigateToScannerPageByButton(WebDriver driver){
        WebElement scannerButton = driver.findElement(PersonalPo.scannerButtonSelector());
        scannerButton.click();
    }

    public static void navigateToScannerPageByPicture(WebDriver driver){
        WebElement scannerPicture = driver.findElement(PersonalPo.imgScannerSelector());
        scannerPicture.click();
    }

    public static void selectedObjectByRowNumber(WebDriver driver, int rowNumber){
        WebElement dataRow = driver.findElement(PersonalPo.dataRowByNumberSelector(rowNumber));
        WebElement dataRowSelectableSpan = dataRow.findElement(PersonalPo.dataRowSelactableSpanSelector());
        dataRowSelectableSpan.click();
    }

    public static void navigateToEditModal(WebDriver driver) throws InterruptedException {
        WebElement editButton = driver.findElement(PersonalPo.editItemButtonSelector());
//Thread.sleep(200);
        editButton.click();
    }
}