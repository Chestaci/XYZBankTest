package com.github.Chestaci.pages;

import com.github.Chestaci.elements.WebTableElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Объект страницы со списком клиентов
 */

public class ListCustomerPage extends Page {

    /**
     * определение локатора вкладки first name
     */
    @FindBy(css = "[ng-click*=fName]")
    private WebElement firstNameTab;
    /**
     * определение локатора поля ввода для поиска книентов
     */
    @FindBy(css = "input[ng-model=searchCustomer]")
    private WebElement searchCustomerField;

//    @FindBy(css = "tr")
//    private List<WebElement> rowList;


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

        List<WebElement> rowList = driver.findElements(By.cssSelector("tr"));
        wait.until(ExpectedConditions.visibilityOfAllElements(rowList));
//        wait.until(ExpectedConditions.(rowList));

        for (int i = 1; i < rowList.size(); i++) {
            WebElement webElement = rowList.get(i);
            List<WebElement> colList = webElement.findElements(By.tagName("td"));

            wait.until(ExpectedConditions.visibilityOfAllElements(colList));

            webTableElements.add(new WebTableElement(colList.get(0).getText(), colList.get(1).getText(), colList.get(2).getText(), colList.get(3).getText(), colList.get(4)));
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