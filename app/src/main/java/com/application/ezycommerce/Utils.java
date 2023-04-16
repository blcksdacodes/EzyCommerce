package com.application.ezycommerce;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {
    public static Drawable getUrlAsDrawable(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, url);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap getUrlAsBitmap(String url){
        try {
            URL urlPicture = new URL(url);
            return BitmapFactory.decodeStream(urlPicture.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
