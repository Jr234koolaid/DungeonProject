package edu.utsa.cs3443.dungeon.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.controller.ItemController;
import edu.utsa.cs3443.dungeon.model.Entity;
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

        // Get context resources
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Intent intent = getIntent();

        // Get item
        final Item item = (Item)intent.getSerializableExtra("EXTRA_ITEM_PUSH_ITEM");
        final char[][] itemCharacter = item.getLargeCharacter();

        // Create new table layout
        TableLayout tableLayout = new TableLayout(this);

        // Create table
        final float textWidth = 10.f;
        final float rowWidth = (Entity.MAX_SPRITE_WIDTH * textWidth);

        final int rowLayoutWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rowWidth, metrics));
        final int textLayoutWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textWidth, metrics));
        final int textLayoutHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30.f, metrics));

        for (int i = 0; i < Entity.MAX_SPRITE_HEIGHT; i++)
        {
            // Create row
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(rowLayoutWidth, TableLayout.LayoutParams.WRAP_CONTENT));

            // Create text columns
            for (int j = 0; j < Entity.MAX_SPRITE_WIDTH; j++)
            {
                TextView textView = new TextView(this);
                textView.setLayoutParams(new TableRow.LayoutParams(textLayoutWidth, textLayoutHeight));
                textView.setTextAlignment(android.view.View.TEXT_ALIGNMENT_CENTER);
                textView.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                //TODO: fix this as well
                //textView.setTextColor(this.getColorStateList(R.color.teal_700));
                textView.setTextColor(0xFF03DAC5);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19.f);
                textView.setText(String.valueOf(itemCharacter[i][j]));

                // Add columns to row
                row.addView(textView);
            }

            // Add row to table
            tableLayout.addView(row);
        }

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
        tableLayout.setBackground(AppCompatResources.getDrawable(this, R.drawable.entity_border));

        // Add item to layout
        LinearLayout mainLayout = findViewById(R.id.ITEM_main_layout);
        mainLayout.addView(tableLayout, 0);

        // TODO (Juan): REMOVE or finish
        ItemController itemController = new ItemController(this);

        TextView prompt = findViewById(R.id.ITEM_info_text);
        prompt.setText(item.toString());

        Button acceptButton = findViewById(R.id.ITEM_button_accept);
        acceptButton.setOnClickListener(itemController);

        Button rejectButton = findViewById(R.id.ITEM_button_reject);
        rejectButton.setOnClickListener(itemController);
    }

} // class ItemActivity
