package edu.utsa.cs3443.dungeon.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.controller.MapController;
import edu.utsa.cs3443.dungeon.model.Floor;

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
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Intent intent = getIntent();

        // TODO (Juan): Replace with real info

        // Load level
        // final int mapLevel = intent.getInteger("EXTRA_MAP_LEVEL", 1);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.topMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.f, metrics));

        Floor floor = new Floor(this);
        floor.setId(R.id.MAP_map);
        floor.setFloorLevel(1);
        floor.setLayoutParams(params);
        floor.setPadding(
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)));
        floor.setBackground(AppCompatResources.getDrawable(this, R.drawable.map_border));
        try
        {
            // TODO (Juan): Change with intent
            floor.loadMaps(1);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        floor.generateMapLayout();

        LinearLayout mainLayout = findViewById(R.id.MAP_main_layout);
        mainLayout.addView(floor, 0);

        MapController mapController = new MapController(this);

        Button interactButton = findViewById(R.id.interact_button);
        interactButton.setOnClickListener(mapController);

        Button leftButton = findViewById(R.id.left_button);
        leftButton.setOnClickListener(mapController);

        Button rightButton = findViewById(R.id.right_button);
        rightButton.setOnClickListener(mapController);

        Button upButton = findViewById(R.id.up_button);
        upButton.setOnClickListener(mapController);

        Button downButton = findViewById(R.id.down_button);
        downButton.setOnClickListener(mapController);
    }

} // class MapActivity
