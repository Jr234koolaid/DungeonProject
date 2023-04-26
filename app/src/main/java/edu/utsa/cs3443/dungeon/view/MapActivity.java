package edu.utsa.cs3443.dungeon.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.model.Level;

/**
 */
public class MapActivity extends AppCompatActivity {

    /**
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Get context resources
        Intent intent = getIntent();

        // TODO (Juan): Replace with real info

        // Load level
        // final int mapLevel = intent.getInteger("EXTRA_MAP_LEVEL", 1);

        Level level = new Level(this);
        level.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        level.load(1, getAssets());
        level.generateMapLayout();

        LinearLayout mainLayout = findViewById(R.id.MAP_main_layout);
        mainLayout.addView(level, 0);
    }

} // class MapActivity
