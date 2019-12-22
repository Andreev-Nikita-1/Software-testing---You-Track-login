package ru.spb.hse.andreevn.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserWindow {

    private static final String CREATE_USER_BUTTON = "id_l.U.cr.createUserDialog";
    private static final String LOGIN_FIELD = "id_l.U.cr.login";
    private static final String PASSWORD_FIELD = "id_l.U.cr.password";
    private static final String PASSWORD_CONFIRM = "id_l.U.cr.confirmPassword";
    private static final String OK_BUTTON = "id_l.U.cr.createUserOk";
    private static final String CANCEL_BUTTON = "id_l.U.cr.createUserCancel";
    private final WebDriver driver;

    public UserWindow(WebDriver driver) {
        this.driver = driver;
        new WebDriverWait(driver, 100).
                until(ExpectedConditions.visibilityOfElementLocated(
                        By.id(CREATE_USER_BUTTON)));
    }

    public void typeLogin(String login) {
        driver.findElement(By.id(LOGIN_FIELD)).sendKeys(login);
    }

    public void typePassword(String password) {
        driver.findElement(By.id(PASSWORD_FIELD)).sendKeys(password);
        driver.findElement(By.id(PASSWORD_CONFIRM)).sendKeys(password);
    }

    public void cancel() {
        driver.findElement(By.id(CANCEL_BUTTON)).click();
    }

    public void confirm() {
        driver.findElement(By.id(OK_BUTTON)).click();
    }
}