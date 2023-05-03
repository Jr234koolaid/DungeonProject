package edu.utsa.cs3443.dungeon.model;

//import androidx.annotation.NonNull;

import java.util.Random;

/**
 */
public class Weapon extends Item
{
    protected int                   m_damage;   //
    protected float                 m_crit;     //
    protected float                 m_accuracy; //

    /**
     * @param _name Weapon's name (also used for finding bigCharacter)
     * @param _damage Weapon's damage (int, >0)
     * @param _crit Weapon's chance to do double damage (float, 0.00-1.00)
     */
    public Weapon(final String _name, final int _damage, final float _crit)
    {
        super(_name, '$'); //note: I think all weapons and armor should be the same symbol on the map. -R
        m_type = "WEAPON";
        m_damage = _damage;
        m_crit = _crit;
        m_accuracy = 0.8f;
    }

    /**
     */
    public int attack()
    {
        Random r = new Random();
        if (r.nextFloat() > m_accuracy)
            return 0;
        else if (r.nextFloat() <= m_crit)
            return (2 * m_damage);
        else
            return m_damage;
    }

    /**
     */
    @Override
    public String getType() {
        return m_type;
    }

    //TODO (R): create modifiers (maybe using EntityGenerator)
    /*
    public void applyModifier(String modifierName, int damageChange, float critChange, float accuracyChange) {
        m_name = modifierName + " " + m_name;
        damage += damageChange;
        if (damage < 0)
            damage = 0;
        crit += critChange;
        if (crit < (float) 0.0)
            crit = (float) 0.0;
        accuracy += accuracyChange;
        if (accuracy < (float) 0.0)
            accuracy = (float) 0.0;
    }
    */

    //@NonNull
    @Override
    public String toString() {
        String ret = "You found " + getName() + "\n[dmg:" + getDamage() + "] [crit:";
         /*
         if(getCrit() >= 0)
             ret+= "+";
         */
        ret += Math.round(getCrit()*100)+"%]" + "\nTake it?";
         return ret;
    }

    /**
     */
    public int getDamage()
    {
        return m_damage;
    }

    /**
     */
    public float getCrit() {
        return m_crit;
    }
}
