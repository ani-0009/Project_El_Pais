package utils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslationUtil {
    private static final String API_KEY = "";
    private static final String API_HOST = "google-translate113.p.rapidapi.com";
    private static final String API_URL = "https://google-translate113.p.rapidapi.com/api/v1/translator/json";
    private final OkHttpClient client = new OkHttpClient();

    public List<String> translateToEnglish(List<String> spanishTitles) {
        List<String> translatedTitles = new ArrayList<>();
        for (String title : spanishTitles) {
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("from", "es");
                jsonBody.put("to", "en");
                jsonBody.put("json", new JSONObject().put("title", title));

                RequestBody body = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    jsonBody.toString()
                );

                Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-rapidapi-host", API_HOST)
                    .addHeader("x-rapidapi-key", API_KEY)
                    .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    JSONObject responseJson = new JSONObject(response.body().string());
                    String translatedTitle = responseJson.getJSONObject("trans").getString("title");
                    translatedTitles.add(translatedTitle);
                } else {
                    System.out.println("Translation failed for title: " + title + ", Status: " + response.code());
                    translatedTitles.add(title);
                }
            } catch (Exception e) {
                System.out.println("Translation error for title: " + title + ", Error: " + e.getMessage());
                translatedTitles.add(title); 
            }
        }
        return translatedTitles;
    }

    public Map<String, Integer> analyzeRepeatedWords(List<String> titles) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String title : titles) {
            String[] words = title.toLowerCase().split("\\W+");
            for (String word : words) {
                if (word.length() > 2) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }
        Map<String, Integer> repeatedWords = new HashMap<>();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 2) {
                repeatedWords.put(entry.getKey(), entry.getValue());
            }
        }
        return repeatedWords;
    }
}