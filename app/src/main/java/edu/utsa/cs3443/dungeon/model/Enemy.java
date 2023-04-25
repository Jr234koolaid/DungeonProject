package edu.utsa.cs3443.dungeon.model;

/**
 */
public class Enemy extends Entity {

    /**
     * Defines the Entity class and relevant methods
     * @param _maxHP max hp
     **/
    public Enemy(final String _name, final int _maxHP, final char _smallCharacter)
    {
        super(_name, 0, _maxHP, _smallCharacter);
    }

} // class Enemy
