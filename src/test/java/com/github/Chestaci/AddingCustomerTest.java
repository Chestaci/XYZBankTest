package com.github.Chestaci;

import com.github.Chestaci.pages.AddCustomerPage;
import com.github.Chestaci.pages.ListCustomerPage;
import com.github.Chestaci.pages.ManagerPage;
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
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;


@DisplayName("Тесты добавления клиента")
@Feature("Тесты проверки добавления клиента")
public class AddingCustomerTest {

    private final long DElAY_SECONDS = 20;
    private ManagerPage managerPage;
    private AddCustomerPage addCustomerPage;
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
        addCustomerPage = managerPage.clickAddCustomerButton();
    }

    @Step("Завершающие действия после теста")
    @AfterEach
    void tearDown() {
        driver.quit();
    }

    /**
     * Метод для осуществляющий действия по заполнению полей формы параметрами
     *
     * @param firstName Имя
     * @param lastName  Фамилия
     * @param postCode  Почтовый индекс,
     *                  а также нажатие кнопки добавления нового клиента
     */
    @Step("Заполнение полей ввода firstName: {firstName}," + " lastName: {lastName}, postCode: {postCode} и нажатие на кнопку добавления клиента")
    private void fillFieldsAndClick(String firstName, String lastName, String postCode) {
        addCustomerPage.fillCustomerFields(firstName, lastName, postCode);
        addCustomerPage.clickAddNewCustomerButton();
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

        fillFieldsAndClick(firstName, lastName, postCode);

        Alert alert = driver.switchTo().alert();
        String alertMessage = alert.getText();

        driver.switchTo().alert().accept();

        Assertions.assertTrue(alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert1").toUpperCase()), "Ожидаемый результат должен содержать: " + ConfProperties.getProperty("alert1") + ", фактический результат: " + alertMessage);

        ListCustomerPage listCust = managerPage.clickCustomersButton();
        listCust.deleteCustomer(listCust.getTableCustomerList().getElement(firstName, lastName, postCode));

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
        fillFieldsAndClick(firstName, lastName, postCode);

        String tooltipMessage = getTooltipMessage(firstName, lastName, postCode);

        Assertions.assertTrue(tooltipMessage.toUpperCase().contains(ConfProperties.getProperty("tooltip").toUpperCase()), "Ожидаемый результат должен содержать: " + ConfProperties.getProperty("tooltip") + ", фактический результат: " + tooltipMessage);
    }


    /**
     * Метод для получения подсказки о незаполненном поле
     *
     * @param firstName первый параметр в строке файла
     * @param lastName  второй параметр в строке файла
     * @param postCode  третий параметр в строке файла
     * @return подсказку о незаполненном поле
     */
    @Step("Получения подсказки о незаполненном поле")
    private String getTooltipMessage(String firstName, String lastName, String postCode) {
        if (firstName == null || firstName.isBlank() || firstName.isEmpty()) {
            return addCustomerPage.checkFirstNameTooltip();
        }
        if (lastName == null || lastName.isBlank() || lastName.isEmpty()) {
            return addCustomerPage.checkLastNameTooltip();
        }
        if (postCode == null || postCode.isBlank() || postCode.isEmpty()) {
            return addCustomerPage.checkPostCodeTooltip();
        }
        return "";
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

        fillFieldsAndClick(" ", " ", " ");

        Alert alert = driver.switchTo().alert();
        String alertMessage = alert.getText();

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
        fillFieldsAndClick(ConfProperties.getProperty("first_name1"), ConfProperties.getProperty("last_name1"), ConfProperties.getProperty("post_code1"));
        driver.switchTo().alert().accept();

        //создание второго клиента с такими же данными
        fillFieldsAndClick(ConfProperties.getProperty("first_name1"), ConfProperties.getProperty("last_name1"), ConfProperties.getProperty("post_code1"));

        Alert alert = driver.switchTo().alert();
        String alertMessage = alert.getText();

        driver.switchTo().alert().accept();

        Assertions.assertTrue(alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert2").toUpperCase()), "Ожидаемый результат должен содержать: " + ConfProperties.getProperty("alert2") + ", фактический результат: " + alertMessage);

    }
}
