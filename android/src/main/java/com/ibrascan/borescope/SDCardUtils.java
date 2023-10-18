package com.ibrascan.borescope;

import android.os.Environment;

import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SDCardUtils {
    public static String getSecondExternPath(){
        List<String> allExterSdcardPath = getAllExterSdcardPath();
        if(allExterSdcardPath.size() != 2) {
            return null;
        }
        for (String str : allExterSdcardPath) {
            if (str != null && !str.equals(getFirstExternPath())) {
                return str;
            }
        }
        return null;
    }

    public static String getFirstExternPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static List<String> getAllExterSdcardPath() {
        ArrayList arrayList = new ArrayList();
        String firstExternPath = getFirstExternPath();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("mount").getInputStream()));
            while(true) {
                String readLine = bufferedReader.readLine();
                if(readLine == null) {
                    break;
                    //RETIRED FROM CODE readLine.contains(ResponseCacheMiddleware.CACHE)
                } else if(!(readLine.contains("secure") || readLine.contains("asec") || readLine.contains("media") || readLine.contains("system") || readLine.contains(NotificationCompat.CATEGORY_SYSTEM) || readLine.contains("data") || readLine.contains("tmpfs") || readLine.contains("shell") || readLine.contains("root") || readLine.contains("acct") || readLine.contains("proc") || readLine.contains("misc") || readLine.contains("obb"))) {
                    if (readLine.contains("fat") || readLine.contains("fuse") || readLine.contains("ntfs")) {
                        String[] split = readLine.split(" ");
                        if (split != null && split.length > 1) {
                            String toLowerCase = split[1].toLowerCase(Locale.getDefault());
                            if (!(toLowerCase == null || arrayList.contains(toLowerCase) || !toLowerCase.contains("sd"))) {
                                arrayList.add(split[1]);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!arrayList.contains(firstExternPath)) {
            arrayList.add(firstExternPath);
        }
        return arrayList;
    }
}
