package com.example.qiu.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("WeatherForecast Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String currTemperatuer = "0";
        private String minTemperatuer = "0";
        private String maxTemperatuer = "0";
        private String iconName = "";
        private Bitmap currImage;

        @Override
        protected String doInBackground(String ... params) {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

            retrieveTemperature(urlString);
            retrieveIcon(urlString);
            return "";
        }

        private void retrieveTemperature(String urlString) {
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setDoInput(true);
            // Starts the query
            try {
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //return conn.getInputStream();

            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                parser.nextTag();
                readFeedTemperatur(parser);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        private void retrieveIcon(String urlString) {
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setDoInput(true);
            // Starts the query
            try {
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //return conn.getInputStream();

            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                parser.nextTag();
                readFeedWeather(parser);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        private void readFeedTemperatur(XmlPullParser parser) throws XmlPullParserException, IOException {
            parser.require(XmlPullParser.START_TAG, null, "current");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("temperature")) {
                    currTemperatuer = parser.getAttributeValue(null, "value");
                    publishProgress(25);

                    minTemperatuer = parser.getAttributeValue(null, "min");
                    publishProgress(50);

                    maxTemperatuer = parser.getAttributeValue(null, "max");

                    publishProgress(75);
                } else {
                    skip(parser);

                }

            }
        }

        private void readFeedWeather(XmlPullParser parser) throws XmlPullParserException, IOException {
            parser.require(XmlPullParser.START_TAG, null, "current");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("weather")) {
                    iconName = parser.getAttributeValue(null, "icon");
                    String imageFile = iconName + ".png";
                    if (fileExistance(imageFile)) {
                        Log.i("Asych Activity", "Load from local storage");
                        FileInputStream fis = null;
                        try {
                            //getBaseContext().getFileStreamPath(imageFile);
                            String imageFilePath = getBaseContext().getFilesDir() + "/" + imageFile;
                            Log.i("Local Storage Path", imageFilePath);
                            fis = new FileInputStream(imageFilePath);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        currImage = BitmapFactory.decodeStream(fis);

                    } else {
                        Log.i("Asych Activity", "Download from internet");
                        String url = "http://openweathermap.org/img/w/" + iconName + ".png";
                        currImage = getImage(url);
                        saveImage(currImage, iconName);
                    }
                    publishProgress(100);
                } else {
                    skip(parser);
                }
            }
        }

        private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }

        private Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        private Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

        private void saveImage(Bitmap bm, String iconName) throws IOException {
            Bitmap image = bm;
            FileOutputStream outputStream = null;
            try {
                outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
            outputStream.flush();
            outputStream.close();
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
            //return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ProgressBar pb = (ProgressBar) findViewById(R.id.weatherBar);
            pb.setProgress(values[0]);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView currTemp = (TextView) findViewById(R.id.currentTemp);
            currTemp.setText("Current Temperature:" + this.currTemperatuer);

            TextView minTemp = (TextView) findViewById(R.id.minTemp);
            minTemp.setText("Lowest Temperature:" + this.minTemperatuer);

            TextView maxTemp = (TextView) findViewById(R.id.maxTemp);
            maxTemp.setText("Highest Temperature:" + this.maxTemperatuer);

            ImageView imgWeather = (ImageView) findViewById(R.id.currentWeather);
            imgWeather.setImageBitmap(currImage);

            ProgressBar pb = (ProgressBar) findViewById(R.id.weatherBar);
            pb.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ProgressBar pb = (ProgressBar) findViewById(R.id.weatherBar);
        pb.setVisibility(View.VISIBLE);
        new ForecastQuery().execute("");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


}
