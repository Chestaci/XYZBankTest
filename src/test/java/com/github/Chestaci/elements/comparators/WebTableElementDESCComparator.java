package com.github.Chestaci.elements.comparators;

import com.github.Chestaci.elements.WebTableElement;

import java.util.Comparator;

/**
 * Компаратор для сортировки в обратном алфавитном порядке элементов
 *
 * @see WebTableElement
 */
public class WebTableElementDESCComparator implements Comparator<WebTableElement> {
    @Override
    public int compare(WebTableElement webTableElement1, WebTableElement webTableElement2) {
        return webTableElement2.getFirstName().compareToIgnoreCase(webTableElement1.getFirstName());
    }
}
