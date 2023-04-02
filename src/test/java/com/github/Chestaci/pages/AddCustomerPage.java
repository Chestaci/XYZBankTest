package com.github.Chestaci.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Объект страницы добавления клиента
 */

public class AddCustomerPage extends Page {

    /**
     * определение локатора поля ввода first name
     */
    @FindBy(css = "input[ng-model=fName]")
    private WebElement firstNameField;
    /**
     * определение локатора поля ввода last name
     */
    @FindBy(css = "input[ng-model=lName]")
    private WebElement lastNameField;
    /**
     * определение локатора поля ввода post code
     */
    @FindBy(css = "input[ng-model=postCd]")
    private WebElement postCodeField;
    /**
     * определение локатора поля кнопки добавления нового клиента
     */
    @FindBy(css = "[type=submit]")
    private WebElement addNewCustomerButton;

    /**
     * конструктор класса, занимающийся инициализацией полей класса
     *
     * @param driver
     */
    public AddCustomerPage(WebDriver driver) {
        super(driver);
    }

    /**
     * метод для ввода
     *
     * @param firstName Имя
     */
    @Step("Заполнение поля firstName: {firstName} на странице добавления клиента")
    private void inputFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfAllElements(firstNameField));
        firstNameField.sendKeys(firstName);
    }

    /**
     * метод для ввода
     *
     * @param lastName Фамилия
     */
    @Step("Заполнение поля lastName: {lastName} на странице добавления клиента")
    private void inputLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfAllElements(lastNameField));
        lastNameField.sendKeys(lastName);
    }

    /**
     * метод для ввода
     *
     * @param postCode Почтовый индекс
     */
    @Step("Заполнение поля postCode: {postCode} на странице добавления клиента")
    private void inputPostCode(String postCode) {
        wait.until(ExpectedConditions.visibilityOfAllElements(postCodeField));
        postCodeField.sendKeys(postCode);
    }

    /**
     * метод для осуществления проверки сообщения подсказки для поля First Name
     *
     * @return возвращает текст подсказки
     */
    @Step("Получение текста подсказки поля First Name")
    private String getFirstNameTooltip() {
        return firstNameField.getAttribute("validationMessage");
    }

    /**
     * метод для осуществления проверки сообщения подсказки для поля Last Name
     *
     * @return возвращает текст подсказки
     */
    @Step("Получение текста подсказки поля Last Name")
    private String getLastNameTooltip() {
        return lastNameField.getAttribute("validationMessage");
    }

    /**
     * метод для осуществления проверки сообщения подсказки для поля Post Code
     *
     * @return возвращает текст подсказки
     */
    @Step("Получение текста подсказки поля Post Code")
    private String getPostCodeTooltip() {
        return postCodeField.getAttribute("validationMessage");
    }

    /**
     * метод для осуществления нажатия на кнопку добавления нового клиента
     */
    @Step("Нажатие на кнопку добавления нового клиента")
    private void clickAddNewCustomerButton() {
        addNewCustomerButton.click();
    }


    /**
     * метод для осуществляющий действия по заполнению полей формы параметрами
     *
     * @param firstName Имя
     * @param lastName  Фамилия
     * @param postCode  Почтовый индекс
     */
    @Step("Заполнение полей firstName: {firstName}, lastName: {lastName}, postCode: {postCode}")
    private void fillCustomerFields(String firstName, String lastName, String postCode) {
        inputFirstName(firstName);
        inputLastName(lastName);
        inputPostCode(postCode);
    }

    /**
     * Метод для осуществляющий действия по заполнению полей формы параметрами
     *
     * @param firstName Имя
     * @param lastName  Фамилия
     * @param postCode  Почтовый индекс,
     *                  а также нажатие кнопки добавления нового клиента
     */
    @Step("Заполнение полей ввода firstName: {firstName}," + " lastName: {lastName}, postCode: {postCode} и нажатие на кнопку добавления клиента")
    public void fillFieldsAndClick(String firstName, String lastName, String postCode) {
        fillCustomerFields(firstName, lastName, postCode);
        clickAddNewCustomerButton();
    }


    /**
     * Метод для получения подсказки о незаполненном поле
     *
     * @param firstName первый параметр в строке файла
     * @param lastName  второй параметр в строке файла
     * @param postCode  третий параметр в строке файла
     * @return подсказку о незаполненном поле
     */
    @Step("Получения подсказки о незаполненном поле")
    public String getTooltipMessage(String firstName, String lastName, String postCode) {
        if (firstName == null || firstName.isBlank() || firstName.isEmpty()) {
            return getFirstNameTooltip();
        }
        if (lastName == null || lastName.isBlank() || lastName.isEmpty()) {
            return getLastNameTooltip();
        }
        if (postCode == null || postCode.isBlank() || postCode.isEmpty()) {
            return getPostCodeTooltip();
        }
        return "";
    }
}
