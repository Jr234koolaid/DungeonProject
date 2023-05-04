package edu.utsa.cs3443.dungeon.model;

//import androidx.annotation.NonNull;

/**
 */
public class Armor extends Item
{
    protected String                m_slot;     //
    protected int                   m_defense;  //
    protected float                 m_dodge;    //
    protected String                m_modifier;

    /**
     * @param _name Armor name
     * @param _slot Armor slot (head,torso,legs,feet)
     * @param _defense Armor defense (int, >0)
     * @param _dodge Armor dodge (float, 0.00-1.00)
     */
    public Armor(final String _name, final String _slot, final int _defense, final float _dodge)
    {
        super(_name, '$');

        m_type = "ARMOR";
        m_slot = _slot;
        m_defense = _defense;
        m_dodge = _dodge;
    }

    /**
     */
    public Armor(final Armor _armor)
    {
        super(_armor.m_name, _armor.m_smallCharacter);

        m_largeCharacter = _armor.m_largeCharacter;
        m_type = _armor.m_type;
        m_slot = _armor.m_slot;
        m_defense = _armor.m_defense;
        m_dodge = _armor.m_dodge;
    }

    public void applyModifier(Modifier mod) {
        m_modifier = mod.getName();
        m_defense += mod.getIntMod();
        if (m_defense < 0)
            m_defense = 0;
        m_dodge += mod.getFloatMod();
        if (m_dodge < (float) 0.0)
            m_dodge = (float) 0.0;
    }

    /**
     */
    @Override
    public String toString()
    {
        String ret = "You found ";
        if (m_modifier != null)
            ret += " ["+m_modifier+"] ";
        ret += getName() + "\n[def:" + getDefense() + "] [dodge:";
        if(getDodge() >= 0)
            ret+= "+";
        ret += Math.round(getDodge()*100) + "%]" + "\nTake it?";
        return ret;
    }

    /**
    */
    public int getDefense()
    {
        return m_defense;
    }

    /**
     */
    @Override
    public String getType()
    {
        return m_type;
    }

    /**
     */
    public String getSlot()
    {
        return m_slot;
    }

    /**
     */
    public float getDodge()
    {
        return m_dodge;
    }

} // class Armor
