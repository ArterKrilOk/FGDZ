package com.pixelswordgames.fgdz.BackEnd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader {
    private Context context;


    private ImageDownloader(Context context) {
        this.context = context;
    }

    public static ImageDownloader with(Context context){
        return new ImageDownloader(context);
    }

    public Bitmap getBitmap(String src){
        File imgFile = new File(context.getCacheDir(), src.hashCode()+"");

        if(imgFile.exists())
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());

        Bitmap bitmap = getBitmapFromUrl(src);

        if(bitmap != null) {
            saveBitmapToCache(bitmap, src.hashCode() + "");
            return bitmap;
        }
        return null;
    }

    private Bitmap getBitmapFromUrl(String src){
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    private Uri saveBitmapToCache(Bitmap bitmap, String name){
        File file = new File(context.getCacheDir(), name);

        try{
            OutputStream stream = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

            stream.flush();

            stream.close();

        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return Uri.parse(file.getAbsolutePath());
    }

}
