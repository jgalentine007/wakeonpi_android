package jgalentine007.wakeonpi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.preference.PreferenceManager;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private MyTwitter twit;
    private final Context actContext = this;

    // preferences and consts
    private SharedPreferences prefs;
    public static final String PREF_consumerKey = "consumerKey";
    public static final String PREF_consumerSecret = "consumerSecret";
    public static final String PREF_token = "token";
    public static final String PREF_tokenSecret = "tokenSecret";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(actContext);

        // prepare twitter
        twit = new MyTwitter(
                prefs.getString(PREF_consumerKey, ""),
                prefs.getString(PREF_consumerSecret, ""),
                prefs.getString(PREF_token, ""),
                prefs.getString(PREF_tokenSecret, "")
        );

        twit.init();

        // prepare computers ListView
        String data = "pc1,pc2";
        final List<String> items = Arrays.asList(data.split("\\s*,\\s*"));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        final ListView lvComputers = (ListView) findViewById(R.id.lvComputers);
        lvComputers.setAdapter(adapter);

        // long click event handler
        lvComputers.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        String message = "WOL " + parent.getItemAtPosition(position);
                        twit.sendMessage(message);
                        Toast.makeText(getApplicationContext(), "Trying to wakeup.", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public void Settings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void Refresh(){

    }

    public void About(){
        Toast.makeText(getApplicationContext(), "The secret to humor is surprise.", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Refresh:
                Refresh();
                break;
            case R.id.Settings:
                Settings();
                break;
            case R.id.About:
                About();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}