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


    private Player(final String _name, final int _maxHP, final char _smallCharacter)
    {
        super(_name, 0, _maxHP, _smallCharacter, 0);
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
            instance = new Player("Player",30, '@');
        return instance;
    }

    public static void destroy(){
        instance = null;
    }

    /**
     */
    public void addItem(final Item _item)
    {

        // TODO: Check item
    }

    public int takeDamage(int attack){
        int defense = getDefense(); //if -1, then dodge
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

    public int getDefense(){
        Random r = new Random();
        if (r.nextFloat() < 0.2)
            return -1;
        else
            return getTotalArmorDefense();
    }

    public int getTotalArmorDefense(){
        //TODO: count up all the defense of all equipped armor
        return 2;
    }

    public Weapon getWeapon(){
        if (equippedWeapon == null)
            equippedWeapon = new Weapon("Stick", 3, (float)0.1);
        return equippedWeapon;
    }

} // class Player
