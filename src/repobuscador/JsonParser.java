package repobuscador;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author tarod
 */
public class JsonParser {

    /**
     * @param repoName
     * @return
     */
    public static ArrayList<String[]> getResults(String repoName) {
        URL url = null;
        ArrayList<String[]> results = new ArrayList<>();
        try {
            url = new URL("https://api.github.com/search/repositories?q=" + repoName);
        } catch (MalformedURLException ex) {
            Logger.getLogger(JsonParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {
            JsonObject obj = rdr.readObject();
            JsonArray jsonResults = obj.getJsonArray("items");

            for (JsonObject result : jsonResults.getValuesAs(JsonObject.class)) {
                results.add(new String[]{
                    result.getString("name"),
                    result.get("language").toString(),
                    result.getString("html_url")
                });
            }

            return results;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
