package edu.utsa.cs3443.dungeon.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import edu.utsa.cs3443.dungeon.R;

/**
 */
public class Level extends TableLayout
{
    // Class variables
    private static final int MAX_MAP_LAYOUT_WIDTH =     35; //
    private static final int MAX_MAP_LAYOUT_HEIGHT =    15; //

    // Member variables
    private int             m_mapIndex; //
    private ArrayList<Map>  m_mapList;  //
    private Player          m_player;   //

    /**
     */
    public Level(Context _context)
    {
        super(_context);
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.setBackground(AppCompatResources.getDrawable(_context, R.drawable.map_border));

        m_mapIndex = 0;
        m_mapList = new ArrayList<>();
        m_player = null;
    }

    /**
     */
    private void findPlayer(Player _player)
    {
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

        final float textWidth = 10.f;
        final float rowWidth = (MAX_MAP_LAYOUT_WIDTH * textWidth);

        final int rowLayoutWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rowWidth, metrics));
        final int textLayoutWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textWidth, metrics));
        final int textLayoutHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20.f, metrics));

        // TODO (Juan): Make focus on player
        // Create table
        for (int i = 0; i < MAX_MAP_LAYOUT_HEIGHT; i++)
        {
            // Create row
            TableRow row = new TableRow(context);
            row.setLayoutParams(new TableLayout.LayoutParams(rowLayoutWidth, TableLayout.LayoutParams.WRAP_CONTENT));

            // Create text columns
            for (int j = 0; j < MAX_MAP_LAYOUT_WIDTH; j++)
            {
                TextView textView = new TextView(context);
                textView.setLayoutParams(new TableRow.LayoutParams(textLayoutWidth, textLayoutHeight));
                textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                textView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                textView.setTextColor(context.getColorStateList(R.color.teal_700));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22.f);
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
    public void load(final int _level, AssetManager _assetManager)
    {
        // Get root (level) directory
        final String rootDirName = ("level_" + _level);

        String[] regionList;
        try
        {
            regionList = _assetManager.list(rootDirName);
        }
        catch (IOException _e)
        {
            throw new RuntimeException(_e);
        }

        try
        {
            for (final String region : regionList)
            {
                final String regionDirName = (rootDirName + "/" + region);

                // TODO (Juan): Load positions
                Scanner scanner = new Scanner(_assetManager.open(regionDirName + "/" + "position.tpos"));
                while (scanner.hasNext())
                {
                    final String line = scanner.nextLine();
                }

                // Load map
                Map map = new Map();
                map.load((regionDirName + "/" + "map.tmap"), _assetManager);

                // TODO(Juan): Load enemies


                // TODO (Juan): Load items

                m_mapList.add(map);
            }
        }
        catch (IOException _e)
        {
            throw new RuntimeException(_e);
        }

        // Reset map index
        m_mapIndex = 0;
    }

} // class Scene
