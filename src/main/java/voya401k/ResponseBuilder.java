package voya401k;

public interface ResponseBuilder {
    String buildResponse(int questionNo, VoyaIntentType intentType, String locale, VoyaUserDataObject user);
}
