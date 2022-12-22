package ch.ibw.appl.restserver.functional;

import ch.ibw.appl.restserver.functional.shared.guitest.PersonalPo;
import ch.ibw.appl.restserver.functional.shared.guitest.SeleniumHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

public class GuiTest {
    private static WebDriver driver;
    @BeforeClass
    public static void setUp()
    {
        driver = SeleniumHelper.setUpWebDriver();
    }

    @Test
    public void testHeaderText() {
        WebElement header = driver.findElement(PersonalPo.headerTagSelector());
        header.findElement(PersonalPo.aTagSelector());
        assertEquals("QR-Scanner", header.getText());
    }

//    @Test
//    public void testSearchOnList() {
//        WebElement searchField = driver.findElement(PersonalPo.searchInputSelector());
//        searchField.sendKeys("perko");
//
//        WebElement listTable = driver.findElement(PersonalPo.listTableSelector());
//        List<WebElement> listOfTdElements = listTable.findElements(PersonalPo.tdTagSelector());
//
//        assertEquals("Perko", listOfTdElements.get(1).getText());
//        assertEquals("Mitja", listOfTdElements.get(2).getText());
//        assertEquals("16.5.1993", listOfTdElements.get(3).getText());
//        assertEquals("Aktiv", listOfTdElements.get(4).getText());
//    }

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
