package edu.utsa.cs3443.dungeon.controller;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.model.Enemy;
import edu.utsa.cs3443.dungeon.model.Floor;
import edu.utsa.cs3443.dungeon.model.Item;
import edu.utsa.cs3443.dungeon.model.Map;
import edu.utsa.cs3443.dungeon.model.Player;

/**
 */
public class MapController implements View.OnClickListener
{
    private Activity                m_activity; //

    /**
     */
    public MapController(Activity _activity)
    {
        m_activity = _activity;
    }

    /**
     */
    @Override
    public void onClick(View _view)
    {
        // Get floor
        Floor floor = m_activity.findViewById(R.id.MAP_map);

        // Get map
        Map map = floor.getCurrentMap();

        // Update player movement
        Player player = new Player(map.getPlayer());
        switch(String.valueOf(_view.getTag()))
        {
            // Move player left
            case "Left":
                player.setX(player.getX() - 1);
                map.updatePlayer(player);
                break;

            // Move player right
            case "Right":
                player.setX(player.getX() + 1);
                map.updatePlayer(player);
                break;

            // Move player up
            case "Up":
                player.setY(player.getY() - 1);
                map.updatePlayer(player);
                break;

            // Move player down
            case "Down":
                player.setY(player.getY() + 1);
                map.updatePlayer(player);
                break;

            // Check interactions with entities
            case "Interact":
                for (final Enemy enemy : map.getEnemyList())
                {
                    if (player.canInteract(enemy))
                    {
                        // TODO (Juan): Start the enemy activity here
                        System.out.println("Interacting with enemy: " + enemy.getName());
                        break;
                    }
                }
                for (final Item item : map.getItemList())
                {
                    if (player.canInteract(item))
                    {
                        // TODO (Juan): Start the item activity here
                        System.out.println("Interacting with enemy: " + item.getName());
                        break;
                    }
                }

            default:
                break;
        }

        // Generate map layout
        floor.generateMapLayout();

        LinearLayout mainLayout = m_activity.findViewById(R.id.MAP_main_layout);
        mainLayout.removeView(floor);
        mainLayout.addView(floor, 0);
    }

} // class MapController
