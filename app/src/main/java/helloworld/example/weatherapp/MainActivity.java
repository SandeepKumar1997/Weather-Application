package helloworld.example.weatherapp;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity  {
    TextView editText;
    Button button;
    TextView result;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                AlertDialog.Builder my = new AlertDialog.Builder(this);
                my.setTitle("Exit");
                my.setMessage("Are you sure ?");
                my.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                my.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                my.setCancelable(false);
                my.show();

                break;

        }
            return true;

    }
    public void speak(View view)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi Say Something ");
        try {
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if(resultCode==RESULT_OK && null!=data)
                {
                    ArrayList<String> res=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editText.setText(res.get(0));
                }
                break;
        }
    }



    public void search(View view)
    {
        editText=findViewById(R.id.editText);
        button=findViewById(R.id.button);
        result=findViewById(R.id.resultview);
        String cName=editText.getText().toString();
        String content;
        back b1=new back();
        try {
            content=b1.execute("https://openweathermap.org/data/2.5/weather?q="+cName+"&appid=b6907d289e10d714a6e88b30761fae22").get();
            //To check data is retrieve succesfully
            Log.i("content", content);
            //JSON
            JSONObject jsonObject=new JSONObject(content);
            String weatherData=jsonObject.getString("weather");
            String mainTemperature=jsonObject.getString("main");
            String humd=jsonObject.getString("main");
            String mintemp=jsonObject.getString("main");
            String maxtemp=jsonObject.getString("main");
            double visibility;
            Log.i("WeatherData",weatherData);
            //Weather data is in array
            JSONArray array=new JSONArray(weatherData);
            String main="";
            String description="";
            String temperature="";
            String humidity="";
            String mintemperature="";
            String maxtemperature="";
            for(int i=0;i<array.length();i++)
            {
                JSONObject weatherpart=array.getJSONObject(i);
                main=weatherpart.getString("main");
                description=weatherpart.getString("description");
            }
            JSONObject mainpart=new JSONObject(mainTemperature);
            JSONObject mainhum=new JSONObject(humd);
            JSONObject mainmintemp=new JSONObject(mintemp);
            JSONObject mainmaxtemp=new JSONObject(maxtemp);
            temperature=mainpart.getString("temp");
            humidity=mainhum.getString("humidity");
            maxtemperature=mainmaxtemp.getString("temp_max") ;
            mintemperature=mainmintemp.getString("temp_min");
            visibility = Double.parseDouble(jsonObject.getString("visibility"));
            int vis=(int) visibility/1000;
            Log.i("Temperature",temperature);
           /* Log.i("main",main);
            Log.i("description",description);*/
           //How we will show the result
            String resultText="Main : "+main+
                    "\nDescription : "+description+
                    "\nTemperature : "+temperature+"°C"+
                    "\nVisibility : "+vis+" KM"+
                    "\nHumidity : "+humidity+"%"+
                    "\nMinimum Temperature : "+mintemperature+"°C"+
                    "\nMaximum Temperature : "+maxtemperature+"°C";
            result.setText(resultText);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String content;
        back b1=new back();
        try {
            content=b1.execute("https://openweathermap.org/data/2.5/weather?q=Dubai&appid=b6907d289e10d714a6e88b30761fae22").get();
            //To check data is retrieve succesfully
             Log.i("content", content);
             //JSON
            JSONObject jsonObject=new JSONObject(content);
            String weatherData=jsonObject.getString("weather");
            Log.i("WeatherData",weatherData);
            //Weather data is in array
            JSONArray array=new JSONArray(weatherData);
            String main="";
            String description="";
            for(int i=0;i<array.length();i++)
            {
                JSONObject weatherpart=array.getJSONObject(i);
                main=weatherpart.getString("main");
                description=weatherpart.getString("description");
            }
            Log.i("main",main);
            Log.i("description",description);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
