package edu.utsa.cs3443.dungeon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 */
public class ItemActivity extends AppCompatActivity {

    /**
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_item);

        TextView foundWeapon = findViewById(R.id.im_weapon);
        Button buttonYes = findViewById(R.id.im_yes);
        Button buttonNo = findViewById(R.id.im_no);

        // foundWeapon.setText(???);
        // buttonYes.setOnClickListener(new Some_Controller_2());
        // buttonNo.setOnClickListener(new Some_Controller_2());
    }

} // class ItemActivity
