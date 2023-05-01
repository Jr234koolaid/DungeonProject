package edu.utsa.cs3443.dungeon.model;

/**
 */
public class Player extends Entity {

    /**
     */
    public Player(final String _name, final char _smallCharacter)
    {
        super(_name, 0, 100, _smallCharacter, 10);
    }

    /**
     * Copy Constructor
     */
    public Player(Player _player)
    {
        super(_player.m_name, _player.m_minHP, _player.m_maxHP, _player.m_smallCharacter, _player.m_attack);

        m_hp = _player.m_hp;
        m_x = _player.m_x;
        m_y = _player.m_y;
    }

} // class Player
