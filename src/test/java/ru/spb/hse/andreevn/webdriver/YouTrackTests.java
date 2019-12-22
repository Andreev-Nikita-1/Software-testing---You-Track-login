package ru.spb.hse.andreevn.webdriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class YouTrackTests {

    private Users users;

    private void toAccept(String name, boolean del) {
        users.createUser(name, "0000");
        users.refresh();
        assertTrue(users.existUser(name));
        if (del) {
            users.deleteUser(name);
            users.refresh();
        }
    }

    private void toReject(String name, String expected) {
        UserWindow usersWindow = users.createUser(name, "0000");
        assertEquals(expected, users.getSeverityErrorString());
        usersWindow.cancel();
        users.refresh();
    }


    @BeforeEach
    public void rootLogin() {
        Login loginPage = YouTrackFramework.launch();
        loginPage.typeLogin("root");
        loginPage.typePassword("0000");
        users = loginPage.logIn();
    }

    @AfterEach
    public void rootLogout() {
        users.logOut().quit();
    }

    @Test
    public void testAlphabetic() {
        toAccept("ivanIvanov", true);
    }

    @Test
    public void testNumeric() {
        toAccept("12345", true);
    }

    @Test
    public void testAlphaNumeric() {
        toAccept("ivan1977", true);
    }

    @Test
    public void testUnderline() {
        toAccept("ivan_ivanov", true);
    }

    @Test
    public void testDash() {
        toAccept("ivan-ivanov", true);
    }

    @Test
    public void testStrangeSymbols() {
        toAccept("?!*&^%$#@", true);
    }

    @Test
    public void testCyrillitsa() {
        toAccept("ваня", true);
    }

    @Test
    public void testMixed() {
        toAccept("иванIvanov", true);
    }

    @Test
    public void testWhitespace() {
        toReject("ivan ivanov", "Restricted character ' ' in the name");
    }

    @Test
    public void testEmpty() {
        UserWindow userWindow = users.createUser("", "pwd");
        assertEquals("Login is required!", users.getBulbErrorString());
        userWindow.cancel();
    }

    @Test
    public void testSecondRegistration() {
        toAccept("ivan", false);
        toReject("ivan", "Value should be unique: login");
        users.deleteUser("ivan");
        users.refresh();
    }

    @Test
    public void testForbiddenSymbols() {
        toReject("<ivan/ivanov>", "login shouldn't contain characters \"<\", \"/\", \">\": login");
    }

    @Test
    public void testDots() {
        toReject("..", "Can't use \"..\", \".\" for login: login");
    }

    @Test
    public void testDotWithName() {
        toAccept("ivan.ivanov", true);
    }

}