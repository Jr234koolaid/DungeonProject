package edu.utsa.cs3443.dungeon.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.model.Door;
import edu.utsa.cs3443.dungeon.model.Enemy;
import edu.utsa.cs3443.dungeon.model.Item;
import edu.utsa.cs3443.dungeon.model.Map;
import edu.utsa.cs3443.dungeon.model.Player;
import edu.utsa.cs3443.dungeon.view.EnemyActivity;
import edu.utsa.cs3443.dungeon.view.ItemActivity;

/**
 */
public class MapController implements View.OnClickListener
{
    private final AppCompatActivity               m_activity;             //
    private final ActivityResultLauncher<Intent>  m_enemyActivityStart;   //
    private final ActivityResultLauncher<Intent>  m_itemActivityStart;    //

    /**
     */
    public MapController(AppCompatActivity _activity)
    {
        m_activity = _activity;
        m_enemyActivityStart = m_activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->
                {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        // Get map
                        Map map = m_activity.findViewById(R.id.MAP_map);

                        // TODO (Juan): Handle the Intent
                        Intent enemyData = result.getData();

                        // Check if player won
                        final boolean playerWon = enemyData.getBooleanExtra("EXTRA_ENEMY_POP_WON", false);
                        if (playerWon)
                        {
                            // Get player to remove
                            Enemy enemy = (Enemy)enemyData.getSerializableExtra("EXTRA_ENEMY_POP_ENEMY");

                            // Remove the enemy from the screen
                            map.removeEnemy(enemy);
                        }
                        else
                        {
                            // Destroy the player
                            Player.destroy();

                            // Exit
                            Intent intent = new Intent();

                            m_activity.setResult(Activity.RESULT_CANCELED, intent);
                            m_activity.finish();
                        }

                        // Generate map layout
                        map.generate();
                    }
                });
        m_itemActivityStart = m_activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->
                {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        // Get map
                        Map map = m_activity.findViewById(R.id.MAP_map);

                        // Check if we remove item
                        Intent itemData = result.getData();

                        final boolean remove = itemData.getBooleanExtra("EXTRA_ITEM_POP_REMOVE", false);
                        if (remove)
                        {
                            // Remove item
                            final Item item = (Item)itemData.getSerializableExtra("EXTRA_ITEM_POP_ITEM");
                            map.removeItem(item);

                            // Get player
                            Player.getInstance().addItem(item);
                        }

                        // Generate map layout
                        map.generate();
                    }
                });
    }

    /**
     */
    @Override
    public void onClick(View _view)
    {
        // Get map
        Map map = m_activity.findViewById(R.id.MAP_map);

        // Update player movement
        final Player player = Player.getInstance();

        final String tag = String.valueOf(_view.getTag());
        switch(tag)
        {
            // Move player left
            case "Left":
                map.updatePlayer((player.getX() - 1), player.getY());
                break;

            // Move player right
            case "Right":
                map.updatePlayer((player.getX() + 1), player.getY());
                break;

            // Move player up
            case "Up":
                map.updatePlayer(player.getX(), (player.getY() - 1));
                break;

            // Move player down
            case "Down":
                map.updatePlayer(player.getX(), (player.getY() + 1));
                break;

            // Check interactions with entities
            case "Interact":
                for (final Door door : map.getDoorList())
                {
                    if (player.canInteract(door, map))
                    {
                        // Finish this activity
                        Intent intent = new Intent();
                        intent.putExtra("EXTRA_MAP_POP_FLOOR", map.getNextFloor());
                        intent.putExtra("EXTRA_MAP_POP_MAP", map.getNextMap());

                        m_activity.setResult(Activity.RESULT_OK, intent);
                        m_activity.finish();
                    }
                }
                for (final Enemy enemy : map.getEnemyList())
                {
                    if (player.canInteract(enemy, map))
                    {
                        Intent intent = new Intent(m_activity, EnemyActivity.class);
                        intent.putExtra("EXTRA_ENEMY_PUSH_ENEMY", enemy);

                        // Launch new activity
                        m_enemyActivityStart.launch(intent);
                        break;
                    }
                }
                for (final Item item : map.getItemList())
                {
                    if (player.canInteract(item, map))
                    {
                        Intent intent = new Intent(m_activity, ItemActivity.class);
                        intent.putExtra("EXTRA_ITEM_PUSH_ITEM", item);

                        // Launch new activity
                        m_itemActivityStart.launch(intent);
                        break;
                    }
                }

            default:
                break;
        }

        // Generate map layout
        map.generate();
    }

} // class MapController
