package com.github.Chestaci;

import com.github.Chestaci.elements.WebTableElement;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.stream.Collectors;

@DisplayName("Тесты поиска клиентов")
@Feature("Тесты проверки поиска клиентов")
public class CustomerSearchTest {

    private ManagerPage managerPage;
    private ListCustomerPage listCustomerPage;
    private WebDriver driver = null;


    @Step("Инициализация перед началом теста")
    @BeforeEach
    void setUp() {
        driver = new ChromeDriver(WebDriverUtils.getChromeOptions());
        WebDriverUtils.setUpDriver(driver);
        managerPage = new ManagerPage(driver);
        driver.get(ConfProperties.getProperty("manager_page"));
        listCustomerPage = managerPage.clickCustomersButton();
    }

    @Step("Завершающие действия после теста")
    @AfterEach
    void tearDown() {
        driver.quit();
    }

    /**
     * Параметризированный тест для осуществления проверки успешного поиска клиентов по совпадению вводимых данных
     * Параметры для теста передаются из файла resources/successfulSearchCustomer.csv
     *
     * @param searchCustomer параметр в строке файла
     */
    @DisplayName("Проверка успешного поиска клиентов по совпадению вводимых данных")
    @Description("Тест для осуществления проверки поиска клиентов")
    @Story("Тест успешного поиска клиентов")
    @ParameterizedTest
    @CsvFileSource(resources = "/successfulSearchCustomer.csv")
    public void successfulSearchCustomerTest(String searchCustomer) {
        //Получение списока строк в таблице с клиентами, удовлетворяющих параметрам поиска
        //(для дальнейшего сравнения с результатом поиска)
        List<WebTableElement> foundCustomersList = listCustomerPage.getTableElementsList().
                stream().
                filter(webTableElement ->
                        webTableElement.getFirstName().contains(searchCustomer) ||
                                webTableElement.getLastName().contains(searchCustomer) ||
                                webTableElement.getPostCode().contains(searchCustomer) ||
                                ((webTableElement.getAccountNumber() != null) && (webTableElement.getAccountNumber().contains(searchCustomer)))
                ).
                sorted().
                collect(Collectors.toList());

        listCustomerPage.inputSearchCustomer(searchCustomer);

        //Получение списока строк в таблице с клиентами после
        //проведения поиска по заданным параметрам
        List<WebTableElement> customersList = listCustomerPage.getTableElementsList().stream().sorted().collect(Collectors.toList());

        Assertions.assertEquals(foundCustomersList, customersList);
    }

    /**
     * тестовый метод для осуществления проверки поиска несуществующего клиента
     */
    @Test
    @DisplayName("Проверка поиска несуществующего клиента")
    @Description("Тест для осуществления проверки поиска клиентов")
    @Story("Тест поиска несуществующих клиентов")
    public void searchNonexistentCustomerTest() {
        String search = ConfProperties.getProperty("search_customer");
        listCustomerPage.inputSearchCustomer(search);

        //Получение списока строк в таблице с клиентами по заданным параметрам поиска
        List<WebTableElement> customersList = listCustomerPage.getTableElementsList();

        Assertions.assertTrue(customersList.isEmpty(), "Ожидаемый результат: список должен  быть пустым, фактический результат: " + customersList);
    }
}
