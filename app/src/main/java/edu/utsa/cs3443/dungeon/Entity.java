package edu.utsa.cs3443.dungeon;

/**
 */
public class Entity {

    private final String            m_name;             //
    private final int               m_minHP;            //
    private final int               m_maxHP;            //
    private final char              m_smallCharacter;   //
    private int                     m_hp;               //

    /**
     */
    public Entity(
            final String    _name,
            final int       _minHP,
            final int       _maxHP,
            final char      _smallCharacter)
    {
        m_name = _name;
        m_minHP = _minHP;
        m_maxHP = _maxHP;
        m_smallCharacter = _smallCharacter;
        m_hp = m_maxHP;
    }

    /**
     */
    final String getName()
    {
        return m_name;
    }

    /**
     */
    final int getMinHP()
    {
        return m_minHP;
    }

    /**
     */
    final int getMaxHP()
    {
        return m_maxHP;
    }

    /**
     */
    final char getSmallCharacter()
    {
        return m_smallCharacter;
    }

    /**
     */
    final int getHP()
    {
        return m_hp;
    }

    /**
     */
    void loadLargeCharacter()
    {
    }

} // class Entity