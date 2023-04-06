package gtn.testtask.gtntest.addPerson;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AddPersonPage {
    private final SelenideElement operationFromLeftNavBar = $x("//a[@title='Операции']");
    private final SelenideElement addPersonButton = $x("//a[contains(@class, 'add')]");
    private final SelenideElement personsLi = $x("//a[contains(@href, 'ersons')]");
    private final SelenideElement popupAddPerson  = $x("//a[@title='Физ. лицо']");

    private final SelenideElement lastNameFormInput = $x("//input[@class='form-control" +
       " mandatory-field-color-useroption169 ng-empty'][1]");
    private final SelenideElement firstNameFormInput = $x("//input[@class='form-control" +
      " mandatory-field-color-useroption169 ng-empty'][1]");
    private final SelenideElement taxNumberInput = $x("//input[@ng-model='person.Inn']");
    private final SelenideElement sexInputUl = $x("//div[@ng-click='selectClick()'][.//option[@label='М']]");
    private final SelenideElement sexSelect = $x("//select[.//option[@label='М']]");
    private final SelenideElement birthDateInput = $x("//input[@ng-blur='checkPerson()']");
    private final SelenideElement citizenshipDropDown = $x("//select[.//option[@value='string:88']]");
    private final SelenideElement citizenshipDiv = $x("//div[contains(@class, 'input-group')]" +
       "[.//option[@value='string:88']]");
    private final SelenideElement birthAddressButton = $x(
       "/html/body/div[1]/div/div/div[2]/div/div/div[1]" +
               "/div[1]/div/div[6]/div[2]/address-control/div/div/button[1]");

    private final SelenideElement birthAddressTextArea = $x("//textarea[@ng-model='address']");
    private final SelenideElement birthAddressTextAreaSubmitButton = $x("//button[@class='btn btn-green']");
    private final SelenideElement livingAddressButton = $x("/html/body/div[1]/div/div/div[2]/div/div/" +
            "div[1]/div[1]/div/div[7]/div[2]/address-control/div/div/button[1]");
    private final SelenideElement livingAddressSubmitFormButton = $("#BodyWrapper > div.modal.fade." +
            "ng-isolate-scope.show > div > div > div.modal-footer.ng-scope" +
            " > div > ok-cancel-buttons > button.btn.btn-green.ng-binding");
    private final SelenideElement newIdButton = $x("//button[@ng-click='newNumber()']");
    private final SelenideElement submitAllButton = $x("//button[@ng-click='ok()']");
    private final SelenideElement yesSubmitButton = $x("//button[@ng-click='yes()']");

    private final SelenideElement taxNumberSearchInput = $x("//input[@placeholder='ИНН...']");
    private final SelenideElement searchButton = $x("//*[@id='searchButtonKey']");
    private final By personFoundByTaxNumber = By.xpath("//tr[contains(@ng-attr-class, 'row')]");
    private final String taxNumber = TaxNumberGenerator.innfl();
    @Step("Открытие формы: \"Физическое лицо\".")
    public void openAddPersonForm(){
        operationFromLeftNavBar.click();
        personsLi.click();
        addPersonButton.click();
        popupAddPerson.click();

    }

    @Step("Добавление физического лица.")
    public void addPerson(Actions actions){
        createNewUniqueNumberOfPerson();
        fillInputsWithAWackyData();
        fillAddresses();
        submitAddPerson(actions);
    }
    @Step("Проверка по ИНН: была ли создана запись.")
    public boolean findPersonByTaxNumber(){

        taxNumberSearchInput.shouldBe(Condition.interactable, Duration.ofSeconds(40)).clear();
        taxNumberSearchInput.sendKeys(taxNumber);
        searchButton.shouldBe(Condition.interactable, Duration.ofSeconds(40)).click();
        return Selenide.$(personFoundByTaxNumber).shouldBe(Condition.visible,Condition.enabled, Condition.interactable)
                .exists();

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
    private void fillAddresses(){
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
        Selenide.sleep(3000);//Без этого ошибка "Не заполнено поле Регион адреса"
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
