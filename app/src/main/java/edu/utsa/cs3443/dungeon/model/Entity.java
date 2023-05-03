package edu.utsa.cs3443.dungeon.model;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.Serializable;

/**
 */
public abstract class Entity implements Serializable
{
    // Class variable
    public static final int         MAX_SPRITE_WIDTH = 30;  //
    public static final int         MAX_SPRITE_HEIGHT = 15; //

    // Member variables
    protected final String          m_name;             //
    protected final int             m_minHP;            //
    protected final int             m_maxHP;            //
    protected final char            m_smallCharacter;   //
    protected char[][]              m_largeCharacter;   //
    protected int                   m_hp;               //
    protected int                   m_x;                //
    protected int                   m_y;                //
    protected int                   m_attack;

    /**
     */
    public Entity(
            final String    _name,
            final int       _minHP,
            final int       _maxHP,
            final char      _smallCharacter,
            final int       _attack)
    {
        m_name = _name;
        m_minHP = _minHP;
        m_maxHP = _maxHP;
        m_smallCharacter = _smallCharacter;
        m_hp = m_maxHP;
        m_x = 0;
        m_y = 0;
        m_attack = _attack;
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
    public final char[][]getLargeCharacter()
    {
        return m_largeCharacter;
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
    public final int getAttack()
    {
        return m_attack;
    }

    /**
     */
    public void setHP(final int _hp)
    {
        m_hp = _hp;
    }

    public void setAttack(final int _attack) {
        m_attack = _attack;
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

    /*
     */
    public final boolean canInteract(final Entity _entity)
    {
        return ((m_x == _entity.m_x) && ((m_y == (_entity.m_y - 1)) || (m_y == (_entity.m_y + 1))) ||
                (m_y == _entity.m_y) && ((m_x == (_entity.m_x - 1)) || (m_x == (_entity.m_x + 1))));
    }

    /**
     */
    public void loadLargeCharacter(
            final String    _characterFilePath,
            AssetManager    _assetManager) throws IOException
    {
        // Allocate large character
        m_largeCharacter = new char[MAX_SPRITE_HEIGHT][MAX_SPRITE_WIDTH];

        // Go through large character data file
        TextParser parser = new TextParser(_characterFilePath, _assetManager);

        // Get large character data
        int sizeY = 0;

        String line = null;
        while (((line = parser.next()) != null) && (sizeY < MAX_SPRITE_HEIGHT))
        {
            // Get characters from string
            int sizeX = 0;

            final int min = Math.min(line.length(), MAX_SPRITE_WIDTH);
            for (; sizeX < min; sizeX++)
                m_largeCharacter[sizeY][sizeX] = line.charAt(sizeX);

            // Fill in missing characters
            if (sizeX != MAX_SPRITE_WIDTH)
            {
                for (; sizeX < MAX_SPRITE_WIDTH; sizeX++)
                    m_largeCharacter[sizeY][sizeX] = ' ';
            }

            // Increment size
            sizeY++;
        }

        // Fill in missing lines
        if (sizeY != MAX_SPRITE_HEIGHT)
        {
            for (; sizeY < MAX_SPRITE_HEIGHT; sizeY++)
            {
                int sizeX = 0;
                for (; sizeX < MAX_SPRITE_WIDTH; sizeX++)
                    m_largeCharacter[sizeY][sizeX] = ' ';
            }
        }

        // Close parser
        parser.close();
    }

} // class Entity
