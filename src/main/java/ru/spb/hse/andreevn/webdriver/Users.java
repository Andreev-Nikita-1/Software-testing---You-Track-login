package ru.spb.hse.andreevn.webdriver;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Users {
    private static final String USER_RIGHT_WINDOW = "//*[@id=\"id_l.HeaderNew.header\"]/div[1]/div/div/a[1]/span";
    private static final String CREATE_USER_BUTTON = "//*[@id=\"id_l.U.createNewUser\"]";
    private static final String CREATE_USER_LINK = "Create user";
    private static final String USERS_TABLE = "//*[@id=\"id_l.U.usersList.usersList\"]/table/tbody";
    private static final String USERS_TABLE_NAMES = ".//td[1]/a";
    private static final String USERS_TABLE_DELETE = ".//../../td[6]/a[1]";
    private static final String BULB_ERROR_CLASSNAME = "error-bulb2";
    private static final String BULB_ERROR_TEXT = "/html/body/div[3]";
    private static final String SEVERITY_ERROR_CLASSNAME = "errorSeverity";
    private static final String EDIT_USER_URL = "editUser";
    private static final String USERS_URL = "http://localhost:8080/users";
    private static final String ERROR_SELECTOR = "body > div.ring-dropdown > div > a.ring-dropdown__item.yt-header__login-link.ring-link";

    private WebElement usersTable;
    private WebDriver driver;
    private WebDriverWait wait;

    public Users(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 100);
        wait.until(and(
                visibilityOfElementLocated(By.xpath(USER_RIGHT_WINDOW)),
                visibilityOfElementLocated(By.xpath(CREATE_USER_BUTTON)),
                visibilityOfElementLocated(By.xpath(USERS_TABLE))
        ));
        usersTable = driver.findElement(By.xpath(USERS_TABLE));
    }


    public UserWindow createUser(String login, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(visibilityOfElementLocated(
                By.linkText(CREATE_USER_LINK)))
                .click();
        UserWindow userWindow = new UserWindow(driver);
        userWindow.typeLogin(login);
        userWindow.typePassword(password);
        userWindow.confirm();
        wait.until(or(
                visibilityOfElementLocated(By.className(BULB_ERROR_CLASSNAME)),
                visibilityOfElementLocated(By.className(SEVERITY_ERROR_CLASSNAME)),
                urlContains(EDIT_USER_URL)
        ));
        return userWindow;
    }

    public Login logOut() {
        driver.findElement(By.xpath(USER_RIGHT_WINDOW)).click();
        wait.until(visibilityOfElementLocated(By.cssSelector(ERROR_SELECTOR)));
        driver.findElement(By.cssSelector(ERROR_SELECTOR)).click();
        return new Login(driver);
    }

    public void refresh() {
        driver.get(USERS_URL);
        wait.until(and(
                visibilityOfElementLocated(By.xpath(USER_RIGHT_WINDOW)),
                visibilityOfElementLocated(By.xpath(CREATE_USER_BUTTON)),
                visibilityOfElementLocated(By.xpath(USERS_TABLE))
        ));
        usersTable = driver.findElement(By.xpath(USERS_TABLE));
    }


    public boolean existUser(String login) {
        return usersTable.findElements(By.xpath(USERS_TABLE_NAMES)).stream()
                .map(WebElement::getText)
                .collect(Collectors.toSet())
                .contains(login);
    }

    public void deleteUser(String login) {
        List<WebElement> users = usersTable.findElements(By.xpath(USERS_TABLE_NAMES));
        for (WebElement user : users) {
            if (login.equals(user.getText())) {
                user.findElement(By.xpath(USERS_TABLE_DELETE)).click();
                wait.until(alertIsPresent());
                driver.switchTo().alert().accept();
            }
        }
    }

    public String getSeverityErrorString() {
        return driver.findElement(By.className(SEVERITY_ERROR_CLASSNAME)).getText();
    }

    public String getBulbErrorString() {
        driver.findElement(By.className(BULB_ERROR_CLASSNAME)).click();
        wait.until(visibilityOfElementLocated(By.className(BULB_ERROR_CLASSNAME)));
        return driver.findElement(By.xpath(BULB_ERROR_TEXT)).getText();
    }

}