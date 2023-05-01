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
import android.widget.TextView;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.controller.EnemyController;
import edu.utsa.cs3443.dungeon.model.Enemy;
import edu.utsa.cs3443.dungeon.model.Map;

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

        // Get enemy layout
        Enemy enemy = intent.getSerializableExtra("EXTRA_ENEMY_PUSH_ENEMY", Enemy.class);
        enemy.generate(this);

        // Setup enemy layout
        TableLayout tableLayout = enemy.getLayout();

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
        mainLayout.addView(enemy.getLayout(), 0);

        LinearLayout buttonLayout = findViewById(R.id.ENEMY_button_layout);

        // TODO (Juan): REMOVE or finish
        EnemyController enemyController = new EnemyController(this);

        Button backButton = findViewById(R.id.ENEMY_button_back);
        backButton.setOnClickListener(enemyController);

        Button fightButton = findViewById(R.id.ENEMY_button_fight);
        fightButton.setOnClickListener(enemyController);
        
        Map map = new Map(this);
        ArrayList<Enemy> enemyList = map.getEnemyList();
        String line = enemy.getName() + " \nHealth: " + enemy.getMaxHP() + " \nAttack: " + enemy.getAttack();
        
        TextView textView = findViewById(R.id.ENEMY_info_text);
        textView.setText(line);

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

} // class EnemyActivity
