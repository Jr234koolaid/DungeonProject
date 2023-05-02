package edu.utsa.cs3443.dungeon.model;

/**
 */
public abstract class Item extends Entity
{
    /**
     */
    public Item(final String _name, final char _smallCharacter)
    {
        super(_name, 0, 0, _smallCharacter, 0);
    }

    /**
     */
    public abstract String[] getStats();

} // class Item
