package org.automationtesting.actions;

import com.microsoft.playwright.Page;
import org.automationtesting.locators.LoginLocators;

public class LoginActions {

    private Page page;

    public LoginActions(Page page) {
        this.page = page;
    }

    public void enterUsername(String username) {
        page.locator(LoginLocators.USERNAME_INPUT).fill(username);
    }

    public void enterPassword(String password) {
        page.locator(LoginLocators.PASSWORD_INPUT).fill(password);
    }

    public void clickLogin() {
        page.locator(LoginLocators.LOGIN_BUTTON).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}
