package org.gradle.epicture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String[] cheeses = {
            "Parmesan",
            "Ricotta",
            "Fontina",
            "Mozzarella",
            "Cheddar"
    };
    ArrayAdapter<String> cheeseAdapter =
            new ArrayAdapter<String>(this,
                    R.layout.list_item,
                    R.id.cheese_name,
                    cheeses
            );
    ListView cheeseList = new ListView(this);
    setContentView(cheeseList);
        cheeseList.setAdapter(cheeseAdapter);

    }
}