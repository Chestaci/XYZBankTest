package com.github.Chestaci.elements.comparators;

import com.github.Chestaci.elements.WebTableElement;

import java.util.Comparator;

/**
 * Компаратор для сортировки в алфавитном порядке элементов
 *
 * @see WebTableElement
 */
public class WebTableElementASCComparator implements Comparator<WebTableElement> {
    @Override
    public int compare(WebTableElement webTableElement1, WebTableElement webTableElement2) {
        return webTableElement1.getFirstName().compareToIgnoreCase(webTableElement2.getFirstName());
    }
}
