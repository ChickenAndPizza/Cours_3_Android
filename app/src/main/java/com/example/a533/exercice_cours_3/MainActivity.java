package com.example.a533.exercice_cours_3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    String bingUrl = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img =  findViewById(R.id.ivBing);
        SetListener();
    }

    private void SetListener()
    {
        findViewById(R.id.btnDownload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.pbRoll).setVisibility(View.VISIBLE);
                OnloadImage("https://2.bp.blogspot.com/-A5WrBUgrrdo/WUTJaZWzsKI/AAAAAAAABGw/Ih-gOWCh1Fs99Fa1CMrKnkKF5XGzlG-5gCPcBGAYYCw/s320/ANDROID.PNG");
            }
        });

        findViewById(R.id.btnConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.pbRoll).setVisibility(View.VISIBLE);
                OnloadBingImage();
            }
        });
    }

    private void OnloadBingImage()
    {
        bingImageLinkDownloader imgB = new bingImageLinkDownloader();
        try{
            imgB.execute(bingUrl);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void OnloadImage(String imageUrl)
    {
        ImageDownloader imgD = new ImageDownloader();
        try{
            imgD.execute(imageUrl);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try{
                URL url = new URL(urls[0]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                return imageBitmap;
            }
            catch (MalformedURLException ex){
                ex.printStackTrace();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            img.setImageBitmap(bitmap);
            findViewById(R.id.pbRoll).setVisibility(View.GONE);
        }
    }

    public class bingImageLinkDownloader extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls) {
            BufferedReader reader = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line=reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0)
                    return null;

                return buffer.toString();

            }
            catch (MalformedURLException ex){
                ex.printStackTrace();
                return null;
            }
            catch (IOException ex){
                ex.printStackTrace();
                return null;
            }
            finally{
                if(connection != null){
                    connection.disconnect();
                }
                if(reader != null) {
                    try{
                        reader.close();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }

                }
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if(response != null){
                JSONObject jsonRoot = null;
                try{
                    jsonRoot = new JSONObject(response);
                    JSONArray array = jsonRoot.getJSONArray("images");
                    String urlImage = array.getJSONObject(0).getString("url");
                    OnloadImage("https://Bing.com" + urlImage);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}