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

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;

import edu.utsa.cs3443.dungeon.R;

/**
 */
public abstract class Entity implements TableView, Serializable
{
    // Class variable
    protected static final int      MAX_SPRITE_WIDTH = 30;  //
    protected static final int      MAX_SPRITE_HEIGHT = 15; //

    // Member variables
    protected TableLayout           m_layout;           //
    protected final String          m_name;             //
    protected final int             m_minHP;            //
    protected final int             m_maxHP;            //
    protected final char            m_smallCharacter;   //
    protected char[][]              m_largeCharacter;   //
    protected int                   m_hp;               //
    protected int                   m_x;                //
    protected int                   m_y;                //

    /**
     */
    public Entity(
            final String    _name,
            final int       _minHP,
            final int       _maxHP,
            final char      _smallCharacter)
    {
        m_name = _name;
        m_minHP = _minHP;
        m_maxHP = _maxHP;
        m_smallCharacter = _smallCharacter;
        m_hp = m_maxHP;
        m_x = 0;
        m_y = 0;
    }

    /**
     */
    public TableLayout getLayout()
    {
        return m_layout;
    }

    /**
     */
    public final String getName()
    {
        return m_name;
    }

    /**
     */
    public final int getMinHP()
    {
        return m_minHP;
    }

    /**
     */
    public final int getMaxHP()
    {
        return m_maxHP;
    }

    /**
     */
    public final char getSmallCharacter()
    {
        return m_smallCharacter;
    }

    /**
     */
    public final int getHP()
    {
        return m_hp;
    }

    /**
     */
    public final int getPositionX()
    {
        return m_x;
    }

    /**
     */
    public final int getPositionY()
    {
        return m_y;
    }

    /**
     */
    public void setHP(final int _hp)
    {
        m_hp = _hp;
    }

    /**
     */
    public void setPositionX(final int _x)
    {
        m_x = _x;
    }

    /**
     */
    public void setPositionY(final int _y)
    {
        m_y = _y;
    }

    /*
     */
    public final boolean canInteract(final Entity _entity)
    {
        return (
                (m_x == _entity.m_x) && ((m_y == (_entity.m_y - 1)) || (m_y == (_entity.m_y + 1))) ||
                (m_y == _entity.m_y) && ((m_x == (_entity.m_x - 1)) || (m_x == (_entity.m_x + 1))));
    }

    /**
     */
    @Override
    public void generate(Context _context)
    {
        // Create new table layout
        m_layout = new TableLayout(_context);

        // Get context resources
        Resources res = _context.getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();

        // Create table
        final float textWidth = 10.f;
        final float rowWidth = (MAX_SPRITE_WIDTH * textWidth);

        final int rowLayoutWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rowWidth, metrics));
        final int textLayoutWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textWidth, metrics));
        final int textLayoutHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30.f, metrics));

        for (int i = 0; i < MAX_SPRITE_HEIGHT; i++)
        {
            // Create row
            TableRow row = new TableRow(_context);
            row.setLayoutParams(new TableLayout.LayoutParams(rowLayoutWidth, TableLayout.LayoutParams.WRAP_CONTENT));

            // Create text columns
            for (int j = 0; j < MAX_SPRITE_WIDTH; j++)
            {
                TextView textView = new TextView(_context);
                textView.setLayoutParams(new TableRow.LayoutParams(textLayoutWidth, textLayoutHeight));
                textView.setTextAlignment(android.view.View.TEXT_ALIGNMENT_CENTER);
                textView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                textView.setTextColor(_context.getColorStateList(R.color.teal_700));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19.f);
                textView.setText(String.valueOf(m_largeCharacter[i][j]));

                // Add columns to row
                row.addView(textView);
            }

            // Add row to table
            m_layout.addView(row);
        }
    }

    /**
     */
    @Override
    public void load(final String _root, AssetManager _assetManager) throws IOException
    {
        // Allocate large character
        m_largeCharacter = new char[MAX_SPRITE_HEIGHT][MAX_SPRITE_WIDTH];

        // Open character file
        InputStream characterStream = _assetManager.open(_root + "/" + "large_character.tett");

        // Go through the file
        Scanner scanner = new Scanner(characterStream);

        // Get lines from file
        int sizeY = 0;
        for (; (scanner.hasNext() && (sizeY < MAX_SPRITE_HEIGHT)); sizeY++)
        {
            final String line = scanner.nextLine();
            final int min = Math.min(line.length(), MAX_SPRITE_WIDTH);

            // Get characters from string
            int sizeX = 0;
            for (; sizeX < min; sizeX++)
                m_largeCharacter[sizeY][sizeX] = line.charAt(sizeX);

            // Fill in missing characters
            if (sizeX != MAX_SPRITE_WIDTH)
            {
                for (; sizeX < MAX_SPRITE_WIDTH; sizeX++)
                    m_largeCharacter[sizeY][sizeX] = ' ';
            }
        }

        // Fill in missing lines
        if (sizeY != MAX_SPRITE_HEIGHT)
        {
            for (; sizeY < MAX_SPRITE_HEIGHT; sizeY++)
            {
                int sizeX = 0;
                for (; sizeX < MAX_SPRITE_WIDTH; sizeX++)
                    m_largeCharacter[sizeY][sizeX] = ' ';
            }
        }
        scanner.close();

        // Close character file
        characterStream.close();
    }

} // class Entity
