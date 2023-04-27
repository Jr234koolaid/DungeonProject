package edu.utsa.cs3443.dungeon.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
        switch(String.valueOf(_view.getTag()))
        {
            case "Back":
            {
                Intent intent = new Intent();
                intent.putExtra("EXTRA_ENEMY_POP_CODE", 1);
                m_activity.setResult(Activity.RESULT_OK, intent);
                m_activity.finish();

            } break;

            default:
                break;
        }
    }

} // class EnemyController
