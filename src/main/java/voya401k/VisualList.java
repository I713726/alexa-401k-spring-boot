package voya401k;

import java.util.List;

/**
 * This interface represents a visual display that presents a list of String items.
 */

public interface VisualList extends VisualDisplay{

    /**
     * return the list of String items
     * @return list of strings
     */
    List<String> getListItems();

    /**
     * Add an item to the list
     * @param item String to be added
     */
    void addItem(String item);

    /**
     * remove an item from the list
     * @param item item to be removed
     */
    void removeItem(String item);
}
