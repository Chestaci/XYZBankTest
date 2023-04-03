package com.github.Chestaci.elements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;

/**
 * Объект элемента таблицы
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebTableElement implements Comparable {
    private String firstName;
    private String lastName;
    private String postCode;
    private String accountNumber;
    private WebElement deleteButton;

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
}
