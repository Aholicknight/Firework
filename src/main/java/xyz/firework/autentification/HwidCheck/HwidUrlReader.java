package xyz.firework.autentification.HwidCheck;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import xyz.firework.autentification.HwidCheck.HwidManager;

public class HwidUrlReader {
    public static List<String> readURL() {
        ArrayList<String> s = new ArrayList<String>();
        try {
            String hwid;
            URL url = new URL(HwidManager.pastebinURL);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((hwid = bufferedReader.readLine()) != null) {
                s.add(hwid);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return s;
    }
}
