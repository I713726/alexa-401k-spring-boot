package voya401k;

import java.util.ArrayList;
import java.util.List;

public class VisualListImpl extends VisualDisplayImpl implements VisualList {
    ArrayList<String> listItems;

    public VisualListImpl(String title, String primaryText, String secondaryText, String tertiaryText,
                          String backgroundImageURL, String foregroundImageURL) {
        super(title, primaryText, secondaryText, tertiaryText, backgroundImageURL, foregroundImageURL);
        this.listItems = new ArrayList<>();
    }

    public VisualListImpl(String title, ArrayList<String> listItems, String primaryText, String secondaryText, String tertiaryText,
                          String backgroundImageURL, String foregroundImageURL) {
        super(title, primaryText, secondaryText, tertiaryText, backgroundImageURL,foregroundImageURL);
        this.listItems = listItems;
    }

    public void addItem(String item) {
        this.listItems.add(item);
    }

    public void removeItem(String item) {
        this.listItems.remove(item);
    }

    public List<String> getListItems() {
        return this.listItems;
    }
}
