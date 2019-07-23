package com.sanjnan.shopping.apps;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by vavasthi on 15/1/18.
 */

public class FileUtils {
    public static File getTempFile(Context context) {

        try {

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return getTempFile(context.getExternalFilesDir(null));
            }
            else {
                return getTempFile(context.getFilesDir());
            }
        }
        catch(Exception e) {
            return null;
        }
    }
    public static File getTempFile(Context context, String dir, String extension) {

        try {

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return getTempFile(context.getExternalFilesDir(null), dir, extension);
            }
            else {
                return getTempFile(context.getFilesDir(), dir, extension);
            }
        }
        catch(Exception e) {
            return null;
        }
    }
    public static Uri getUri(Context context, File file) {
        return Uri.fromFile(file);
    }
    private static File getTempFile(File filesDir) throws IOException {

        return getTempFile(filesDir, "images", ".jpg");
    }

    protected static File getTempFile(File filesDir, String dir, String extension) throws IOException {

        File reportsDir = new File(filesDir, dir);
        if (reportsDir.isDirectory() || reportsDir.mkdirs()) {

            String filename = generateRandomString(16);
            return File.createTempFile(filename, extension, reportsDir);
        }
        return null;
    }
    public static String generateRandomString(int length) {
        char[] charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < length; ++i) {
            result[i] = charset[random.nextInt(charset.length)];
        }
        return new String(result);
    }
    public static File createImageFile(Context context) {
        try {

            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "TQ_" + timeStamp + "_";
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            return image;
        }
        catch(Exception e) {
            return null;
        }
    }

}
