package edu.utsa.cs3443.dungeon.model;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ArmorGenerator extends EntityGenerator{

    protected ArrayList<Armor> entityList;

    public ArmorGenerator(String armorDir, AssetManager aM)
    {
        super(armorDir, aM);
        entityList = readFromList();
    }

    @Override
    protected ArrayList<Armor> readFromList() {
        Scanner sc;
        String[] strs;
        ArrayList<Armor> retList = new ArrayList<>();
        Armor tArmor;

        try {
            sc = new Scanner(aM.open(entityDir + "/list.tinf"));
        } catch (IOException e) {
            return null; //TODO: handle IOException
        }

        while (sc.hasNextLine()){
            strs = sc.nextLine().split(",");
            //                 Name,    Type     Defense,                   Dodge%
            tArmor = new Armor(strs[0], strs[1], Integer.parseInt(strs[2]), Float.parseFloat(strs[3]));
            try {
                tArmor.loadLargeCharacter(entityDir + "/" + tArmor.getName().toLowerCase() + ".tett", aM);
            } catch (IOException e) {
                //TODO: handle IOException
            }
            retList.add(tArmor);


        }

        return retList;
    }

    public Armor generate()
    {
        Random r = new Random();
        return new Armor(entityList.get(r.nextInt(entityList.size())));
    }

}
