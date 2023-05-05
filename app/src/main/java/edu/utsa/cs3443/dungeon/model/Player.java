package edu.utsa.cs3443.dungeon.model;

import java.util.Random;

/**
 */
public class Player extends Entity
{
    public static Player           INSTANCE = null;     //

    private Weapon                  m_equippedWeapon;   //
    private final Armor[]                 m_equippedArmor;    //

    /**
     */
    private Player(final String _name, final int _maxHP, final char _smallCharacter)
    {
        super(_name, 0, _maxHP, _smallCharacter, 0);

        m_equippedWeapon = new Weapon("Stick", 3, (float)0.1);
        m_equippedArmor = new Armor[4];
    }

    /**
     */
    public static Player getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new Player("Player", 50, '@');

        return INSTANCE;
    }

    /**
     */
    public static void destroy()
    {
        INSTANCE = null;
    }

    /**
     */
    public Weapon getWeapon()
    {
        return m_equippedWeapon;
    }

    /**
     */
    public void addItem(final Item item)
    {
        final String type = item.getType();
        switch (type)
        {
            case "WEAPON":
                m_equippedWeapon = (Weapon)item;
                break;

            case "ARMOR":
                switch(((Armor)item).getSlot())
                {
                    case "head":
                        m_equippedArmor[0] = (Armor)item;
                        break;

                    case "torso":
                        m_equippedArmor[1] = (Armor)item;
                        break;

                    case "legs":
                        m_equippedArmor[2] = (Armor)item;
                        break;

                    case "feet":
                        m_equippedArmor[3] = (Armor)item;
                        break;
                        
                    case "HEALING":
                        m_hp = m_maxHP;
                        break;

                    default:
                        break;
                        
                } break;
                
            default:
                break;
        }
    }

    /**
     */
    public final int takeDamage(final int _attack)
    {
        final int defense = defend(); //if -1, then dodge
        if (defense == -1)
            return -1;

        final int ret = (_attack - defense);
        if (ret <= 0)
            return 0;
        else
            m_hp -= ret;

        return ret;
    }

    /**
     */
    public final int defend()
    {
        Random r = new Random();
        if (r.nextFloat() < getTotalDodge())
            return -1;
        else
            return getTotalDefense();
    }

    /**
     */
    public final float getTotalDodge()
    {
        float ret = 0.20f;
        for (final Armor a : m_equippedArmor)
        {
            if (a != null)
                ret += a.getDodge();

        } return ret;
    }

    /**
     */
    public final int getTotalDefense()
    {
        int ret = 0;
        for (final Armor a : m_equippedArmor)
        {
            if (a != null)
                ret += a.getDefense();

        } return ret;
    }

} // class Player
