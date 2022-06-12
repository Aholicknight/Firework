package com.firework.client.Implementations.Managers.Parser;

import com.firework.client.Firework;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

public class JsonPrefixPraser {
    public static void parse() {
        JSONObject obj = new JSONObject();
        obj.put("Prefix", ".");
        File theDir = new File(Firework.FIREWORK_DIRECTORY + "Prefix.json");
        if (!theDir.exists()) {
            try (FileWriter file = new FileWriter(Firework.FIREWORK_DIRECTORY + "Prefix.json");){
                file.write(obj.toJSONString());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
