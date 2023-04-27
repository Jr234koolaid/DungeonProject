package edu.utsa.cs3443.dungeon.model;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;

/**
 */
public interface TableView
{
    /**
     */
    public void generate(Context _context);

    /**
     */
    public void load(final String _root, AssetManager _assetManager) throws IOException;

} // interface TableView
