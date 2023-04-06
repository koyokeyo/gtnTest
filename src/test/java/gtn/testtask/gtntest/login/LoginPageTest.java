package gtn.testtask.gtntest.login;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;


@DisplayName("Тест: страница \"Логин\".")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginPageTest {
    LoginPage loginPage = new LoginPage();

    @Attachment(value = "{0}", type = "text/plain")
    public static String attach(String attachmentName, String attachmentText) {
        return attachmentText;
    }

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
    }

    @Test
    @Description("Неудачная попытка входа. Неправильные данные для входа")
    @Order(1)
    @DisplayName("Негативный кейс логина (Пользователь ввел некорректные данные).")
    public void failureLogin() {
        
        logStartTime();

        loginPage.enterCredentials(LoginConfiguration.INCORRECT_LOGIN, LoginConfiguration.INCORRECT_PASSWD);
        loginPage.clickSubmitButton();
        By xpathExpression = By.xpath("//div[@id='Password']//span[@id='helpBlock']");
        Duration duration = Duration.of(2, ChronoUnit.SECONDS);
        String errorMessageText = getTextMessageFromAppearingElement(xpathExpression, duration);

        logEndTime();

        Assertions.assertTrue(errorMessageText.contains("Неверный логин или пароль"),
                "Корректное отображение ошибки.");

    }
    @Step("Ожидание ответного сообщения.")
    private String getTextMessageFromAppearingElement(By xpathExpression, Duration duration) {
        WebDriverWait wait = new WebDriverWait(webdriver().object(), duration);
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(xpathExpression));
        return message.getText();
    }


    @Test
    @Description("Неудачная попытка входа. Не введены данные для входа.")
    @DisplayName("Негативный кейс логина (Пользователь забыл указать данные).")
    @Order(2)
    public void submitWithoutLogin() {
        logStartTime();

        loginPage.clickSubmitButton();
        By xpathExpression = By.xpath("//div[@id='Password']//span[@id='helpBlock']");
        Duration duration = Duration.of(2, ChronoUnit.SECONDS);
        String errorMessageText = getTextMessageFromAppearingElement(xpathExpression, duration);

        logEndTime();
        Assertions.assertTrue(errorMessageText.contains("Введите логин"),
                "Ошибка отображается корректно");

    }

    @Test
    @Description("Удачная попытка входа. Введены корректные данные для входа.")
    @DisplayName("Позитивный кейс логина (Пользователь ввел корректные данные).")
    @Order(3)
    public void successLogin() {
        logStartTime();
        loginPage.enterCredentials(LoginConfiguration.CORRECT_LOGIN, LoginConfiguration.CORRECT_PASSWD);
        loginPage.clickSubmitButton();
        Duration duration = Duration.of(10, ChronoUnit.SECONDS);
        By xpathExpression = By.xpath("//*[@id=\"navbar-top\"]/ul/li[2]/label");
        String message = getTextMessageFromAppearingElement(xpathExpression, duration);
        Assertions.assertTrue(!message.isEmpty(), "Корректный вход.");
        logEndTime();


    }

    private void logEndTime() {
        String endTime = "Окончание теста: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM HH:mm:ss"));
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
