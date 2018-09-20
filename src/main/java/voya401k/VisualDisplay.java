package voya401k;

import org.json.JSONObject;

/**
 * This interface represents a standard interface for presenting visual content.
 * It includes primary, secondary, and tertiary text as well as a foreground and background image.
 * The formatting and placing of these variables is determined in the RequestAndResponseBuilder.
 */
public interface VisualDisplay {
    String getPrimaryText();
    String getSecondaryText();
    String getTertiaryText();
    String getForegroundImageURL();
    String getBackgroundImageURL();
    String getTitle();
}