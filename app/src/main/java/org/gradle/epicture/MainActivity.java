package org.gradle.epicture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.connection).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        setContentView(R.layout.activity_after_log);
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
