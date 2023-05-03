package edu.utsa.cs3443.dungeon.model;

import java.util.Random;

public class Weapon extends Item {


    protected int damage;
    protected float crit;
    protected float accuracy;

    /**
     * @param name Weapon's name (also used for finding bigCharacter)
     * @param damage Weapon's damage (int, >0)
     * @param crit Weapon's chance to do double damage (float, 0.00-1.00)
     */
    public Weapon(String name, int damage, float crit) {
        super(name, '$'); //note: I think all weapons and armor should be the same symbol on the map. -R
        this.type = "WEAPON";
        this.damage = damage;
        this.crit = crit;
        accuracy = (float) 0.8;
    }

    public int attack() {
        Random r = new Random();
        if (r.nextFloat() > accuracy)
            return 0;
        else if (r.nextFloat() <= crit)
            return 2*damage;
        else
            return damage;
    }

    @Override
    public String getType() {
        return type;
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

}
