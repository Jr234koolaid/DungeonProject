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
import android.widget.TableLayout;
import android.widget.TextView;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.controller.ItemController;
import edu.utsa.cs3443.dungeon.model.Enemy;
import edu.utsa.cs3443.dungeon.model.Item;

/**
 */
public class ItemActivity extends AppCompatActivity {

    /**
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_item);

        //TextView foundWeapon = findViewById(R.id.im_weapon);
        //Button buttonYes = findViewById(R.id.im_yes);
        //Button buttonNo = findViewById(R.id.im_no);

        // foundWeapon.setText(???);
        // buttonYes.setOnClickListener(new Some_Controller_2());
        // buttonNo.setOnClickListener(new Some_Controller_2());

        // Get context resources
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Intent intent = getIntent();

        // Get item layout
        Item item = intent.getSerializableExtra("EXTRA_ITEM_PUSH_ITEM", Item.class);
        item.generate(this);

        // Setup enemy layout
        TableLayout tableLayout = item.getLayout();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.topMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.f, metrics));

        tableLayout.setId(R.id.ITEM_item);
        tableLayout.setLayoutParams(params);
        tableLayout.setPadding(
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)));
        tableLayout.setBackground(AppCompatResources.getDrawable(this, R.drawable.map_border));

        // Add item to layout
        LinearLayout mainLayout = findViewById(R.id.ITEM_main_layout);
        mainLayout.addView(item.getLayout(), 0);

        // TODO (Juan): REMOVE or finish
        ItemController itemController = new ItemController(this);

        Button acceptButton = findViewById(R.id.ITEM_button_accept);
        acceptButton.setOnClickListener(itemController);

        Button rejectButton = findViewById(R.id.ITEM_button_reject);
        rejectButton.setOnClickListener(itemController);
    }

} // class ItemActivity
