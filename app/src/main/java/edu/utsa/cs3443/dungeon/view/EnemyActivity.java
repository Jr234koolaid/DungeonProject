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

import java.util.Locale;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.controller.EnemyController;
import edu.utsa.cs3443.dungeon.model.Enemy;
import edu.utsa.cs3443.dungeon.model.Entity;
import edu.utsa.cs3443.dungeon.model.Player;

/**
 */
public class EnemyActivity extends AppCompatActivity
{
    /**
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState)
    {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_enemy);

        // Get context resources
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Intent intent = getIntent();

        // Get player
        Player player = Player.getInstance();

        // Get enemy
        final Enemy enemy = (Enemy)intent.getSerializableExtra("EXTRA_ENEMY_PUSH_ENEMY");
        final char[][] enemyCharacter = enemy.getLargeCharacter();

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
                //TODO fix this too
                //textView.setTextColor(this.getColorStateList(R.color.teal_700));
                textView.setTextColor(0xFF03DAC5);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19.f);
                textView.setText(String.valueOf(enemyCharacter[i][j]));

                // Add columns to row
                row.addView(textView);
            }

            // Add row to table
            tableLayout.addView(row);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.topMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.f, metrics));

        tableLayout.setId(R.id.ENEMY_enemy);
        tableLayout.setLayoutParams(params);
        tableLayout.setPadding(
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.f, metrics)));
        tableLayout.setBackground(AppCompatResources.getDrawable(this, R.drawable.entity_border));

        // Add enemy to layout
        LinearLayout mainLayout = findViewById(R.id.ENEMY_main_layout);
        mainLayout.addView(tableLayout, 0);

        EnemyController enemyController = new EnemyController(this);

        Button backButton = findViewById(R.id.ENEMY_button_back);
        backButton.setOnClickListener(enemyController);

        Button fightButton = findViewById(R.id.ENEMY_button_fight);
        fightButton.setOnClickListener(enemyController);

        // Get enemy info text
        TextView enemyInfoText = findViewById(R.id.ENEMY_info_text);
        enemyInfoText.setText(String.format(Locale.getDefault(), "%s \nHealth: %d", enemy.getName(), enemy.getHP()));

        // Get player info text
        TextView playerInfoText = findViewById(R.id.PLAYER_info_text);
        playerInfoText.setText(String.format(Locale.getDefault(), "%s \nHealth: %d", player.getName(), player.getHP()));
    }

} // class EnemyActivity
