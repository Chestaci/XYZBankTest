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
    @FindBy(xpath = "//input[contains(@placeholder,'First Name')]")
    private WebElement firstNameField;
    /**
     * определение локатора поля ввода last name
     */
    @FindBy(xpath = "//input[contains(@placeholder,'Last Name')]")
    private WebElement lastNameField;
    /**
     * определение локатора поля ввода post code
     */
    @FindBy(xpath = "//input[contains(@placeholder,'Post Code')]")
    private WebElement postCodeField;
    /**
     * определение локатора поля кнопки добавления нового клиента
     */
    @FindBy(xpath = "//*[contains(@class,'btn btn-default')]")
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
    @Step("Проверка текста подсказки")
    public String checkFirstNameTooltip() {
        return firstNameField.getAttribute("validationMessage");
    }

    /**
     * метод для осуществления проверки сообщения подсказки для поля Last Name
     *
     * @return возвращает текст подсказки
     */
    @Step("Проверка текста подсказки")
    public String checkLastNameTooltip() {
        return lastNameField.getAttribute("validationMessage");
    }

    /**
     * метод для осуществления проверки сообщения подсказки для поля Post Code
     *
     * @return возвращает текст подсказки
     */
    @Step("Проверка текста подсказки")
    public String checkPostCodeTooltip() {
        return postCodeField.getAttribute("validationMessage");
    }

    /**
     * метод для осуществления нажатия на кнопку добавления нового клиента
     */
    @Step("Нажатие на кнопку добавления нового клиента")
    public void clickAddNewCustomerButton() {
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
    public void fillCustomerFields(String firstName, String lastName, String postCode) {
        inputFirstName(firstName);
        inputLastName(lastName);
        inputPostCode(postCode);
    }
}
