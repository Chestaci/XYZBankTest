package com.github.Chestaci.pages;

import org.openqa.selenium.WebElement;
import java.util.Objects;

/**
 * Объект элемента таблицы
 */
public class WebTableElement implements Comparable {
    private String firstName;
    private String lastName;
    private String postCode;
    private String accountNumber;
    private WebElement deleteButton;

    public WebTableElement(String firstName, String lastName, String postCode, String accountNumber, WebElement deleteButton) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postCode = postCode;
        this.accountNumber = accountNumber;
        this.deleteButton = deleteButton;
    }

    public WebTableElement() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public WebElement getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(WebElement deleteButton) {
        this.deleteButton = deleteButton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebTableElement that = (WebTableElement) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(postCode, that.postCode) && Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, postCode, accountNumber);
    }

    @Override
    public int compareTo(Object o) {
        int compareFirstName = firstName.compareTo(((WebTableElement) o).getFirstName());
        if (compareFirstName == 0) {
            int compareLastName = lastName.compareTo(((WebTableElement) o).getLastName());
            if (compareLastName == 0) {
                return postCode.compareTo(((WebTableElement) o).getPostCode());
            }
            return compareLastName;
        }
        return compareFirstName;
    }

    @Override
    public String toString() {
        return "WebTableElement{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", postCode='" + postCode + '\'' + ", accountNumber='" + accountNumber + '\'' + '}';
    }
}
