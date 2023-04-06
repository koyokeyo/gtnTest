package gtn.testtask.gtntest.login;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;


public class LoginPage {
    public SelenideElement loginInput = $x("//*[@id='inputLogin']");
    public SelenideElement passwordInput = $x("//*[@id='inputPassword']");
    public SelenideElement submitLoginButton = $x("//*[@id='loginButton']");

    @Step("Ввод данных для входа.")
    public void enterCredentials(String login, String password){
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
    }

    @Step("Нажатие кнопки \"Войти\".")
    public void clickSubmitButton(){
        submitLoginButton.click();
    }
}
