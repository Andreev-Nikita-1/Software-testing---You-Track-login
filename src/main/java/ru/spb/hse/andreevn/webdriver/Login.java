package ru.spb.hse.andreevn.webdriver;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.and;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Login {

    private static final String PASSWORD_FIELD_ID = "//*[@id=\"id_l.L.password\"]";
    private static final String LOGIN_FIELD_ID = "//*[@id=\"id_l.L.login\"]";
    private static final String LOGIN_BUTTON_ID = "//*[@id=\"id_l.L.loginButton\"]";
    private WebDriver driver;

    public Login(WebDriver driver) {
        this.driver = driver;
        new WebDriverWait(driver, 100).until(and(
                visibilityOfElementLocated(By.xpath(LOGIN_FIELD_ID)),
                visibilityOfElementLocated(By.xpath(PASSWORD_FIELD_ID)),
                visibilityOfElementLocated(By.xpath(LOGIN_BUTTON_ID))));
    }

    public void typeLogin(String login) {
        driver.findElement(By.xpath(LOGIN_FIELD_ID)).sendKeys(login);
    }

    public void typePassword(String password) {
        driver.findElement(By.xpath(PASSWORD_FIELD_ID)).sendKeys(password);
    }

    public Users logIn() {
        driver.findElement(By.xpath(LOGIN_BUTTON_ID)).click();
        return new Users(driver);
    }

    public void quit() {
        driver.quit();
    }
}