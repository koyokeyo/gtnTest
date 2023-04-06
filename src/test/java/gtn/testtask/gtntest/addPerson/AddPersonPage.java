package gtn.testtask.gtntest.addPerson;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class AddPersonPage {
    private By operationFromLeftNavBar = By.xpath("//a[contains(@class, 'operation')]");
    private By addPersonButton = By.xpath("//a[contains(@class, 'add')]");
    private By popupAddPerson  =By.xpath("//a[@title='Физ. лицо']");

    private SelenideElement lastNameFormInput = $x("//input[@class='form-control" +
       " mandatory-field-color-useroption169 ng-empty'][1]");
    private SelenideElement firstNameFormInput = $x("//input[@class='form-control" +
      " mandatory-field-color-useroption169 ng-empty'][1]");
    private SelenideElement taxNumberInput = $x("//input[@ng-model='person.Inn']");
    private SelenideElement sexInputUl = $x("//div[@ng-click='selectClick()'][.//option[@label='М']]");
    private SelenideElement sexSelect = $x("//select[.//option[@label='М']]");
    private SelenideElement birthDateInput = $x("//input[@ng-blur='checkPerson()']");
    private SelenideElement citizenshipDropDown = $x("//select[.//option[@value='string:88']]");
    private SelenideElement citizenshipDiv = $x("//div[contains(@class, 'input-group')]" +
       "[.//option[@value='string:88']]");
    private SelenideElement birthAddressButton = $x(
       "/html/body/div[1]/div/div/div[2]/div/div/div[1]" +
               "/div[1]/div/div[6]/div[2]/address-control/div/div/button[1]");

    private SelenideElement birthAddressTextArea = $x("//textarea[@ng-model='address']");
    private SelenideElement birthAddressTextAreaSubmitButton = $x("//button[@class='btn btn-green']");
    private SelenideElement livingAddressButton = $x("/html/body/div[1]/div/div/div[2]/div/div/" +
            "div[1]/div[1]/div/div[7]/div[2]/address-control/div/div/button[1]");
    private SelenideElement livingAddressSubmitFormButton = $("#BodyWrapper > div.modal.fade." +
            "ng-isolate-scope.show > div > div > div.modal-footer.ng-scope" +
            " > div > ok-cancel-buttons > button.btn.btn-green.ng-binding");
    private SelenideElement newIdButton = $x("//button[@ng-click='newNumber()']");
    private SelenideElement submitAllButton = $x("//button[@ng-click='ok()']");
    private SelenideElement yesSubmitButton = $x("//button[@ng-click='yes()']");

    private SelenideElement taxNumberSearchInput = $x("//input[@placeholder='ИНН...']");
    private SelenideElement searchButton = $x("//*[@id='searchButtonKey']");
    private By personFoundByTaxNumber = By.xpath("//tr[contains(@ng-attr-class, 'row')]");
    private String taxNumber = TaxNumberGenerator.innfl();
    @Step("Открытие формы: \"Физическое лицо\".")
    public void openAddPersonForm(WebDriverWait wait, Actions actions){

        WebElement operationFromLeftNavBarElement = wait.until(ExpectedConditions.
                visibilityOfElementLocated(operationFromLeftNavBar));
        actions.moveToElement(operationFromLeftNavBarElement).click().build().perform();
        WebElement addPersonButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(addPersonButton));
        addPersonButtonElement.click();
        WebElement popupAddPersonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(popupAddPerson));
        popupAddPersonElement.click();
    }

    @Step("Добавление физического лица.")
    public void addPerson(WebDriverWait wait, Actions actions){
        createNewUniqueNumberOfPerson();
        fillInputsWithAWackyData();
        fillAddresses(wait);
        submitAddPerson(actions);
    }
    @Step("Проверка по ИНН: была ли создана запись.")
    public boolean findPersonByTaxNumber(){

        taxNumberSearchInput.shouldBe(Condition.interactable, Duration.ofSeconds(20)).clear();
        taxNumberSearchInput.sendKeys(taxNumber);
        searchButton.click();
        if(Selenide.$(personFoundByTaxNumber).exists()){
            return true;
        }
        else{return false;}

    }
    @Step("Завершение создания физического лица.")
    private void submitAddPerson(Actions actions) {
        if(submitAllButton.isDisplayed())
            submitAllButton.click();
        else
            actions.moveToElement(submitAllButton).click().build().perform();
        if(yesSubmitButton.isDisplayed())
            yesSubmitButton.click();
        else
            actions.moveToElement(yesSubmitButton).click().build().perform();
    }

    @Step("Заполнение адресов (Минимальным набором данных).")
    private void fillAddresses(WebDriverWait wait){
        birthAddressButton.click();
        birthAddressTextArea.sendKeys(AddPersonConfiguration.BIRTHADDRESS);
        birthAddressTextAreaSubmitButton.click();
        livingAddressButton.click();
        $("#BodyWrapper > div.modal.fade.ng-isolate-scope.show > div > div > " +
                "div.modal-body.ng-scope > div:nth-child(4) > div.col > dictionary-select-control > div > div")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        $x("//li[contains(@data-option-array-index, '63')]").click();
        Selenide.sleep(1000);//Без этого ошибка "Не заполнено поле Регион адреса"
        livingAddressSubmitFormButton.shouldBe(Condition.enabled).click();
    }

    @Step("Заполнение данных.")
    private void fillInputsWithAWackyData() {
        lastNameFormInput.click();
        lastNameFormInput.sendKeys(RandomStringGenerator.generateRandomString(10));
        firstNameFormInput.sendKeys(RandomStringGenerator.generateRandomString(5));
        taxNumberInput.sendKeys(taxNumber);
        sexInputUl.click();
        sexSelect.selectOptionContainingText(AddPersonConfiguration.SEX);
        birthDateInput.click();
        birthDateInput.sendKeys(AddPersonConfiguration.BIRTHDATE);
        birthDateInput.pressEnter();
        citizenshipDiv.click();
        citizenshipDropDown.selectOptionContainingText(AddPersonConfiguration.CITIZENSHIP);
    }

    @Step("Новый номер дела.")
    private void createNewUniqueNumberOfPerson() {
        newIdButton.click();
        yesSubmitButton.click();
    }
}
