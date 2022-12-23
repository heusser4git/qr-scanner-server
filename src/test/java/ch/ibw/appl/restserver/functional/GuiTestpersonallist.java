package ch.ibw.appl.restserver.functional;

import ch.ibw.appl.restserver.functional.shared.guitest.PersonalPo;
import ch.ibw.appl.restserver.functional.shared.guitest.SeleniumHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

public class GuiTestpersonallist {
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

    @AfterClass
    public static void tearDown(){
        if (driver != null) {
            SeleniumHelper.tearDownWebDriver(driver);
        }
    }
}
