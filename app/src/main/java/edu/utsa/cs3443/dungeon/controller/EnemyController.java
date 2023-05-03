package edu.utsa.cs3443.dungeon.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.dungeon.model.Map;
import edu.utsa.cs3443.dungeon.model.Enemy;
import edu.utsa.cs3443.dungeon.model.Player;
import edu.utsa.cs3443.dungeon.view.EnemyActivity;

/**
 */
public class EnemyController implements View.OnClickListener
{
    private EnemyActivity       m_activity; //
    private Enemy                   m_enemy;

    /**
     */
    public EnemyController(EnemyActivity _activity, Enemy _enemy)
    {
        m_activity = _activity;
        m_enemy = _enemy;
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

            case "Fight": {
                //Enemy.enemyFight();
                Player p = Player.getInstance();
                int attack;
                int damage;


                //get player's item's attack
                attack = p.getWeapon().attack();
                //apply damage to enemy
                damage = m_enemy.takeDamage(attack);
                //display attack toast
                displayAttackToast(p.getName(), damage, m_enemy.getName());
                //update enemy health display
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                m_activity.displayEnemyStats(m_enemy);
                if (m_enemy.getHP() <= m_enemy.getMinHP()){
                    //you win
                    Toast.makeText(m_activity,"You defeated: " + m_enemy.getName(), Toast.LENGTH_SHORT).show();
                    m_activity.setResult(EnemyActivity.RESULT_WIN);
                    m_activity.finish();
                } else {
                    //get enemy's attack
                    attack = m_enemy.attack();
                    //apply damage to player
                    damage = p.takeDamage(attack);
                    //display attack toast
                    displayAttackToast(m_enemy.getName(), damage, p.getName());
                    //update player health display
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    m_activity.displayPlayerStats(p);

                    if (p.getHP() <= p.getMinHP()){
                        //you lose
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(m_activity,"YOU DIED", Toast.LENGTH_LONG).show();
                        m_activity.setResult(EnemyActivity.RESULT_LOSE);
                        m_activity.finish();
                    }
                }

            }break;

            default:
                break;
        }
    }

    public void displayAttackToast(String attacker, int damageDealt, String victim){
        if (damageDealt == -1){
            Toast.makeText(m_activity, victim + " dodged " + attacker + "'s attack!", Toast.LENGTH_SHORT).show();
        } else if (damageDealt == 0) {
            Toast.makeText(m_activity, attacker + "'s attack missed " + victim + "!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(m_activity, attacker + " hit " + victim + " for " + damageDealt + " damage!", Toast.LENGTH_SHORT).show();
        }
    }

} // class EnemyController
