package edu.utsa.cs3443.dungeon.model;

import android.content.res.AssetManager;

import java.util.ArrayList;
import java.util.Random;

public class ModifierGenerator {

    ArrayList<Modifier> entityList = new ArrayList();

    public ModifierGenerator() {
        readFromList();
    }

    protected ArrayList readFromList() {
        entityList.add(new Modifier("Normal",0,0F));
        entityList.add(new Modifier("Deft",0,0.1F));
        entityList.add(new Modifier("Clunky",0,-0.1F));
        entityList.add(new Modifier("Lucky",0,0.1F));
        entityList.add(new Modifier("Unlucky",0,-0.1F));
        entityList.add(new Modifier("Premium",2,0F));
        entityList.add(new Modifier("Cheap",-2,0F));
        entityList.add(new Modifier("Staunch",2,0F));
        entityList.add(new Modifier("Flimsy",-2,0F));
        entityList.add(new Modifier("Light",-2,0.1F));
        entityList.add(new Modifier("Heavy",2,-0.1F));
        entityList.add(new Modifier("Golden",2,0.1F));
        entityList.add(new Modifier("Rusty",-2,-0.1F));
        return entityList;
    }

    public Modifier generate() {
            Random r = new Random();
            return (Modifier)(entityList.get(r.nextInt(entityList.size())));
        }
}
