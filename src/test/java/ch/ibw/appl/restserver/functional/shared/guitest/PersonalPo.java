package ch.ibw.appl.restserver.functional.shared.guitest;

import org.openqa.selenium.By;

public class PersonalPo {
    public static By headerTagSelector() {
        return By.tagName("header");
    }
    public static By aTagSelector() {
        return By.tagName("a");
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
    public static By modalAddWindowSelector() {
        return By.cssSelector(".bx--modal-container");
    }
    public static By modalAddWindowHeaderSelector() {
        return By.cssSelector("h3.bx--modal-header__heading");
    }
    public static By modalAddWindowGeburtstagInput() {
        return By.cssSelector("input#ccs-0.jdw02byeuba");
    }
}