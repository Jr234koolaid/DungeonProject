package edu.utsa.cs3443.dungeon.model;

public class Armor extends Item{

    protected String type;
    protected int defense;

    protected float dodge;

    /**
     * @param _name Armor name
     * @param type Armor type (head,torso,legs,feet)
     * @param defense Armor defense (int, >0)
     * @param dodge Armor dodge (float, 0.00-1.00)
     */
    public Armor(String _name, String type, int defense, float dodge) {
        super(_name, '$');
        this.type = type;
        this.defense = defense;
        this.dodge = dodge;
    }

    public int getDefense() {
        return defense;
    }
    public String getType(){
        return type;
    }
    public float getDodge() {
        return dodge;
    }

    @Override
    public String[] getStats() {
        return new String[0];
    }
}
