package edu.utsa.cs3443.dungeon.model;

import android.content.Context;

/**
 */
public class Enemy extends Entity
{
    /**
     * Defines the Entity class and relevant methods
     * @param _maxHP max hp
     **/
    public Enemy(final String _name, final int _maxHP, final char _smallCharacter)
    {
        super(_name, 0, _maxHP, _smallCharacter);
    }

    /**
     * Copy Constructor
     */
    public Enemy(Entity _enemy)
    {
        super(_enemy.m_name, _enemy.m_minHP, _enemy.m_maxHP, _enemy.m_smallCharacter);

        m_largeCharacter= _enemy.m_largeCharacter;
        m_hp = _enemy.m_hp;
        m_x = _enemy.m_x;
        m_y = _enemy.m_y;
    }

} // class Enemy
