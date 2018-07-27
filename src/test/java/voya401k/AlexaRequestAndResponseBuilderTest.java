package voya401k;
import org.junit.Assert;
import org.junit.Test;

public class AlexaRequestAndResponseBuilderTest {

    @Test
    public void testCreateRequest() {
        AlexaRequestAndResponseBuilder builder = new AlexaRequestAndResponseBuilder();
        String launchJSON = "{\n" +
                "\t\"version\": \"1.0\",\n" +
                "\t\"session\": {\n" +
                "\t\t\"new\": true,\n" +
                "\t\t\"sessionId\": \"amzn1.echo-api.session.293c755e-c8cc-4164-a3ce-148e10be1992\",\n" +
                "\t\t\"application\": {\n" +
                "\t\t\t\"applicationId\": \"amzn1.ask.skill.8494a428-4792-4b63-a599-bc73d1a4ba65\"\n" +
                "\t\t},\n" +
                "\t\t\"user\": {\n" +
                "\t\t\t\"userId\": \"amzn1.ask.account.AHSBD3YT5YSE7BZXWEJ7ABMNH6JMLG3KXICVOZHGJSWPJW57NQG6Q3HLWPA2DZPERAEN2VHABPFU2GOWCSI6XDHMZAEO7ONGKXUMLKD6LAZXYI4FWW5NZWGA4HRMR3W4VBSR6IL6XE3ZY5C3BFBO3NEVPIDYSRXKVXM2BNKFLH3WTRNEMSN3CCTK4IGEL6IUR7NZ4LGKEV56LYA\"\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"context\": {\n" +
                "\t\t\"System\": {\n" +
                "\t\t\t\"application\": {\n" +
                "\t\t\t\t\"applicationId\": \"amzn1.ask.skill.8494a428-4792-4b63-a599-bc73d1a4ba65\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"user\": {\n" +
                "\t\t\t\t\"userId\": \"amzn1.ask.account.AHSBD3YT5YSE7BZXWEJ7ABMNH6JMLG3KXICVOZHGJSWPJW57NQG6Q3HLWPA2DZPERAEN2VHABPFU2GOWCSI6XDHMZAEO7ONGKXUMLKD6LAZXYI4FWW5NZWGA4HRMR3W4VBSR6IL6XE3ZY5C3BFBO3NEVPIDYSRXKVXM2BNKFLH3WTRNEMSN3CCTK4IGEL6IUR7NZ4LGKEV56LYA\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"device\": {\n" +
                "\t\t\t\t\"deviceId\": \"amzn1.ask.device.AHHH3BPJMG2YYC4J36H7BX4G52NB4NSX62AYOJYOLWLXFYJW5ZVQ6UMMAW3C6R2VYNLTNPJM4KIWE5EO5UG625UBSMUMSKULNTLGTIMOOY34AGJVPFUVWDKC3BOUEVJCYFXPRBYJG5DMIWI5V6CDDONULA2Q\",\n" +
                "\t\t\t\t\"supportedInterfaces\": {}\n" +
                "\t\t\t},\n" +
                "\t\t\t\"apiEndpoint\": \"https://api.amazonalexa.com\",\n" +
                "\t\t\t\"apiAccessToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLjg0OTRhNDI4LTQ3OTItNGI2My1hNTk5LWJjNzNkMWE0YmE2NSIsImV4cCI6MTUzMjYzNDIyNSwiaWF0IjoxNTMyNjMwNjI1LCJuYmYiOjE1MzI2MzA2MjUsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUhISDNCUEpNRzJZWUM0SjM2SDdCWDRHNTJOQjROU1g2MkFZT0pZT0xXTFhGWUpXNVpWUTZVTU1BVzNDNlIyVllOTFROUEpNNEtJV0U1RU81VUc2MjVVQlNNVU1TS1VMTlRMR1RJTU9PWTM0QUdKVlBGVVZXREtDM0JPVUVWSkNZRlhQUkJZSkc1RE1JV0k1VjZDRERPTlVMQTJRIiwidXNlcklkIjoiYW16bjEuYXNrLmFjY291bnQuQUhTQkQzWVQ1WVNFN0JaWFdFSjdBQk1OSDZKTUxHM0tYSUNWT1pIR0pTV1BKVzU3TlFHNlEzSExXUEEyRFpQRVJBRU4yVkhBQlBGVTJHT1dDU0k2WERITVpBRU83T05HS1hVTUxLRDZMQVpYWUk0RldXNU5aV0dBNEhSTVIzVzRWQlNSNklMNlhFM1pZNUMzQkZCTzNORVZQSURZU1JYS1ZYTTJCTktGTEgzV1RSTkVNU04zQ0NUSzRJR0VMNklVUjdOWjRMR0tFVjU2TFlBIn19.jdc1P_TiD-ev5IOPx0QHoumy5zA3c9pskH0aaSL3oHuzN389XrrFEAk6Ash35aWj6OSBMz9sA-kM0FZUKmBypnifkwoZXzTeLrmsIPNX9JDxyNzRQH20to2MLhshOW4BKGh5AU4dcgMrDLZaNpJV3_g5xmwuqQYS5ufTI9-mQt-1UFIQJhUcgJ-niZ5cFMibYgfwDDvukEOBkPomyiX9xsVtB9ZjhyjmqfJfWaER4WObUhHxcpl22prGD6CHm8U9M3Tcq6-acxoN2mITziheF_S0XdIUozNlajW_uELq1SSk8_QFWUuQU3jeeXSuLShrQdGYXKVsl0GsuzU99WlJVw\"\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"request\": {\n" +
                "\t\t\"type\": \"LaunchRequest\",\n" +
                "\t\t\"requestId\": \"amzn1.echo-api.request.93d3a105-cebd-4360-95e9-1d45d4f67a06\",\n" +
                "\t\t\"timestamp\": \"2018-07-26T18:43:45Z\",\n" +
                "\t\t\"locale\": \"en-US\",\n" +
                "\t\t\"shouldLinkResultBeReturned\": false\n" +
                "\t}\n" +
                "}";

        String pinJSON = "{\n" +
                "\t\"version\": \"1.0\",\n" +
                "\t\"session\": {\n" +
                "\t\t\"new\": false,\n" +
                "\t\t\"sessionId\": \"amzn1.echo-api.session.80c364ed-145a-4614-a8b2-847c429b6ddd\",\n" +
                "\t\t\"application\": {\n" +
                "\t\t\t\"applicationId\": \"amzn1.ask.skill.8494a428-4792-4b63-a599-bc73d1a4ba65\"\n" +
                "\t\t},\n" +
                "\t\t\"attributes\": {\n" +
                "\t\t\t\"questionNo\": \"0\"\n" +
                "\t\t},\n" +
                "\t\t\"user\": {\n" +
                "\t\t\t\"userId\": \"amzn1.ask.account.AHSBD3YT5YSE7BZXWEJ7ABMNH6JMLG3KXICVOZHGJSWPJW57NQG6Q3HLWPA2DZPERAEN2VHABPFU2GOWCSI6XDHMZAEO7ONGKXUMLKD6LAZXYI4FWW5NZWGA4HRMR3W4VBSR6IL6XE3ZY5C3BFBO3NEVPIDYSRXKVXM2BNKFLH3WTRNEMSN3CCTK4IGEL6IUR7NZ4LGKEV56LYA\"\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"context\": {\n" +
                "\t\t\"System\": {\n" +
                "\t\t\t\"application\": {\n" +
                "\t\t\t\t\"applicationId\": \"amzn1.ask.skill.8494a428-4792-4b63-a599-bc73d1a4ba65\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"user\": {\n" +
                "\t\t\t\t\"userId\": \"amzn1.ask.account.AHSBD3YT5YSE7BZXWEJ7ABMNH6JMLG3KXICVOZHGJSWPJW57NQG6Q3HLWPA2DZPERAEN2VHABPFU2GOWCSI6XDHMZAEO7ONGKXUMLKD6LAZXYI4FWW5NZWGA4HRMR3W4VBSR6IL6XE3ZY5C3BFBO3NEVPIDYSRXKVXM2BNKFLH3WTRNEMSN3CCTK4IGEL6IUR7NZ4LGKEV56LYA\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"device\": {\n" +
                "\t\t\t\t\"deviceId\": \"amzn1.ask.device.AHHH3BPJMG2YYC4J36H7BX4G52NB4NSX62AYOJYOLWLXFYJW5ZVQ6UMMAW3C6R2VYNLTNPJM4KIWE5EO5UG625UBSMUMSKULNTLGTIMOOY34AGJVPFUVWDKC3BOUEVJCYFXPRBYJG5DMIWI5V6CDDONULA2Q\",\n" +
                "\t\t\t\t\"supportedInterfaces\": {}\n" +
                "\t\t\t},\n" +
                "\t\t\t\"apiEndpoint\": \"https://api.amazonalexa.com\",\n" +
                "\t\t\t\"apiAccessToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLjg0OTRhNDI4LTQ3OTItNGI2My1hNTk5LWJjNzNkMWE0YmE2NSIsImV4cCI6MTUzMjYzMzg1OCwiaWF0IjoxNTMyNjMwMjU4LCJuYmYiOjE1MzI2MzAyNTgsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUhISDNCUEpNRzJZWUM0SjM2SDdCWDRHNTJOQjROU1g2MkFZT0pZT0xXTFhGWUpXNVpWUTZVTU1BVzNDNlIyVllOTFROUEpNNEtJV0U1RU81VUc2MjVVQlNNVU1TS1VMTlRMR1RJTU9PWTM0QUdKVlBGVVZXREtDM0JPVUVWSkNZRlhQUkJZSkc1RE1JV0k1VjZDRERPTlVMQTJRIiwidXNlcklkIjoiYW16bjEuYXNrLmFjY291bnQuQUhTQkQzWVQ1WVNFN0JaWFdFSjdBQk1OSDZKTUxHM0tYSUNWT1pIR0pTV1BKVzU3TlFHNlEzSExXUEEyRFpQRVJBRU4yVkhBQlBGVTJHT1dDU0k2WERITVpBRU83T05HS1hVTUxLRDZMQVpYWUk0RldXNU5aV0dBNEhSTVIzVzRWQlNSNklMNlhFM1pZNUMzQkZCTzNORVZQSURZU1JYS1ZYTTJCTktGTEgzV1RSTkVNU04zQ0NUSzRJR0VMNklVUjdOWjRMR0tFVjU2TFlBIn19.Tr7Xa8QFW2FK-Qn5Cc01Fq83_hNS7AZsDXExjuK56vMQMQrBVB_dWjWtA3VXyf7qG0DbmIcChK9Z9nwThm3dVp3RJ0G6DIQYAB5WBboz1HTt17_ZrcKXMiK7M9r74ukIgOnKXSYfzZuaKYk_Vm56JlvaMPTS8IXaJe32Rw6f7RiWbZfEq-pEhO0RPtxhMoJhEaxU_IXrECV_wyYr4SbLY9qtLs8hmH7yuPKwlkEfDZk5JiFYQv6IodrLM-u3f86lV3x2qmQYerp9sNp681FSxruIaDB7OJvCCaqt2Bkm5vCAQRZl5ZOCvOhSeuHV27lBteT-jKi5MO3wh5YSgVApRQ\"\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"request\": {\n" +
                "\t\t\"type\": \"IntentRequest\",\n" +
                "\t\t\"requestId\": \"amzn1.echo-api.request.045ccf03-0153-4417-8dea-3c70e08d5a85\",\n" +
                "\t\t\"timestamp\": \"2018-07-26T18:37:38Z\",\n" +
                "\t\t\"locale\": \"en-US\",\n" +
                "\t\t\"intent\": {\n" +
                "\t\t\t\"name\": \"VoyaPINIntent\",\n" +
                "\t\t\t\"confirmationStatus\": \"NONE\",\n" +
                "\t\t\t\"slots\": {\n" +
                "\t\t\t\t\"pin\": {\n" +
                "\t\t\t\t\t\"name\": \"pin\",\n" +
                "\t\t\t\t\t\"value\": \"1111\",\n" +
                "\t\t\t\t\t\"confirmationStatus\": \"NONE\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        String howMyAccountJSON = "{\n" +
                "\t\"version\": \"1.0\",\n" +
                "\t\"session\": {\n" +
                "\t\t\"new\": false,\n" +
                "\t\t\"sessionId\": \"amzn1.echo-api.session.80c364ed-145a-4614-a8b2-847c429b6ddd\",\n" +
                "\t\t\"application\": {\n" +
                "\t\t\t\"applicationId\": \"amzn1.ask.skill.8494a428-4792-4b63-a599-bc73d1a4ba65\"\n" +
                "\t\t},\n" +
                "\t\t\"attributes\": {\n" +
                "\t\t\t\"voyaPin\": \"1111\"\n" +
                "\t\t},\n" +
                "\t\t\"user\": {\n" +
                "\t\t\t\"userId\": \"amzn1.ask.account.AHSBD3YT5YSE7BZXWEJ7ABMNH6JMLG3KXICVOZHGJSWPJW57NQG6Q3HLWPA2DZPERAEN2VHABPFU2GOWCSI6XDHMZAEO7ONGKXUMLKD6LAZXYI4FWW5NZWGA4HRMR3W4VBSR6IL6XE3ZY5C3BFBO3NEVPIDYSRXKVXM2BNKFLH3WTRNEMSN3CCTK4IGEL6IUR7NZ4LGKEV56LYA\"\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"context\": {\n" +
                "\t\t\"System\": {\n" +
                "\t\t\t\"application\": {\n" +
                "\t\t\t\t\"applicationId\": \"amzn1.ask.skill.8494a428-4792-4b63-a599-bc73d1a4ba65\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"user\": {\n" +
                "\t\t\t\t\"userId\": \"amzn1.ask.account.AHSBD3YT5YSE7BZXWEJ7ABMNH6JMLG3KXICVOZHGJSWPJW57NQG6Q3HLWPA2DZPERAEN2VHABPFU2GOWCSI6XDHMZAEO7ONGKXUMLKD6LAZXYI4FWW5NZWGA4HRMR3W4VBSR6IL6XE3ZY5C3BFBO3NEVPIDYSRXKVXM2BNKFLH3WTRNEMSN3CCTK4IGEL6IUR7NZ4LGKEV56LYA\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"device\": {\n" +
                "\t\t\t\t\"deviceId\": \"amzn1.ask.device.AHHH3BPJMG2YYC4J36H7BX4G52NB4NSX62AYOJYOLWLXFYJW5ZVQ6UMMAW3C6R2VYNLTNPJM4KIWE5EO5UG625UBSMUMSKULNTLGTIMOOY34AGJVPFUVWDKC3BOUEVJCYFXPRBYJG5DMIWI5V6CDDONULA2Q\",\n" +
                "\t\t\t\t\"supportedInterfaces\": {}\n" +
                "\t\t\t},\n" +
                "\t\t\t\"apiEndpoint\": \"https://api.amazonalexa.com\",\n" +
                "\t\t\t\"apiAccessToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLjg0OTRhNDI4LTQ3OTItNGI2My1hNTk5LWJjNzNkMWE0YmE2NSIsImV4cCI6MTUzMjYzMzkwNSwiaWF0IjoxNTMyNjMwMzA1LCJuYmYiOjE1MzI2MzAzMDUsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUhISDNCUEpNRzJZWUM0SjM2SDdCWDRHNTJOQjROU1g2MkFZT0pZT0xXTFhGWUpXNVpWUTZVTU1BVzNDNlIyVllOTFROUEpNNEtJV0U1RU81VUc2MjVVQlNNVU1TS1VMTlRMR1RJTU9PWTM0QUdKVlBGVVZXREtDM0JPVUVWSkNZRlhQUkJZSkc1RE1JV0k1VjZDRERPTlVMQTJRIiwidXNlcklkIjoiYW16bjEuYXNrLmFjY291bnQuQUhTQkQzWVQ1WVNFN0JaWFdFSjdBQk1OSDZKTUxHM0tYSUNWT1pIR0pTV1BKVzU3TlFHNlEzSExXUEEyRFpQRVJBRU4yVkhBQlBGVTJHT1dDU0k2WERITVpBRU83T05HS1hVTUxLRDZMQVpYWUk0RldXNU5aV0dBNEhSTVIzVzRWQlNSNklMNlhFM1pZNUMzQkZCTzNORVZQSURZU1JYS1ZYTTJCTktGTEgzV1RSTkVNU04zQ0NUSzRJR0VMNklVUjdOWjRMR0tFVjU2TFlBIn19.Uu9iN6XBgItNF_rOmYElljyCTxE9w_cDfC-OddMt3XI1rFbRCU3d9TmRVYk2IN-Q8sk4pvsSGs08v34CaHpa7hLuNq0LVBs3CMrGxFRodPzZvQSaFO7636CTZmuzkq7QYCcoDQBY-Ssi6_Be9SWFptygvqUqiq32U8y8oywQ8J_Idrte57pqT88PqF557aOU3Z2ykcubBl-NrmgPkiguTejeL0LSeHcIPrCrYmTm01e0lcGrOGxgf6DsHTbiq1B2asJwcV5dGGioXb7xbcOdn9HtfpdW_STxggM4CeGHIR5ZqyM7pdpau3-3fhoQLt2qsvW9EbnQ-RqxE2NW_sCxCA\"\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"request\": {\n" +
                "\t\t\"type\": \"IntentRequest\",\n" +
                "\t\t\"requestId\": \"amzn1.echo-api.request.fc8d5f51-a1c5-42aa-9168-fb44e8cb7984\",\n" +
                "\t\t\"timestamp\": \"2018-07-26T18:38:25Z\",\n" +
                "\t\t\"locale\": \"en-US\",\n" +
                "\t\t\"intent\": {\n" +
                "\t\t\t\"name\": \"VoyaHowMyAccountIntent\",\n" +
                "\t\t\t\"confirmationStatus\": \"NONE\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        VoyaRequest launchReq = builder.buildRequest(launchJSON);
        VoyaRequest pinReq = builder.buildRequest(pinJSON);
        VoyaRequest summaryRequest = builder.buildRequest(howMyAccountJSON);

        Assert.assertEquals(true,launchReq.getLocale().equals("en-US"));
        Assert.assertEquals(0, launchReq.getQuestionNo());
        Assert.assertEquals(VoyaRequestType.LAUNCH_REQUEST, launchReq.getRequestType());
        Assert.assertEquals(null, launchReq.getIntent());
        Assert.assertEquals(0,launchReq.getVoyaPIN());

        Assert.assertEquals(true,pinReq.getLocale().equals("en-US"));
        Assert.assertEquals(0, pinReq.getQuestionNo());
        Assert.assertEquals(VoyaRequestType.INTENT_REQUEST, pinReq.getRequestType());
        Assert.assertEquals(VoyaIntentType.PIN, pinReq.getIntent());
        Assert.assertEquals(1111,pinReq.getVoyaPIN());

        Assert.assertEquals(true,summaryRequest.getLocale().equals("en-US"));
        Assert.assertEquals(0, summaryRequest.getQuestionNo());
        Assert.assertEquals(VoyaRequestType.INTENT_REQUEST, summaryRequest.getRequestType());
        Assert.assertEquals(VoyaIntentType.SUMMARY, summaryRequest.getIntent());
        Assert.assertEquals(1111,summaryRequest.getVoyaPIN());
    }

    @Test
    public void testCreateResponse() {
        VoyaResponse response = new VoyaResponseImpl(1, 1111, "hi ther", "", false);

        String expectedJSON = "{\"response\":{\"shouldEndSession\":false,\"reprompt\":{\"outputSpeech\":{\"ssml\":\"<speak><\\/speak>\",\"type\":\"SSML\"}},\"outputSpeech\":{\"ssml\":\"<speak>hi ther<\\/speak>\",\"type\":\"SSML\"}},\"sessionAttributes\":{\"questionNo\":1,\"voyaPin\":1111},\"version\":1}";
        System.out.println("</speak>");
        System.out.println(" fuck" + "</speak>");
        AlexaRequestAndResponseBuilder builder = new AlexaRequestAndResponseBuilder();
        System.out.println("<speak>" + response.getSpeech() + "</speak>");
        System.out.println(builder.buildResponse(response));
        Assert.assertEquals(true, builder.buildResponse(response).equals(expectedJSON));
    }
}
