package edu.utsa.cs3443.dungeon.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 */
public class ItemController implements View.OnClickListener
{
    private AppCompatActivity               m_activity;             //

    /**
     */
    public ItemController(AppCompatActivity _activity)
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
            case "Accept":
            case "Reject":
            {
                Intent intent = new Intent();
                intent.putExtra("EXTRA_ITEM_POP_CODE", 1);
                m_activity.setResult(Activity.RESULT_OK, intent);
                m_activity.finish();

            } break;

            default:
                break;
        }
    }

} // class ItemController
