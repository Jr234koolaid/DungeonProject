package edu.utsa.cs3443.dungeon.model;

import java.util.Random;

import edu.utsa.cs3443.dungeon.model.Map;
import edu.utsa.cs3443.dungeon.model.Player;

/**
 */
public class Enemy extends Entity
{
    /**
     * Defines the Entity class and relevant methods
     * @param _maxHP max hp
     **/
    public Enemy(final String _name, final int _maxHP, final char _smallCharacter, int _attack)
    {
        super(_name, 0, _maxHP, _smallCharacter, _attack);
    }

    /**
     * Copy Constructor
     */
    public Enemy(Entity _enemy)
    {
        super(_enemy.m_name, _enemy.m_minHP, _enemy.m_maxHP, _enemy.m_smallCharacter, _enemy.m_attack);

        m_largeCharacter= _enemy.m_largeCharacter;
        m_hp = _enemy.m_hp;
        m_x = _enemy.m_x;
        m_y = _enemy.m_y;
        m_attack = _enemy.m_attack;
    }

    /*
    //Fight method
    public static void enemyFight()
    {
        String name = null;
        int maxHP = 0;
        char smallCharacter = 0;
        int attack = 0;
        Player player = Player.getInstance();//new Player(name, maxHP, smallCharacter, attack);
        Enemy enemy = new Enemy(name, maxHP, smallCharacter, attack);
        int playerHP = player.getMaxHP();
        int enemyHP = enemy.getMaxHP();
        while (playerHP != 0 || enemyHP != 0) {
            playerHP -= player.getAttack();
            enemyHP -= enemy.getAttack();
        }
    }
     */

    /**
    public final int takeDamage(final int _attack)
    {
        m_hp -= _attack;
        return _attack; //placeholder probably
        //TODO: maybe make this more interesting
    }
     */

    public int attack()
    {
        Random r = new Random();
        if (r.nextFloat() > 0.70)
            return 0;
        else
            return m_attack;
    }

} // class Enemy
