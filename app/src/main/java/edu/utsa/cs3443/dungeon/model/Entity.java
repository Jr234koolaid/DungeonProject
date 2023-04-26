package edu.utsa.cs3443.dungeon.model;

/**
 */
public abstract class Entity {

    private final String            m_name;             //
    private final int               m_minHP;            //
    private final int               m_maxHP;            //
    private final char              m_smallCharacter;   //
    private char[][]                m_largeCharacter;   //
    private int                     m_hp;               //
    private int                     m_x;                //
    private int                     m_y;                //

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
    public final String getName()
    {
        return m_name;
    }

    /**
     */
    public final int getMinHP()
    {
        return m_minHP;
    }

    /**
     */
    public final int getMaxHP()
    {
        return m_maxHP;
    }

    /**
     */
    public final char getSmallCharacter()
    {
        return m_smallCharacter;
    }

    /**
     */
    public final int getHP()
    {
        return m_hp;
    }

    /**
     */
    public final int getX()
    {
        return m_x;
    }

    /**
     */
    public final int getY()
    {
        return m_y;
    }

    /**
     */
    public void setHP(final int _hp)
    {
        m_hp = _hp;
    }

    /**
     */
    public void setX(final int _x)
    {
        m_x = _x;
    }

    /**
     */
    public void setY(final int _y)
    {
        m_y = _y;
    }

    /**
     */
    public void loadLargeCharacter(final String _path)
    {
        // TODO: Load large character from file
    }

} // class Entity
