package edu.utsa.cs3443.dungeon.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.dungeon.model.Map;
import edu.utsa.cs3443.dungeon.model.Enemy;

/**
 */
public class EnemyController implements View.OnClickListener
{
    private AppCompatActivity       m_activity; //

    /**
     */
    public EnemyController(AppCompatActivity _activity)
    {
        m_activity = _activity;
    }

    /**
     */
    @Override
    public void onClick(View _view)
    {
        // Check tag
        switch(String.valueOf(_view.getTag()))
        {
            // TODO (Juan): Handle cases
            case "Back":
            {
                Intent intent = new Intent();
                intent.putExtra("EXTRA_ENEMY_POP_CODE", 1);
                m_activity.setResult(Activity.RESULT_OK, intent);
                m_activity.finish();

            } break;

            case "Fight":
            {
                Enemy.enemyFight();
                m_activity.finish();

            }break;

            default:
                break;
        }
    }

} // class EnemyController
