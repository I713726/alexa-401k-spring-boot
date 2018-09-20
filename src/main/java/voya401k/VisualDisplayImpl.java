package voya401k;

public class VisualDisplayImpl implements VisualDisplay {
    String primaryText;
    String secondaryText;
    String tertiaryText;
    String backgroundImageURL;
    String foregroundImageURL;
    String title;

    public VisualDisplayImpl(String title, String primaryText, String secondaryText, String tertiaryText,
                             String backgroundImageURL, String foregroundImageURL) {
        this.title = title;
        this.primaryText = primaryText;
        this.secondaryText = secondaryText;
        this.tertiaryText = tertiaryText;
        this.backgroundImageURL = backgroundImageURL;
        this.foregroundImageURL = foregroundImageURL;
    }

    @Override
    public String getPrimaryText() {
        return primaryText;
    }

    @Override
    public String getSecondaryText() {
        return secondaryText;
    }

    @Override
    public String getTertiaryText() {
        return tertiaryText;
    }

    @Override
    public String getForegroundImageURL() {
        return foregroundImageURL;
    }

    @Override
    public String getBackgroundImageURL() {
        return backgroundImageURL;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
