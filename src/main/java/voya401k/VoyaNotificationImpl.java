package voya401k;

public class VoyaNotificationImpl implements VoyaNotification{

    private String text;
    private boolean isInteractive;

    public VoyaNotificationImpl(String text, boolean isInteractive) {
        this.text = text;
        this.isInteractive = isInteractive;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean isInteractive() {
        return isInteractive;
    }

    @Override
    public void sendResponse(String response) {

    }
}
