package edu.utsa.cs3443.dungeon.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.dungeon.view.MapActivity;

/**
 */
public class MainController implements View.OnClickListener
{
    private AppCompatActivity               m_activity;         //
    private ActivityResultLauncher<Intent>  m_mapActivityStart; //

    /**
     */
    public MainController(AppCompatActivity _activity)
    {
        m_activity = _activity;
        m_mapActivityStart = m_activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->
                {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent data = result.getData();

                        // Launch new map
                        Intent intent = new Intent(m_activity, MapActivity.class);
                        intent.putExtra("EXTRA_MAP_PUSH_FLOOR", data.getIntExtra("EXTRA_MAP_POP_FLOOR", 0));
                        intent.putExtra("EXTRA_MAP_PUSH_MAP", data.getIntExtra("EXTRA_MAP_POP_MAP", 0));

                        m_mapActivityStart.launch(intent);
                    }
                });
    }

    /**
     */
    @Override
    public void onClick(View _view)
    {
        // Check tag
        switch (String.valueOf(_view.getTag()))
        {
            // Start from the first map
            case "New":
            {
                Intent intent = new Intent(m_activity, MapActivity.class);
                intent.putExtra("EXTRA_MAP_PUSH_FLOOR", 1);
                intent.putExtra("EXTRA_MAP_PUSH_MAP", 1);

                m_mapActivityStart.launch(intent);

            } break;

            // Continue from where user left off (not implemented yet)
            case "Continue":
                Toast.makeText(_view.getContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

} // class MainController
