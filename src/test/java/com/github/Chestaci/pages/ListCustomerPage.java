package com.github.Chestaci.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Объект страницы со списком клиентов
 */

public class ListCustomerPage extends Page {

    /**
     * определение локатора вкладки first name
     */
    @FindBy(xpath = "//*[contains(text(),'First Name')]")
    private WebElement firstNameTab;
    /**
     * определение локатора поля ввода для поиска книентов
     */
    @FindBy(xpath = "//input[contains(@placeholder,'Search Customer')]")
    private WebElement searchCustomerField;

    /**
     * конструктор класса, занимающийся инициализацией полей класса
     *
     * @param driver
     */
    public ListCustomerPage(WebDriver driver) {
        super(driver);
    }

    /**
     * метод для осуществления получения таблицы с клиентами
     *
     * @return таблицу с клиентами
     */
    @Step("Получение таблицы с клиентами")
    public WebTable getTableCustomerList() {
        return new WebTable(driver.findElement(By.xpath("//*[contains(@class,'table table-bordered table-striped')]")));
    }

    /**
     * метод для осуществления очистки поля ввода для поиска клиентов
     */
    @Step("Очистка поля ввода для поиска клиентов")
    public void clearSearchField() {
        searchCustomerField.clear();
    }

    /**
     * метод для осуществления нажатия кнопки удаления клиента
     *
     * @param webTableElement строка из таблицы клиентов
     * @see WebTableElement
     */
    @Step("Нажатие на кнопку Delete клиента {webTableElement} на странице со списком клиентов")
    public void deleteCustomer(WebTableElement webTableElement) {
        webTableElement.getDeleteButton().click();
    }

    /**
     * метод для осуществления нажатия на вкладку first name
     */
    @Step("Нажатие на вкладку first name")
    public void clickFirstNameTab() {
        firstNameTab.click();
    }

    /**
     * метод для ввода
     *
     * @param searchCustomer Данные для поиска клиента
     */
    @Step("Заполнение поля ввода для поиска книентов: {searchCustomer}")
    public void inputSearchCustomer(String searchCustomer) {
        searchCustomerField.sendKeys(searchCustomer);
    }

}