package edu.utsa.cs3443.dungeon.model;

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
