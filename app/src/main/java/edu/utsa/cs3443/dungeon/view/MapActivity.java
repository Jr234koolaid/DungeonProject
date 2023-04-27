package edu.utsa.cs3443.dungeon.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.controller.MapController;
import edu.utsa.cs3443.dungeon.model.Map;

/**
 */
public class MapActivity extends AppCompatActivity
{
    /**
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Get context resources
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Intent intent = getIntent();

        // TODO (Juan): Replace with real info

        // final int floorLevel = intent.getInteger("EXTRA_MAP_FLOOR_LEVEL", 1);
        // final int mapLevel = intent.getInteger("EXTRA_MAP_MAP_LEVEL", 1);

        // Setup map layout
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.topMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.f, metrics));

        // Create new map
        Map map = new Map(this);
        map.setId(R.id.MAP_map);
        map.setLayoutParams(params);
        map.setPadding(
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)));
        map.setBackground(AppCompatResources.getDrawable(this, R.drawable.map_border));

        try
        {
            AssetManager assetManager = getAssets();

            // TODO (Juan): Change with intent
            // Get floor directories
            final String rootDirName = ("floor_" + String.valueOf(1));
            final String mapDirName = (rootDirName + "/" + "map_" + String.valueOf(1));
            final String enemyDirName = (mapDirName + "/" + "enemy");
            final String itemDirName = (mapDirName + "/" + "item");

            // Load all map data
            map.load(mapDirName, assetManager);
            map.loadPlayer(mapDirName, assetManager);
            map.loadEnemies(enemyDirName, assetManager);
            map.loadItems(itemDirName, assetManager);
        }
        catch (IOException _e)
        {
            throw new RuntimeException(_e);
        }

        // Generate map
        map.generate();

        // Add map to layout
        LinearLayout mainLayout = findViewById(R.id.MAP_main_layout);
        mainLayout.addView(map, 0);

        // Create new map controller
        MapController mapController = new MapController(this);

        // Get interact button
        Button interactButton = findViewById(R.id.interact_button);
        interactButton.setOnClickListener(mapController);

        // Get left button
        Button leftButton = findViewById(R.id.left_button);
        leftButton.setOnClickListener(mapController);

        // Get right button
        Button rightButton = findViewById(R.id.right_button);
        rightButton.setOnClickListener(mapController);

        // Get up button
        Button upButton = findViewById(R.id.up_button);
        upButton.setOnClickListener(mapController);

        // Get down button
        Button downButton = findViewById(R.id.down_button);
        downButton.setOnClickListener(mapController);
    }

    /**
     */
    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data)
    {
        super.onActivityResult(_requestCode, _resultCode, _data);

        // Get map
        Map map = findViewById(R.id.MAP_map);
        map.generate();
    }

} // class MapActivity
