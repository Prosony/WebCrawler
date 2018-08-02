package services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class JsonService {
    public JsonArray getJsonFromFile(String PATH){
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                builder.append(sCurrentLine);
            }
            if (!builder.toString().isEmpty()){
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(builder.toString());
                JsonObject object = element.getAsJsonObject();
                return object.getAsJsonArray("url-profile");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
