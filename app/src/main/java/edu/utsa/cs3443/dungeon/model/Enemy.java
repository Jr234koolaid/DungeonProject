package edu.utsa.cs3443.dungeon.model;

import android.content.Context;

/**
 */
public class Enemy extends Entity
{
    private int _attack;
    /**
     * Defines the Entity class and relevant methods
     * @param _maxHP max hp
     **/
    public Enemy(final String _name, final int _maxHP, final char _smallCharacter, int _attack)
    {
        super(_name, 0, _maxHP, _smallCharacter, _attack);
        this._attack = _attack;
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

} // class Enemy
