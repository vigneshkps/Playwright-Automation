package tests;

import org.automationtesting.base.BaseTest;
import org.testng.annotations.Test;

public class InitiateTest extends BaseTest {

    @Test
    public void launchBrowserTest() {

        launchBrowser();

        page.navigate("https://devlab-as.aptimeta.com/");

        closeBrowser();
    }
}
