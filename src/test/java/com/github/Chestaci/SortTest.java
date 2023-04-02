package com.github.Chestaci;

import com.github.Chestaci.elements.WebTableElement;
import com.github.Chestaci.elements.comparators.WebTableElementASCComparator;
import com.github.Chestaci.elements.comparators.WebTableElementDESCComparator;
import com.github.Chestaci.pages.ListCustomerPage;
import com.github.Chestaci.pages.ManagerPage;
import com.github.Chestaci.utils.ConfProperties;
import com.github.Chestaci.utils.WebDriverUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

@DisplayName("Тесты сортировки клиентов")
@Feature("Тесты проверки сортировки клиентов")
@Story("Тесты успешной сортировки клиентов")
public class SortTest {
    private ListCustomerPage listCustomerPage;
    private WebDriver driver = null;

    @Step("Инициализация перед началом теста")
    @BeforeEach
    void setUp() {
        driver = WebDriverUtils.getPreparedDriver();
        ManagerPage managerPage = new ManagerPage(driver);
        driver.get(ConfProperties.getProperty("manager_page"));
        listCustomerPage = managerPage.clickCustomersButton();
    }

    @Step("Завершающие действия после теста")
    @AfterEach
    void tearDown() {
        driver.quit();
    }

    /**
     * тестовый метод для осуществления проверки успешной сортировки клиентов по имени
     * в обратном алфавитном порядке
     */
    @Test
    @DisplayName("Проверка успешной сортировки клиентов по имени в обратном алфавитном порядке")
    @Description("Тест для осуществления проверки успешной сортировки клиентов")
    public void successfulReverseSortTest() {

        //Cортировка списка клиентов в обратном порядке для сравнения с
        //конечным списком, который должен быть отсорирован нажатием на вкладку first name
        List<WebTableElement> customersListBefore = listCustomerPage.getTableElementsList();
        customersListBefore.sort(new WebTableElementDESCComparator());

        listCustomerPage.clickFirstNameTab();

        //Получение списка клиентов, которые уже должны быть отсортированы
        //нажатием на вкладку first name
        List<WebTableElement> customersListAfter = listCustomerPage.getTableElementsList();

        Assertions.assertEquals(customersListBefore, customersListAfter);

    }

    /**
     * тестовый метод для осуществления проверки успешной сортировки клиентов по имени в алфавитном порядке
     */
    @Test
    @DisplayName("Проверка успешной сортировки клиентов по имени в алфавитном порядке")
    @Description("Тест для осуществления проверки успешной сортировки клиентов")
    public void successfulSortTest() {
        //Cортировка списка клиентов в обратном порядке для сравнения с
        //конечным списком, который должен быть отсорирован нажатием на вкладку first name
        List<WebTableElement> customersListBefore = listCustomerPage.getTableElementsList();
        customersListBefore.sort(new WebTableElementASCComparator());

        listCustomerPage.clickFirstNameTab();
        listCustomerPage.clickFirstNameTab();

        //Получение списка клиентов, которые уже должны быть отсортированы
        //нажатием на вкладку first name
        List<WebTableElement> customersListAfter = listCustomerPage.getTableElementsList();

        Assertions.assertEquals(customersListBefore, customersListAfter);
    }
}
