package com.github.Chestaci.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Объект страницы Manager
 */

public class ManagerPage extends Page {

    /**
     * определение локатора поля кнопки добавления клиента
     */
    @FindBy(css = "[ng-class=btnClass1]")
    private WebElement addCustomerButton;

    /**
     * определение локатора поля кнопки списка клиентов
     */
    @FindBy(css = "[ng-class=btnClass3]")
    private WebElement customersButton;


    /**
     * конструктор класса, занимающийся инициализацией полей класса
     *
     * @param driver
     */
    public ManagerPage(WebDriver driver) {
        super(driver);
    }

    /**
     * метод для осуществления нажатия кнопки добавления клиента
     *
     * @return возвращает страницу AddCustomer
     * @see AddCustomerPage
     */
    @Step("Нажатие на кнопку добавления клиента")
    public AddCustomerPage clickAddCustomerButton() {
        wait.until(ExpectedConditions.visibilityOfAllElements(addCustomerButton));
        addCustomerButton.click();
        return new AddCustomerPage(this.driver);
    }

    /**
     * метод для осуществления нажатия кнопки списка клиентов
     *
     * @return возвращает страницу ListCustomer
     * @see ListCustomerPage
     */
    @Step("Нажатие на кнопку списка клиентов")
    public ListCustomerPage clickCustomersButton() {
        wait.until(ExpectedConditions.visibilityOfAllElements(customersButton));
        customersButton.click();
        return new ListCustomerPage(this.driver);
    }
}
