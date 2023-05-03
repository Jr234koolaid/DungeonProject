package edu.utsa.cs3443.dungeon.model;

import java.util.Random;

/**
 */
public class Player extends Entity
{
    /**
     */

    private static Player instance = null;

    private Weapon equippedWeapon;
    private Armor[] equippedArmor;


    private Player(final String _name, final int _maxHP, final char _smallCharacter)
    {
        super(_name, 0, _maxHP, _smallCharacter, 0);
        equippedWeapon = new Weapon("Stick", 3, (float)0.1);
        equippedArmor = new Armor[4];
    }

    /**
     * Copy Constructor
     *
    public Player(Player _player)
    {
        super(_player.m_name, _player.m_minHP, _player.m_maxHP, _player.m_smallCharacter, _player.m_attack);

        m_hp = _player.m_hp;
        m_x = _player.m_x;
        m_y = _player.m_y;
        m_attack = _player.m_attack;
    }
     */

    public static synchronized Player getInstance(){
        if (instance == null)
            instance = new Player("Player",50, '@');
        return instance;
    }

    public static void destroy(){
        instance = null;
    }

    /**
     */
    public void addItem(final Item item)
    {
        switch (item.getType()){
            case "WEAPON":
                equippedWeapon = (Weapon)item;
                break;
            case "ARMOR":
                switch(((Armor)item).getSlot()){
                    case "head":
                        equippedArmor[0] = (Armor)item; break;
                    case "torso":
                        equippedArmor[1] = (Armor)item; break;
                    case "legs":
                        equippedArmor[2] = (Armor)item; break;
                    case "feet":
                        equippedArmor[3] = (Armor)item; break;
                    default:
                        //idk
                }
                break;
            case "HEALING":
                m_hp = m_maxHP;
                break;
            default:
                //idk
        }

    }

    public int takeDamage(int attack){
        int defense = defend(); //if -1, then dodge
        int ret = attack - defense;
        if (defense == -1) {
            return -1;
        }
        else if (ret <= 0){
            return 0;
        }
        else {
            setHP(getHP()-ret);
            return ret;
        }
    }

    public int defend(){
        Random r = new Random();
        if (r.nextFloat() < getTotalDodge())
            return -1;
        else
            return getTotalDefense();
    }

    public int getTotalDefense(){
        int ret = 0;
        for (Armor a : equippedArmor){
            if (a != null)
                ret += a.getDefense();
        }
        return ret;
    }

    public float getTotalDodge(){
        float ret = (float)0.20;
        for (Armor a : equippedArmor){
            if (a != null)
                ret += a.getDodge();
        }
        return ret;
    }

    public Weapon getWeapon(){
        return equippedWeapon;
    }

} // class Player
