package edu.utsa.cs3443.dungeon.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.dungeon.model.Item;

/**
 */
public class ItemController implements View.OnClickListener
{
    private final AppCompatActivity               m_activity; //

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
        // Get item
        Intent intent = m_activity.getIntent();
        Item item = (Item) intent.getSerializableExtra("EXTRA_ITEM_PUSH_ITEM");

        // Check tag
        switch(String.valueOf(_view.getTag()))
        {
            // Player rejects the item
            case "Reject":
            {
                Intent data = new Intent();

                m_activity.setResult(Activity.RESULT_CANCELED, intent);
                m_activity.finish();

            } break;

            // Player accepts the item
            case "Accept":
            {
                Intent data = new Intent();
                data.putExtra("EXTRA_ITEM_POP_REMOVE", true);
                data.putExtra("EXTRA_ITEM_POP_ITEM", item);

                m_activity.setResult(Activity.RESULT_OK, data);
                m_activity.finish();
            }

            default:
                break;
        }
    }

} // class ItemController
