package com.github.Chestaci;

import com.github.Chestaci.pages.AddCustomerPage;
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


@DisplayName("Тесты добавления клиента")
@Feature("Тесты проверки добавления клиента")
public class AddingCustomerTest {

    private ManagerPage managerPage;
    private AddCustomerPage addCustomerPage;
    private WebDriver driver = null;

    @Step("Инициализация перед началом теста")
    @BeforeEach
    void setUp() {
        driver = new ChromeDriver(WebDriverUtils.getChromeOptions());
        WebDriverUtils.setUpDriver(driver);
        managerPage = new ManagerPage(driver);
        driver.get(ConfProperties.getProperty("manager_page"));
        addCustomerPage = managerPage.clickAddCustomerButton();
    }

    @Step("Завершающие действия после теста")
    @AfterEach
    void tearDown() {
        driver.quit();
    }


    /**
     * Параметризированный тест для осуществления проверки успешного добавления клиента
     * Параметры для теста передаются из файла resources/successfulAddingCustomer.csv
     *
     * @param firstName первый параметр в строке файла
     * @param lastName  второй параметр в строке файла
     * @param postCode  третий параметр в строке файла
     */
    @DisplayName("Проверка успешного добавления клиента")
    @Description("Параметризированный тест для осуществления проверки успешного добавления клиента")
    @Story("Тест успешного добавления клиента")
    @ParameterizedTest
    @CsvFileSource(resources = "/successfulAddingCustomer.csv")
    public void successfulAddingCustomerTest(String firstName, String lastName, String postCode) {

        addCustomerPage.fillFieldsAndClick(firstName, lastName, postCode);

        String alertMessage = driver.switchTo().alert().getText();

        driver.switchTo().alert().accept();

        Assertions.assertTrue(alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert1").toUpperCase()), "Ожидаемый результат должен содержать: " + ConfProperties.getProperty("alert1") + ", фактический результат: " + alertMessage);
    }


    /**
     * Параметризированный тест для осуществления проверки получения подсказки о незаполненном поле при попытке
     * создания клиента с незаполненными полями
     * Параметры для теста передаются из файла resources/emptyFieldsError.csv
     *
     * @param firstName первый параметр в строке файла
     * @param lastName  второй параметр в строке файла
     * @param postCode  третий параметр в строке файла
     */
    @DisplayName("Проверка получения подсказки о незаполненном поле при попытке создания клиента с незаполненными полями")
    @Description("Параметризированный тест для осуществления проверки получения подсказки " + "о незаполненном поле при неправильном добавлении клиента")
    @Story("Тест проверки сообщения об ошибке")
    @ParameterizedTest
    @CsvFileSource(resources = "/emptyFieldsError.csv")
    public void checkingEmptyFieldsErrorTest(String firstName, String lastName, String postCode) {
        addCustomerPage.fillFieldsAndClick(firstName, lastName, postCode);

        String tooltipMessage = addCustomerPage.getTooltipMessage(firstName, lastName, postCode);

        Assertions.assertTrue(tooltipMessage.toUpperCase().contains(ConfProperties.getProperty("tooltip").toUpperCase()), "Ожидаемый результат должен содержать: " + ConfProperties.getProperty("tooltip") + ", фактический результат: " + tooltipMessage);
    }

    /**
     * тестовый метод для осуществления проверки сообщения об ошибке при попытке
     * создания клиента с заполнением полей пробелом
     */
    @Test
    @DisplayName("Проверка сообщения об ошибке при попытке создания клиента с заполнением полей пробелом")
    @Description("Тест для осуществления проверки сообщения об ошибке при неправильном добавлении клиента")
    @Story("Тест проверки сообщения об ошибке")
    public void checkingSpaceFieldsErrorTest() {

        addCustomerPage.fillFieldsAndClick(" ", " ", " ");

        String alertMessage = driver.switchTo().alert().getText();

        driver.switchTo().alert().accept();

        Assertions.assertTrue(alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert2").toUpperCase()), "Ожидаемый результат должен содержать: " + ConfProperties.getProperty("alert2") + ", фактический результат: " + alertMessage);
    }

    /**
     * тестовый метод для осуществления проверки сообщения об ошибке при попытке добавления дубликата клиента
     */
    @Test
    @DisplayName("Проверка сообщения об ошибке при попытке добавления дубликата клиента")
    @Description("Тест для осуществления проверки сообщения об ошибке при неправильном добавлении клиента")
    @Story("Тест проверки сообщения об ошибке")
    public void checkingDuplicateErrorTest() {

        //создание первого клиента
        addCustomerPage.fillFieldsAndClick(ConfProperties.getProperty("first_name1"), ConfProperties.getProperty("last_name1"), ConfProperties.getProperty("post_code1"));
        driver.switchTo().alert().accept();

        //создание второго клиента с такими же данными
        addCustomerPage.fillFieldsAndClick(ConfProperties.getProperty("first_name1"), ConfProperties.getProperty("last_name1"), ConfProperties.getProperty("post_code1"));

        String alertMessage = driver.switchTo().alert().getText();

        driver.switchTo().alert().accept();

        Assertions.assertTrue(alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert2").toUpperCase()), "Ожидаемый результат должен содержать: " + ConfProperties.getProperty("alert2") + ", фактический результат: " + alertMessage);

    }
}
