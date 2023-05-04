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
    protected String                m_modifier;

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
    public Weapon(final Weapon _weapon)
    {
        super(_weapon.m_name, _weapon.m_smallCharacter);

        m_largeCharacter = _weapon.m_largeCharacter;
        m_type = _weapon.m_type;
        m_damage = _weapon.m_damage;
        m_crit = _weapon.m_crit;
        m_accuracy = _weapon.m_accuracy;
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

    public void applyModifier(Modifier mod) {
        m_modifier = mod.getName();
        m_damage += mod.getIntMod();
        if (m_damage < 0)
            m_damage = 0;
        m_crit += mod.getFloatMod();
        if (m_crit < (float) 0.0)
            m_crit = (float) 0.0;
    }


    //@NonNull
    @Override
    public String toString() {
        String ret = "You found ";
        if (m_modifier != null)
            ret += " ["+m_modifier+"] ";
        ret += getName() + "\n[dmg:" + getDamage() + "] [crit:";
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
