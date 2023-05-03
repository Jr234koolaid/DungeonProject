package edu.utsa.cs3443.dungeon.model;

import androidx.annotation.NonNull;

public class Armor extends Item{

    protected String slot;
    protected int defense;

    protected float dodge;

    /**
     * @param _name Armor name
     * @param slot Armor slot (head,torso,legs,feet)
     * @param defense Armor defense (int, >0)
     * @param dodge Armor dodge (float, 0.00-1.00)
     */
    public Armor(String _name, String slot, int defense, float dodge) {
        super(_name, '$');
        this.type = "ARMOR";
        this.slot = slot;
        this.defense = defense;
        this.dodge = dodge;
    }

    @NonNull
    @Override
    public String toString() {
        String ret = "You found " + getName() + "\n[def:" + getDefense() + "] [dodge:";
        if(getDodge() >= 0)
            ret+= "+";
        ret += Math.round(getDodge()*100) + "%]" + "\nTake it?";
        return ret;
    }


    public int getDefense() {
        return defense;
    }

    @Override
    public String getType(){
        return type;
    }

    public String getSlot() {
        return slot;
    }
    public float getDodge() {
        return dodge;
    }

}
