package edu.utsa.cs3443.dungeon.model;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ArmorGenerator extends EntityGenerator{


    public ArmorGenerator(String armorDir, AssetManager aM){
        super(armorDir, aM);
    }

    @Override
    protected ArrayList<Armor> readFromList() {
        Scanner sc;
        String[] strs;
        ArrayList<Armor> retList = new ArrayList<>();

        try {
            sc = new Scanner(aM.open(entityDir));
        } catch (IOException e) {
            return null; //TODO: handle IOException
        }

        while (sc.hasNextLine()){
            strs = sc.nextLine().split(",");
            retList.add(new Armor(strs[0], strs[1], Integer.parseInt(strs[2]), Float.parseFloat(strs[3])));
            /*                     Name,   Type     Defense,                   Dodge%                    */
        }

        return retList;
    }
}
