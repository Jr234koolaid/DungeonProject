package edu.utsa.cs3443.dungeon.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import edu.utsa.cs3443.dungeon.R;
import edu.utsa.cs3443.dungeon.model.Map;
import edu.utsa.cs3443.dungeon.model.Enemy;
import edu.utsa.cs3443.dungeon.model.Player;
import edu.utsa.cs3443.dungeon.view.EnemyActivity;

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
        final String tag = String.valueOf(_view.getTag());
        switch(tag)
        {
            // Player decides to not fight the enemy
            case "Back":
            {
                Intent intent = new Intent();

                m_activity.setResult(Activity.RESULT_CANCELED, intent);
                m_activity.finish();

            } break;

            // Player decides to fight the enemy
            case "Fight":
            {
                // Get enemy
                Enemy enemy = m_activity.getIntent().getSerializableExtra("EXTRA_ENEMY_PUSH_ENEMY", Enemy.class);
                final String enemyName = enemy.getName();

                // Get player
                Player player = Player.getInstance();
                final String playerName = player.getName();

                // Player attack
                final int playerAttack = player.getWeapon().attack();
                enemy.setHP(enemy.getHP() - playerAttack);

                // Display attack toast
                DisplayAttackToast(player.getName(), playerAttack, enemy.getName());

                // Update enemy info text
                TextView enemyInfoText = m_activity.findViewById(R.id.ENEMY_info_text);
                enemyInfoText.setText(String.format(Locale.getDefault(), "%s \nHealth: %d", enemyName, enemy.getHP()));

                /*
                //wait 1 second
                //TODO: make them not update at the same time
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
                 */

                // Enemy attack
                final int enemyAttack = enemy.attack();
                player.setHP(player.getHP() - enemyAttack);

                // Display attack toast
                DisplayAttackToast(enemyName, enemyAttack, playerName);

                // Update player info text
                TextView playerInfoText = m_activity.findViewById(R.id.PLAYER_info_text);
                playerInfoText.setText(String.format(Locale.getDefault(), "%s \nHealth: %d", playerName, player.getHP()));

                // Check player's hp first
                if (player.getHP() <= player.getMinHP())
                {
                    // You lose
                    Intent intent = new Intent();
                    intent.putExtra("EXTRA_ENEMY_POP_WON", false);

                    m_activity.setResult(Activity.RESULT_OK, intent);
                    m_activity.finish();
                }

                // Check enemy's hp next
                else if (enemy.getHP() <= enemy.getMinHP())
                {
                    // You win
                    Intent intent = new Intent();
                    intent.putExtra("EXTRA_ENEMY_POP_WON", true);
                    intent.putExtra("EXTRA_ENEMY_POP_ENEMY", enemy);

                    m_activity.setResult(Activity.RESULT_OK, intent);
                    m_activity.finish();
                }

            } break;

            default:
                break;
        }
    }

    /**
     */
    private void DisplayAttackToast(final String _attacker, final int _damageDealt, final String _victim)
    {
        if (_damageDealt == -1)
            Toast.makeText(m_activity, _victim + " dodged " + _attacker + "'s attack!", Toast.LENGTH_SHORT).show();
        else if (_damageDealt == 0)
            Toast.makeText(m_activity, _attacker + "'s attack missed " + _victim + "!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(m_activity, _attacker + " hit " + _victim + " for " + _damageDealt + " damage!", Toast.LENGTH_SHORT).show();
    }

} // class EnemyController
