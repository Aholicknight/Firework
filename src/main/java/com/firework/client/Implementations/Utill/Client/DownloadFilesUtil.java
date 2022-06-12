package com.firework.client.Implementations.Utill.Client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.FileUtils;

public class DownloadFilesUtil {
    public static void download(String url, File file, boolean forceDownload) {
        try {
            if (forceDownload || !file.exists()) {
                FileUtils.copyURLToFile(new URL(url), file);
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
