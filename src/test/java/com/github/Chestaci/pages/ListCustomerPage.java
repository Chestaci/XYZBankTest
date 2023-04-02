package com.github.Chestaci.pages;

import com.github.Chestaci.elements.WebTableElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

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

    @FindBy(xpath = "//*[contains(@class,'table table-bordered table-striped')]")
    private WebElement tableCustomerList;

    /**
     * конструктор класса, занимающийся инициализацией полей класса
     *
     * @param driver
     */
    public ListCustomerPage(WebDriver driver) {
        super(driver);
    }

    /**
     * метод для осуществления получения таблицы с клиентами в виде списка элементов строк
     *
     * @return таблицу с клиентами в виде списка элементов строк
     */
    @Step("Получение таблицы с клиентами в виде списка элементов строк")
    public List<WebTableElement> getTableElementsList() {

        List<WebTableElement> webTableElements = new ArrayList<>();
        //получение тела таблицы
        WebElement tbody = tableCustomerList.findElement(By.tagName("tbody"));
        //получение строк
        List<WebElement> tableRows = tbody.findElements(By.tagName("tr"));

        if (tableRows.isEmpty()) {
            return webTableElements;
        }
        //проход по значениям колонок каждой строки
        for (WebElement rowElement : tableRows) {
            List<WebElement> rowList = rowElement.findElements(By.tagName("td"));
            WebTableElement webTableElement = new WebTableElement(rowList.get(0).getText(), rowList.get(1).getText(), rowList.get(2).getText(), rowList.get(3).getText(), rowList.get(4));
            webTableElements.add(webTableElement);
        }

        return webTableElements;
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