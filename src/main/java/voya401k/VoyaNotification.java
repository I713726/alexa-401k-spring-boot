package voya401k;

public interface VoyaNotification {
    String getText();
    boolean isInteractive();
    void sendResponse(String response);
}
