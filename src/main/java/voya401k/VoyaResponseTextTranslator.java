package voya401k;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This is class is a function object that uses Microsoft's translation API to translate input text into the desired
 * language.
 */
public class VoyaResponseTextTranslator {

    /**
     * Sends text to translate to Microsoft Translate
     * @param text text to translate
     * @param locale code for locale, taken from <a href =https://developer.amazon.com/docs/custom-skills/develop-skills-in-multiple-languages.html>here</a>
     * @return
     */
    public String translate(String text, String locale) {
        String langParam;
        switch(locale) {
            case "es-ES":
                langParam = "es";
                break;
            case "de-DE":
                langParam = "de";
                break;
            case "fr-FR":
                langParam = "fr";
                break;
            case "it-IT":
                langParam = "it";
                break;
            case "ja-JP":
                langParam = "ja";
                break;
                default:
                    throw new IllegalArgumentException("Locale not supported!");
        }
        try {
            String subscriptionKey = "d86e4a5c293f43a6820833723cd565e6";
            String host = "https://api.cognitive.microsofttranslator.com";
            String path = "/translate?api-version=3.0";
            String params = "&to=";
            URL url = new URL(host + path + params + langParam);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Text", text);
            JSONArray array = new JSONArray();
            array.put(jsonObject);
            String content = array.toString();
            System.out.print(content);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Conent-Length", content.length() + "");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
            connection.setRequestProperty("X-ClientTraceID", java.util.UUID.randomUUID().toString());
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            byte[] encoded_content = content.getBytes("UTF-8");
            wr.write(encoded_content, 0, encoded_content.length);
            wr.flush();
            wr.close();

            StringBuilder response = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONArray responseJSON = new JSONArray(response.toString());

            return responseJSON.getJSONObject(0).getJSONArray("translations").getJSONObject(0).getString("text");


        }
        catch(MalformedURLException e) {
            return "Malformed URL";
        }
        catch(IOException e) {
            e.printStackTrace();
            return "IO Exception";
        }
    }
}
