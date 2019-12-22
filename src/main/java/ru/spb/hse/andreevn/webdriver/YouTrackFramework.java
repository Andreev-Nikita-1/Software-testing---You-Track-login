package ru.spb.hse.andreevn.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class YouTrackFramework {

    public static Login launch() {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Nikita\\Desktop\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("localhost:8080/users");
        return new Login(driver);
    }
}
