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

import java.util.ArrayList;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.controller.EnemyController;
import edu.utsa.cs3443.dungeon.model.Enemy;
import edu.utsa.cs3443.dungeon.model.Entity;
import edu.utsa.cs3443.dungeon.model.Player;
import edu.utsa.cs3443.dungeon.model.Map;

/**
 */
public class EnemyActivity extends AppCompatActivity
{
    public static final int RESULT_WIN = 420;
    public static final int RESULT_LOSE = 69;

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
        final Player player = intent.getSerializableExtra("EXTRA_PLAYER_PUSH_PLAYER", Player.class);

        // Get enemy
        final Enemy enemy = intent.getSerializableExtra("EXTRA_ENEMY_PUSH_ENEMY", Enemy.class);
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
                textView.setTextColor(this.getColorStateList(R.color.teal_700));
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

        LinearLayout buttonLayout = findViewById(R.id.ENEMY_button_layout);

        EnemyController enemyController = new EnemyController(this, enemy);

        Button backButton = findViewById(R.id.ENEMY_button_back);
        backButton.setOnClickListener(enemyController);
        Button fightButton = findViewById(R.id.ENEMY_button_fight);
        fightButton.setOnClickListener(enemyController);

        /*
        String line = enemy.getName() + " \nHealth: " + enemy.getHP();// + " \nAttack: " + enemy.getAttack();
        String line1 = "Player" + " \nHealth: " + player.getHP();// + " \nAttack: " + player.getAttack();

        TextView textView = findViewById(R.id.ENEMY_info_text);
        textView.setText(line);
        TextView textView1 = findViewById(R.id.PLAYER_info_text);
        textView1.setText(line1);
         */
        displayEnemyAndPlayer(enemy, player);


        /*Button button = new Button(this);
        button.setTag(String.valueOf("Back"));
        button.setLayoutParams(new LinearLayout.LayoutParams(
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200.f, metrics)),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75.f, metrics))));
        button.setGravity(Gravity.CENTER);
        button.setBackgroundTintList(getColorStateList(R.color.purple_700));
        button.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        button.setTextColor(getColorStateList(R.color.white));
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.f);
        button.setText("text");
        button.setOnClickListener(enemyController);

        // Add button to button layout
        buttonLayout.addView(button);*/
    }

    public void displayEnemyAndPlayer(Enemy enemy, Player player){
        String line = enemy.getName() + " \nHealth: " + enemy.getHP();// + " \nAttack: " + enemy.getAttack();
        String line1 = "Player" + " \nHealth: " + player.getHP();// + " \nAttack: " + player.getAttack();

        TextView textView = findViewById(R.id.ENEMY_info_text);
        textView.setText(line);
        TextView textView1 = findViewById(R.id.PLAYER_info_text);
        textView1.setText(line1);
    }


} // class EnemyActivity
