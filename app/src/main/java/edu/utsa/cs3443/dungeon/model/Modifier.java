package edu.utsa.cs3443.dungeon.model;

public class Modifier extends Entity {

    private final int intMod;
    private final float floatMod;
    public Modifier(String name, int intMod, float floatMod) {
        super(name,0,0, '0',0);
        this.intMod=intMod;
        this.floatMod=floatMod;
    }


    public float getFloatMod() {
        return floatMod;
    }

    public int getIntMod() {
        return intMod;
    }

}
