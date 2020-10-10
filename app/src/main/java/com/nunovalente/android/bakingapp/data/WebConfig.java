package com.nunovalente.android.bakingapp.data;

import java.net.MalformedURLException;
import java.net.URL;

public class WebConfig {

    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net";

    public static URL getUrl() {
        URL url = null;
        try {
            url = new URL(RECIPE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
