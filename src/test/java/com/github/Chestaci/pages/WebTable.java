package com.github.Chestaci.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Объект таблицы
 */
public class WebTable {
    private WebElement webTableBody;

    public WebTable(WebElement webTable) {
        setWebTable(webTable);
    }

    public WebElement getWebTable() {
        return webTableBody;
    }

    public void setWebTable(WebElement webTable) {
        this.webTableBody = webTable.findElement(By.tagName("tbody"));
    }

    /**
     * Метод получения количества строк в таблице
     *
     * @return количество строк
     */
    public int getRowCount() {
        List<WebElement> tableRows = webTableBody.findElements(By.tagName("tr"));
        return tableRows.size();
    }

    /**
     * Метод получения количества столбцов в таблице
     *
     * @return количество столбцов
     */
    public int getColumnCount() {
        List<WebElement> tableRows = webTableBody.findElements(By.tagName("tr"));
        WebElement headerRow = tableRows.get(0);
        List<WebElement> tableCols = headerRow.findElements(By.tagName("td"));
        return tableCols.size();
    }

    /**
     * Метод получения списка элементов таблицы
     *
     * @return список WebTableElement
     * @see WebTableElement
     */
    @Step("Получение списка элементов таблицы")
    public List<WebTableElement> getListElement() {
        List<WebTableElement> webTableElements;

        List<WebElement> tableRows = webTableBody.findElements(By.tagName("tr"));

        List<WebTableElement> list = new ArrayList<>();
        for (WebElement rowElement : tableRows) {
            List<WebElement> rowList = rowElement.findElements(By.tagName("td"));
            WebTableElement webTableElement = new WebTableElement(rowList.get(0).getText(), rowList.get(1).getText(), rowList.get(2).getText(), rowList.get(3).getText(), rowList.get(4));
            list.add(webTableElement);
        }
        webTableElements = list;

        return webTableElements;
    }

    /**
     * Метод получение элемента по параметрам
     *
     * @param firstName Имя
     * @param lastName  Фамилия
     * @param postCode  Почтовый индекс
     * @return элемент таблицы, если не найден возвращает null
     * @see WebTableElement
     */
    @Step("Метод получение элемента по параметрам ввода firstName: {firstName}, lastName: {lastName}, postCode: {postCode}")
    public WebTableElement getElement(String firstName, String lastName, String postCode) {
        return getListElement().stream().filter(webTableElement -> webTableElement.getFirstName().equals(firstName) && webTableElement.getLastName().equals(lastName) && webTableElement.getPostCode().equals(postCode)).findFirst().orElse(null);
    }

}
