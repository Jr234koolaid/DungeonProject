package edu.utsa.cs3443.dungeon.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import edu.utsa.cs3443.dungeon.R;

/**
 */
public class Floor extends TableLayout
{
    // Class variables
    private static final int        MAX_LAYOUT_WIDTH = 35;  //
    private static final int        MAX_LAYOUT_HEIGHT = 15; //

    // Member variables
    private int                     m_floorLevel;   //
    private int                     m_mapIndex;     //
    private ArrayList<Map>          m_mapList;      //

    /**
     */
    public Floor(Context _context)
    {
        super(_context);

        m_floorLevel = 0;
        m_mapIndex = 0;
        m_mapList = new ArrayList<>();
    }

    /**
     */
    public Map getCurrentMap()
    {
        return m_mapList.get(m_mapIndex);
    }

    /**
     */
    public void setFloorLevel(final int _floorLevel)
    {
        m_floorLevel = _floorLevel;
    }

    /**
     */
    public void generateMapLayout()
    {
        // Clear views
        this.removeAllViews();

        // Get context resources
        Context context = this.getContext();
        Resources res = context.getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();

        // Create table
        final float textWidth = 10.f;
        final float rowWidth = (MAX_LAYOUT_WIDTH * textWidth);

        final int rowLayoutWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rowWidth, metrics));
        final int textLayoutWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textWidth, metrics));
        final int textLayoutHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30.f, metrics));

        for (int i = 0; i < MAX_LAYOUT_HEIGHT; i++)
        {
            // Create row
            TableRow row = new TableRow(context);
            row.setLayoutParams(new TableLayout.LayoutParams(rowLayoutWidth, TableLayout.LayoutParams.WRAP_CONTENT));

            // Create text columns
            for (int j = 0; j < MAX_LAYOUT_WIDTH; j++)
            {
                TextView textView = new TextView(context);
                textView.setLayoutParams(new TableRow.LayoutParams(textLayoutWidth, textLayoutHeight));
                textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                textView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                textView.setTextColor(context.getColorStateList(R.color.teal_700));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19.f);
                textView.setText(String.valueOf(m_mapList.get(m_mapIndex).getData(j, i)));

                // Add columns to row
                row.addView(textView);
            }

            // Add row to table
            this.addView(row);
        }
    }

    /**
     */
    public void loadMaps(final int _level) throws IOException
    {
        // Get asset manager
        AssetManager assetManager = this.getContext().getAssets();

        // Get root (floor) directory
        final String rootDirName = ("floor_" + m_floorLevel);

        final String[] mapList = assetManager.list(rootDirName);
        for (final String mapName : mapList)
        {
            if (mapName.equals("map_" + _level))
            {
                final String mapDirName = (rootDirName + "/" + mapName);

                // Load map
                Map map = new Map();
                map.loadData(mapDirName, assetManager);
                map.loadPlayer(mapDirName, assetManager);
                map.loadEnemies((mapDirName + "/" + "enemy"), assetManager);
                map.loadItems((mapDirName + "/" + "item"), assetManager);

                // Add map to list
                m_mapList.add(map);
                break;
            }
        }

        // Reset map index
        m_mapIndex = 0;
    }

} // class Scene
