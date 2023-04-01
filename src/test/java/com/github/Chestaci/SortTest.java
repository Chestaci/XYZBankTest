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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

@DisplayName("Тесты сортировки клиентов")
@Feature("Тесты проверки сортировки клиентов")
@Story("Тесты успешной сортировки клиентов")
public class SortTest {

    private final long DElAY_SECONDS = 90;
    private ManagerPage managerPage;
    private ListCustomerPage listCustomerPage;
    private WebDriver driver = null;

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
        driver.quit();
    }

    /**
     * тестовый метод для осуществления проверки успешной сортировки клиентов по имени
     * в обратном порядке
     */
    @Test
    @DisplayName("Проверка успешной сортировки клиентов по имени в обратном порядке")
    @Description("Тест для осуществления проверки успешной сортировки клиентов")
    public void successfulReverseSortTest() {

        //Cортировка списка клиентов в обратном порядке для сравнения с
        //конечным списком, который должен быть отсорирован нажатием на вкладку first name
        List<WebTableElement> customersListBefore = listCustomerPage.getTableCustomerList().getListElement();
        customersListBefore.sort(new Comparator<WebTableElement>() {
            @Override
            public int compare(WebTableElement webTableElement1, WebTableElement webTableElement2) {
                return webTableElement2.getFirstName().compareToIgnoreCase(webTableElement1.getFirstName());
            }
        });

        listCustomerPage.clickFirstNameTab();

        //Получение списка клиентов, которые уже должны быть отсортированы
        //нажатием на вкладку first name
        List<WebTableElement> customersListAfter = listCustomerPage.getTableCustomerList().getListElement();

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
        List<WebTableElement> customersListBefore = listCustomerPage.getTableCustomerList().getListElement();
        customersListBefore.sort(new Comparator<WebTableElement>() {
            @Override
            public int compare(WebTableElement webTableElement1, WebTableElement webTableElement2) {
                return webTableElement1.getFirstName().compareToIgnoreCase(webTableElement2.getFirstName());
            }
        });

        listCustomerPage.clickFirstNameTab();
        listCustomerPage.clickFirstNameTab();

        //Получение списка клиентов, которые уже должны быть отсортированы
        //нажатием на вкладку first name
        List<WebTableElement> customersListAfter = listCustomerPage.getTableCustomerList().getListElement();

        Assertions.assertEquals(customersListBefore, customersListAfter);
    }
}
