package edu.utsa.cs3443.dungeon.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.controller.MainController;

/**
 */
public class MainActivity extends AppCompatActivity
{
    /**
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState)
    {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create main controller
        MainController mainController = new MainController(this);

        // Get new game button
        Button newButton = findViewById(R.id.MAIN_new_button);
        newButton.setOnClickListener(mainController);

        // Get continue game button
        //Button continueButton = findViewById(R.id.MAIN_continue_button);
        //continueButton.setOnClickListener(mainController);
    }

} // class MainActivity
