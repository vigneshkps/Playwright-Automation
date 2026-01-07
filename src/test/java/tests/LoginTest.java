package tests;

import com.microsoft.playwright.options.LoadState;
import org.automationtesting.actions.LoginActions;
import org.automationtesting.base.BaseTest;
import org.automationtesting.locators.LoginLocators;
import org.automationtesting.utils.ExcelUtils;
import org.automationtesting.utils.ScreenshotUtils;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {

        int totalRows = ExcelUtils.getRowCount("InputData");

        for (int rowNum = 1; rowNum <= totalRows; rowNum++) {

            String testName =
                    ExcelUtils.getCellDataByColumnName("InputData", rowNum, "TestName");

            try {
               // 1️⃣ Browser Setup

                launchBrowser();

                String url =
                        ExcelUtils.getCellDataByColumnName("InputData", rowNum, "URL");
                String username =
                        ExcelUtils.getCellDataByColumnName("InputData", rowNum, "Username");
                String password =
                        ExcelUtils.getCellDataByColumnName("InputData", rowNum, "Password");

                // ===============================
                // 2️⃣ API Validation Flag
                // ===============================
                AtomicBoolean loginApiSuccess = new AtomicBoolean(false);

                page.onResponse(response -> {
                    if (response.url().contains("/me")
                            && response.status() == 200) {
                        loginApiSuccess.set(true);
                    }
                });

                // ===============================
                // 3️⃣ Login Action
                // ===============================
                page.navigate(url);
                LoginActions loginActions = new LoginActions(page);
                loginActions.login(username, password);

                page.waitForLoadState(LoadState.NETWORKIDLE);

                // ===============================
                // 4️⃣ UI Validation (Locator Class)
                // ===============================
                boolean isOverviewVisible =
                        page.locator(LoginLocators.OVERVIEW_TAB).isVisible();

               // 5️⃣ Final Decision (API + UI)

                boolean isLoginSuccess =
                        loginApiSuccess.get() && isOverviewVisible;

                if (isLoginSuccess) {
                    ExcelUtils.setCellDataByColumnName(
                            "InputData", rowNum, "Result", "PASS");
                } else {
                    String screenshotPath =
                            ScreenshotUtils.takeScreenshot(page, testName);

                    ExcelUtils.setCellDataByColumnName(
                            "InputData", rowNum, "Result", "FAIL");

                    ExcelUtils.setCellDataByColumnName(
                            "InputData", rowNum, "Screenshot", screenshotPath);
                }

            } catch (Exception e) {

                if (page != null) {
                    String screenshotPath =
                            ScreenshotUtils.takeScreenshot(page, testName);

                    ExcelUtils.setCellDataByColumnName(
                            "InputData", rowNum, "Screenshot", screenshotPath);
                }

                ExcelUtils.setCellDataByColumnName(
                        "InputData", rowNum, "Result", "FAIL");

            } finally {
                closeBrowser();
            }
        }
    }
}
