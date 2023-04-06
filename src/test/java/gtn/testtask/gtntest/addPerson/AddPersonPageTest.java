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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;

@DisplayName("Тест: страница \"Добавить физическое лицо\".")
public class AddPersonPageTest {
    @Attachment(value = "{0}", type = "text/plain")
    public static String attach(String attachmentName, String attachmentText) {
        return attachmentText;
    }
    AddPersonPage addPersonPage = new AddPersonPage();
    LoginPage loginPage = new LoginPage();

    @BeforeAll
    public static void setUpAll() {

        Configuration.browserSize = "1280x800";
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
        WebDriverWait wait = new WebDriverWait(webdriver().object(), Duration.of(10, ChronoUnit.SECONDS));;
        addPersonPage.openAddPersonForm(wait);
        addPersonPage.addPerson(wait);
        Assertions.assertTrue(addPersonPage.findPersonByTaxNumber(), "Пользователь найден");
    }

    @Step("Время начала теста")
    private void saveStartDateAttachment(Date date) {
        attach("Время начала: " + date.toString(), "text/plain");
    }

    @Step("Время конца теста")
    private void saveEndDateAttachment(Date date) {
        attach("Время конца: " + date.toString(), "text/plain");
    }
    @AfterAll
    public static void tearDown() {
        Selenide.webdriver().driver().close();
    }
}
