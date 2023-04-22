package edu.utsa.cs3443.dungeon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

/**
 */
public class MainActivity extends AppCompatActivity {

    /**
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newGame = findViewById(R.id.mm_newGame);
        Button continueGame = findViewById(R.id.mm_continueGame);

        // newGame.setOnClickListener(new Some_Controller());
        // continueGame.setOnClickListener(new Some_Controller());
    }

} // class MainActivity
