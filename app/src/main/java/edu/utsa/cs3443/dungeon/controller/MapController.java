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
    private AppCompatActivity               m_activity;             //
    private ActivityResultLauncher<Intent>  m_enemyActivityStart;   //
    private ActivityResultLauncher<Intent>  m_itemActivityStart;    //

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

                        // TODO (Juan): Handle the Intent
                        Intent itemData = result.getData();

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
        Player player = new Player(map.getPlayer());
        switch(String.valueOf(_view.getTag()))
        {
            // Move player left
            case "Left":
                player.setPositionX(player.getPositionX() - 1);
                map.updatePlayer(player);
                break;

            // Move player right
            case "Right":
                player.setPositionX(player.getPositionX() + 1);
                map.updatePlayer(player);
                break;

            // Move player up
            case "Up":
                player.setPositionY(player.getPositionY() - 1);
                map.updatePlayer(player);
                break;

            // Move player down
            case "Down":
                player.setPositionY(player.getPositionY() + 1);
                map.updatePlayer(player);
                break;

            // Check interactions with entities
            case "Interact":
                for (final Door door : map.getDoorList())
                {
                    if (player.canInteract(door))
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
                    if (player.canInteract(enemy))
                    {
                        System.out.println("Interacting with enemy: " + enemy.getName());

                        Intent intent = new Intent(m_activity, EnemyActivity.class);
                        intent.putExtra("EXTRA_ENEMY_PUSH_ENEMY", enemy);

                        // Launch new activity
                        m_enemyActivityStart.launch(intent);
                        break;
                    }
                }
                for (final Item item : map.getItemList())
                {
                    if (player.canInteract(item))
                    {
                        System.out.println("Interacting with enemy: " + item.getName());

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
