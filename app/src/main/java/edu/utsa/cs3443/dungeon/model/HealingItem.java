package edu.utsa.cs3443.dungeon.model;

import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import java.io.IOException;

public class HealingItem extends Item{

    public HealingItem(String _name, AssetManager aM) {
        super(_name, 'Y');
        try {
            loadLargeCharacter("items/" + _name + ".tett", aM);
        } catch (IOException e) {
            m_largeCharacter = null;
        }
    }

    @Override
    public String getType() {
        return "HEALING";
    }

    @NonNull
    @Override
    public String toString() {
        return "You found " + this.getName().toUpperCase() + ".\nUse it?";
    }
}
