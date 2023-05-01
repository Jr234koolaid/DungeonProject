package edu.utsa.cs3443.dungeon.model;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public abstract class EntityGenerator {

    /**  R
     * This abstract class will contain methods for
     * reading a list of stats and generating a random
     * entity from that list
     */

    protected ArrayList<Entity> entityList;
    protected String entityDir;
    protected AssetManager aM;

    public EntityGenerator(String entityDir, AssetManager aM){
        this.entityDir = entityDir;
        this.aM = aM;
        entityList = readFromList(); //TODO: this makes an ArrayList<Entity> out of an ArrayList<Weapon> etc.

    }

    protected abstract ArrayList readFromList();

    public Entity generate(){
        Random r = new Random();
        return entityList.get(r.nextInt(entityList.size()));
    }

}
