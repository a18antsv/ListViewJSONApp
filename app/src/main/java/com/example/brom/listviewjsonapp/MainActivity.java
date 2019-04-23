package com.example.brom.listviewjsonapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Mountain> mountainArrayList = new ArrayList<>();
    private MountainAdapter adapter;
    private ListView listView;

    public static final String
            MOUNTAIN_ID = "MOUNTIAN_ID",
            MOUNTAIN_NAME = "MOUNTAIN_NAME",
            MOUNTAIN_LOCATION = "MOUNTAIN_LOCATION",
            MOUNTAIN_HEIGHT = "MOUNTAIN_HEIGHT",
            MOUNTAIN_IMAGE = "MOUNTAIN_IMAGE",
            MOUNTAIN_ARTICLE = "MOUNTAIN_ARTICLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchData().execute();

        listView = (ListView) findViewById(R.id.my_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), mountainArrayList.get(i).getInfo(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MountainDetailsActivity.class);
                intent.putExtra(MOUNTAIN_ID, mountainArrayList.get(i).getId());
                intent.putExtra(MOUNTAIN_NAME, mountainArrayList.get(i).getName());
                intent.putExtra(MOUNTAIN_LOCATION, mountainArrayList.get(i).getLocation());
                intent.putExtra(MOUNTAIN_HEIGHT, mountainArrayList.get(i).getHeight());
                intent.putExtra(MOUNTAIN_IMAGE, mountainArrayList.get(i).getImgURL());
                intent.putExtra(MOUNTAIN_ARTICLE, mountainArrayList.get(i).getArticleURL());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
            mountainArrayList.clear();
            new FetchData().execute();
            Toast.makeText(getApplicationContext(), "List refreshed", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class FetchData extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a Java string.
            String jsonStr = null;

            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }
        @Override
        protected void onPostExecute(String o) {
            // This code executes after we have received our data. The String object o holds
            // the un-parsed JSON string or is null if we had an IOException during the fetch.
            super.onPostExecute(o);

            try {
                JSONArray mountains = new JSONArray(o);

                for(int i = 0; i < mountains.length(); i++) {
                    JSONObject mountain = mountains.getJSONObject(i);
                    JSONObject auxdata = new JSONObject(mountain.getString("auxdata"));

                    Mountain m = new Mountain(
                        Integer.parseInt(mountain.getString("ID")),
                        mountain.getString("name"),
                        mountain.getInt("size"),
                        mountain.getString("location"),
                        auxdata.getString("img"),
                        auxdata.getString("url")
                    );
                    mountainArrayList.add(m);

                    /*
                        --Alternative way without arguments according to empty constructor--
                        Mountain m = new Mountain();
                        m.setId(Integer.parseInt(mountain.getString("id")));
                        m.setName(mountain.getString("name"));
                        m.setHeight(mountain.getInt("size"));
                        m.setLocation(mountain.getString("location"));
                        m.setImgURL(auxdata.getString("img"));
                        m.setArticleURL(auxdata.getString("url"));
                    */
                }
            } catch(JSONException e) {
                throw new RuntimeException(e);
            }

            adapter = new MountainAdapter(getApplicationContext(), R.layout.list_item, mountainArrayList);
            listView.setAdapter(adapter);
        }
    }
}

