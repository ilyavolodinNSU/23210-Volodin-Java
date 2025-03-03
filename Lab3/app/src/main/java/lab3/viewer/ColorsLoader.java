package lab3.viewer;

import com.google.gson.*;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.HashMap;

public class ColorsLoader {
    public static Map<Integer, Color> loadColors(String presetsPath) {
        Gson gson = new Gson();
        Map<Integer, Color> idToColorMap = new HashMap<>();

        try (InputStream inputStream = ColorsLoader.class.getResourceAsStream(presetsPath);
             InputStreamReader reader = new InputStreamReader(inputStream)) {

            JsonObject json = gson.fromJson(reader, JsonObject.class);

            if (json.has("background")) {
                JsonObject background = json.getAsJsonObject("background");
                int id = background.get("id").getAsInt();
                JsonArray colorArray = background.getAsJsonArray("color");

                Color bgColor = new Color(colorArray.get(0).getAsInt(),
                                          colorArray.get(1).getAsInt(),
                                          colorArray.get(2).getAsInt());

                idToColorMap.put(id, bgColor);
            }

            JsonObject shapes = json.getAsJsonObject("shapes");
            for (Map.Entry<String, JsonElement> entry : shapes.entrySet()) {
                JsonObject shape = entry.getValue().getAsJsonObject();
                int id = shape.get("id").getAsInt();
                JsonArray colorArray = shape.getAsJsonArray("color");

                Color shapeColor = new Color(colorArray.get(0).getAsInt(),
                                             colorArray.get(1).getAsInt(),
                                             colorArray.get(2).getAsInt());

                Color shadowColor = new Color(shapeColor.getRed(), shapeColor.getGreen(), shapeColor.getBlue(), 50);

                idToColorMap.put(id, shapeColor);
                idToColorMap.put(-id, shadowColor);
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return idToColorMap;
    }
}
