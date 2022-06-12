package xyz.firework.autentification.AutoUpdate;

import java.util.ArrayList;
import java.util.List;
import xyz.firework.autentification.AutoUpdate.UpdateError;
import xyz.firework.autentification.AutoUpdate.UpdateURLReader;
import xyz.firework.autentification.NoStackTraceThrowable;

public class UpdateManager {
    public static final String pastebinURL = "https://fireworkclient.site/data/auto_update";
    public static List<String> hwids = new ArrayList<String>();

    public static void hwidCheck() {
        hwids = UpdateURLReader.readURL();
        boolean isHwidPresent = hwids.contains("0");
        System.out.println(hwids);
        if (!isHwidPresent) {
            UpdateError.Display();
            throw new NoStackTraceThrowable("");
        }
    }
}
