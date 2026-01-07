package org.automationtesting.utils;

import com.microsoft.playwright.Page;

import java.nio.file.Paths;

public class ScreenshotUtils {

    public static String takeScreenshot(Page page, String testName) {

        String screenshotPath =
                "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(screenshotPath))
                .setFullPage(true));

        return screenshotPath;
    }
}
