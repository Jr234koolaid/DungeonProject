package edu.utsa.cs3443.dungeon.model;

import android.content.res.AssetManager;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class WeaponGenerator extends EntityGenerator{


    public WeaponGenerator(String weaponDir, AssetManager aM){
        super(weaponDir, aM);
    }

    @Override
    protected ArrayList<Weapon> readFromList() {
        Scanner sc;
        ArrayList<Weapon> retList = new ArrayList<>();
        String[] strs;
        try {
            sc = new Scanner(aM.open(entityDir)); //TODO: unhandled exception
        } catch (IOException e) {
            return null;
        }
        while (sc.hasNextLine()){
            strs = sc.nextLine().split(",");
            retList.add(new Weapon(strs[0], Integer.parseInt(strs[2]), Float.parseFloat(strs[3])));
            //                     Name,    Defense,                    Dodge%
        }
        return retList;
    }
}
