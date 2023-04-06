package gtn.testtask.gtntest.addPerson;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import gtn.testtask.gtntest.login.LoginConfiguration;
import gtn.testtask.gtntest.login.LoginPage;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;

@DisplayName("Тест: страница \"Добавить физическое лицо\".")
public class AddPersonPageTest {

    AddPersonPage addPersonPage = new AddPersonPage();
    LoginPage loginPage = new LoginPage();

    @BeforeAll
    public static void setUpAll() {

        Configuration.browserSize = "1280x800";
        Configuration.timeout = 15000;
        Configuration.driverManagerEnabled = true;
        webdriver().driver().clearCookies();
        SelenideLogger.addListener("allure", new AllureSelenide());

    }

    @Step("Конфигурация и открытие страницы.")
    @BeforeEach
    public void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        open("http://85.113.47.244:23480");
        loginPage.enterCredentials(LoginConfiguration.CORRECT_LOGIN, LoginConfiguration.CORRECT_PASSWD);
        loginPage.clickSubmitButton();

    }


    @Test
    @Description("Добавление физического лица через форму.")
    @DisplayName("Позитивный кейс добавления физического лица.")
    public void addPerson() {
        logStartTime();
        WebDriverWait wait = new WebDriverWait(webdriver().object(), Duration.of(10, ChronoUnit.SECONDS));
        Actions actions = new Actions(webdriver().object());
        addPersonPage.openAddPersonForm(wait, actions);
        addPersonPage.addPerson(wait, actions);
        Assertions.assertTrue(addPersonPage.findPersonByTaxNumber(), "Пользователь найден");
        logEndTime();
    }
    private void logEndTime() {
        String endTime = "Окончание теста: "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM HH:mm:ss"));
        allureStopStep(endTime);
    }

    private void logStartTime() {
        String startTime = "Начало теста: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM HH:mm:ss"));
        allureStartStep(startTime);
    }


    @Step("{0}")
    public void allureStartStep(String message) {}

    @Step("{0}")
    public void allureStopStep(String message) {}

    @AfterAll
    public static void tearDown() {
        Selenide.webdriver().driver().close();
    }
}
