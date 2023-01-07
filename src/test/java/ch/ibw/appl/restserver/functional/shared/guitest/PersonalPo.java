package ch.ibw.appl.restserver.functional.shared.guitest;

import org.openqa.selenium.By;

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
    public static By listTableSelector() {
        return By.cssSelector(".bx--data-table");
    }
    public static By addItemButtonSelector() {
        return By.cssSelector(".addButton");
    }
    public static By addModalTitleSelector(){ return By.xpath("/html/body/main/div[3]/div/div[1]/h3");}
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
}