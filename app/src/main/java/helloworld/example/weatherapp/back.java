package helloworld.example.weatherapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class back extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... address) {
        InputStream inputStream;
        try {
            URL url=new URL(address[0]);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            //To establish connectionn
            httpURLConnection.connect();
            //Retrieve data from URL
           InputStream is=httpURLConnection.getInputStream();
           InputStreamReader isr=new InputStreamReader(is);
            //retrieve data and return it in string
            int data=isr.read();
            String content="";
            char ch;
            while(data!=-1){
                ch=(char)data;
                content = content + ch;
                data=isr.read();
            }
            return content;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
