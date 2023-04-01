package com.github.Chestaci;


import com.github.Chestaci.pages.ListCustomerPage;
import com.github.Chestaci.pages.ManagerPage;
import com.github.Chestaci.pages.WebTableElement;
import com.github.Chestaci.utils.ConfProperties;
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
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@DisplayName("Тесты поиска клиентов")
@Feature("Тесты проверки поиска клиентов")
public class CustomerSearchTest {

    private final long DElAY_SECONDS = 20;
    private ManagerPage managerPage;
    private ListCustomerPage listCustomerPage;
    private WebDriver driver = null;

    /**
     * Метод сравнивает вводимые данные на совпадение в списке клиентов
     *
     * @param searchCustomer вводимые данные для поиска клиента
     * @param customersList  список клиентов
     * @return список найденных клиентов
     * @see WebTableElement
     */
    @Step("Получение списка клиентов из таблицы, удовлетворяющих параметрам поиска")
    private static List<WebTableElement> getContainsList(String searchCustomer, List<WebTableElement> customersList) {
        List<WebTableElement> containsList = new ArrayList<>();
        for (WebTableElement webTableElement : customersList) {

            if (webTableElement.getFirstName().contains(searchCustomer) || webTableElement.getLastName().contains(searchCustomer) || webTableElement.getPostCode().contains(searchCustomer) || webTableElement.getAccountNumber().contains(searchCustomer)) {

                containsList.add(webTableElement);
            }
        }
        return containsList;
    }

    @Step("Инициализация перед началом теста")
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DElAY_SECONDS));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(DElAY_SECONDS));
        managerPage = new ManagerPage(driver);
        driver.get(ConfProperties.getProperty("manager_page"));
        listCustomerPage = managerPage.clickCustomersButton();
    }

    @Step("Завершающие действия после теста")
    @AfterEach
    void tearDown() {
        listCustomerPage.clearSearchField();
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
        List<WebTableElement> foundCustomersList = getContainsList(searchCustomer, listCustomerPage.getTableCustomerList().getListElement());
        Collections.sort(foundCustomersList);

        listCustomerPage.inputSearchCustomer(searchCustomer);

        //Получение списока строк в таблице с клиентами после
        //проведения поиска по заданным параметрам
        List<WebTableElement> customersList = listCustomerPage.getTableCustomerList().getListElement();
        Collections.sort(customersList);

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
        List<WebTableElement> customersList = listCustomerPage.getTableCustomerList().getListElement();

        Assertions.assertTrue(customersList.isEmpty());
    }
}
