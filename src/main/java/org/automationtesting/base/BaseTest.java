package org.automationtesting.base;

import com.microsoft.playwright.*;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    /**
     * Launch browser and create page
     */
    protected void launchBrowser() {
        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(java.util.List.of("--start-maximized"))
        );

        // Maximize browser window
        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(null)
        );

        page = context.newPage();
    }

    /**
     * Close browser and cleanup resources
     */
    protected void closeBrowser() {

        if (context != null) {
            context.close();
        }

        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}
