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
import java.util.ArrayList;
import java.util.Scanner;

import edu.utsa.cs3443.dungeon.R;

/**
 */
public class Map extends TableLayout
{
    // Class variables
    protected static final int      MAX_MAP_WIDTH = 35;     //
    protected static final int      MAX_MAP_HEIGHT = 15;    //

    // Member variables
    private int                     m_nextFloor;    //
    private int                     m_nextMap;      //
    private char[][]                m_data;         //
    private Player                  m_player;       //
    private ArrayList<Door>         m_doorList;     //
    private ArrayList<Enemy>        m_enemyList;    //
    private ArrayList<Item>         m_itemList;     //

    /**
     */
    public Map(Context _context)
    {
        super(_context);

        m_doorList = new ArrayList<>();
        m_enemyList = new ArrayList<>();
        m_itemList = new ArrayList<>();
    }

    /**
     */
    public final int getNextFloor()
    {
        return m_nextFloor;
    }

    /**
     */
    public final int getNextMap()
    {
        return m_nextMap;
    }

    /**
     */
    public final char[][] getData()
    {
        return m_data;
    }

    /**
     */
    public final char getData(final int _x, final int _y)
    {
        return m_data[_y][_x];
    }

    /**
     */
    public final Player getPlayer()
    {
        return m_player;
    }

    /**
     */
    public final ArrayList<Door> getDoorList()
    {
        return m_doorList;
    }

    /**
     */
    public final ArrayList<Enemy> getEnemyList()
    {
        return m_enemyList;
    }

    /**
     */
    public final ArrayList<Item> getItemList()
    {
        return m_itemList;
    }

    /*
     */
    public void updatePlayer(final Player _player)
    {
        // Check if player can move
        final int setX = _player.getPositionX();
        final int setY = _player.getPositionY();

        if (m_data[setY][setX] == ' ')
        {
            // Replace player's previous spot with an empty space
            m_data[m_player.getPositionY()][m_player.getPositionX()] = ' ';

            // Set player spot
            m_player.setPositionX(setX);
            m_player.setPositionY(setY);

            m_data[setY][setX] = m_player.getSmallCharacter();
        }
    }

    /**
     */
    public void updateEnemy(final Enemy _enemy)
    {
        // TODO (Juan): Implement
    }

    /**
     */
    public void removeEnemy(final Enemy _enemy)
    {
        // TODO (Juan): Implement
    }

    /**
     */
    public void removeItem(final Item _item)
    {
        // TODO (Juan): Implement
    }

    /**
     */
    public void generate()
    {
        // Clear views
        this.removeAllViews();

        // Get context resources
        Context context = getContext();
        Resources res = context.getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();

        // Create table
        final float textWidth = 10.f;
        final float rowWidth = (MAX_MAP_WIDTH * textWidth);

        final int rowLayoutWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rowWidth, metrics));
        final int textLayoutWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textWidth, metrics));
        final int textLayoutHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30.f, metrics));

        for (int i = 0; i < MAX_MAP_HEIGHT; i++)
        {
            // Create row
            TableRow row = new TableRow(context);
            row.setLayoutParams(new TableLayout.LayoutParams(rowLayoutWidth, TableLayout.LayoutParams.WRAP_CONTENT));

            // Create text columns
            for (int j = 0; j < MAX_MAP_WIDTH; j++)
            {
                TextView textView = new TextView(context);
                textView.setLayoutParams(new TableRow.LayoutParams(textLayoutWidth, textLayoutHeight));
                textView.setTextAlignment(android.view.View.TEXT_ALIGNMENT_CENTER);
                textView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                textView.setTextColor(context.getColorStateList(R.color.teal_700));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19.f);
                textView.setText(String.valueOf(m_data[i][j]));

                // Add columns to row
                row.addView(textView);
            }

            // Add row to table
            this.addView(row);
        }
    }

    /**
     */
    public void loadInfo(final String _root, AssetManager _assetManager) throws IOException
    {
        // Open player file
        InputStream infoStream = _assetManager.open(_root + "/" + "map.tinf");

        // Go through the file
        Scanner scanner = new Scanner(infoStream);

        // Get player info
        final String[] lineTokens = scanner.nextLine().replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
        final String name = lineTokens[0];
        final String[] infoTokens = lineTokens[1].split(",");

        // Set map info
        m_nextFloor = Integer.parseInt(infoTokens[0]);
        m_nextMap = Integer.parseInt(infoTokens[1]);

        scanner.close();

        // Close info file
        infoStream.close();
    }

    /**
     */
    public void loadMap(final String _root, AssetManager _assetManager) throws IOException
    {
        // Allocate data
        m_data = new char[MAX_MAP_HEIGHT][MAX_MAP_WIDTH];

        // Open data file
        InputStream dataStream = _assetManager.open(_root + "/" + "map.tmap");

        // Go through the file
        Scanner scanner = new Scanner(dataStream);

        // Get lines from file
        int sizeY = 0;
        for (; (scanner.hasNext() && (sizeY < MAX_MAP_HEIGHT)); sizeY++)
        {
            final String line = scanner.nextLine();
            final int min = Math.min(line.length(), MAX_MAP_WIDTH);

            // Get characters from string
            int sizeX = 0;
            for (; sizeX < min; sizeX++)
                m_data[sizeY][sizeX] = line.charAt(sizeX);

            // Fill in missing characters
            if (sizeX != MAX_MAP_WIDTH)
            {
                for (; sizeX < MAX_MAP_WIDTH; sizeX++)
                    m_data[sizeY][sizeX] = ' ';
            }
        }

        // Fill in missing lines
        if (sizeY != MAX_MAP_HEIGHT)
        {
            for (; sizeY < MAX_MAP_HEIGHT; sizeY++)
            {
                int sizeX = 0;
                for (; sizeX < MAX_MAP_WIDTH; sizeX++)
                    m_data[sizeY][sizeX] = ' ';
            }
        }
        scanner.close();

        // Close data file
        dataStream.close();
    }

    /*
     */
    public void loadPlayer(final String _root, AssetManager _assetManager) throws IOException
    {
        // Open player file
        InputStream infoStream = _assetManager.open(_root + "/" + "player.tinf");

        // Go through the file
        Scanner scanner = new Scanner(infoStream);

        // Get player info
        final String[] lineTokens = scanner.nextLine().replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
        final String name = lineTokens[0];
        final String[] infoTokens = lineTokens[1].split(",");

        // Create player
        final int x = Integer.parseInt(infoTokens[0]);
        final int y = Integer.parseInt(infoTokens[1]);
        final char smallCharacter = infoTokens[2].charAt(0);

        m_player = new Player(name, smallCharacter);
        m_player.setPositionX(x);
        m_player.setPositionY(y);

        // Replace character at enemy x and y with enemy
        m_data[y][x] = smallCharacter;

        scanner.close();

        // Close info file
        infoStream.close();
    }

    /*
     */
    public void loadDoors(final String _root, AssetManager _assetManager) throws IOException
    {
        // Clear door list
        m_doorList.clear();

        // Open info file
        InputStream infoStream = _assetManager.open(_root + "/" + "door.tinf");

        // Go through info file line by line
        Scanner scanner = new Scanner(infoStream);
        while(scanner.hasNext())
        {
            // Get door info
            final String[] lineTokens = scanner.nextLine().replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
            final String name = lineTokens[0];
            final String[] infoTokens = lineTokens[1].split(",");

            // Create door
            final int x = Integer.parseInt(infoTokens[0]);
            final int y = Integer.parseInt(infoTokens[1]);
            final char smallCharacter = infoTokens[2].charAt(0);

            Door door = new Door(name, smallCharacter);
            door.setPositionX(x);
            door.setPositionY(y);

            // Add door to list
            m_doorList.add(door);

            // Replace character at door x and y with door
            m_data[y][x] = smallCharacter;
        }
        scanner.close();

        // Close info file
        infoStream.close();
    }

    /**
     */
    public void loadEnemies(final String _root, AssetManager _assetManager) throws IOException
    {
        // Clear enemy list
        m_enemyList.clear();

        // Open info file
        InputStream infoStream = _assetManager.open(_root + "/" + "enemy.tinf");

        // Go through info file line by line
        Scanner scanner = new Scanner(infoStream);
        while(scanner.hasNext())
        {
            // Get enemy info
            final String[] lineTokens = scanner.nextLine().replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
            final String name = lineTokens[0];
            final String[] infoTokens = lineTokens[1].split(",");

            final String enemyDir = (_root + "/" + name.toLowerCase());

            // Create enemy
            final int x = Integer.parseInt(infoTokens[0]);
            final int y = Integer.parseInt(infoTokens[1]);
            final char smallCharacter = infoTokens[2].charAt(0);
            final int maxHP = Integer.parseInt(infoTokens[3]);

            Enemy enemy = new Enemy(name, maxHP, smallCharacter);
            enemy.setPositionX(x);
            enemy.setPositionY(y);
            enemy.loadLargeCharacter(enemyDir, _assetManager);

            // Add enemy to list
            m_enemyList.add(enemy);

            // Replace character at enemy x and y with enemy
            m_data[y][x] = smallCharacter;
        }
        scanner.close();

        // Close info file
        infoStream.close();
    }

    /**
     */
    public void loadItems(final String _root, AssetManager _assetManager) throws IOException
    {
        // Clear item list
        m_itemList.clear();

        // Open info file
        InputStream infoStream = _assetManager.open(_root + "/" + "item.tett");

        // Go through info file line by line
        Scanner scanner = new Scanner(infoStream);
        while(scanner.hasNext())
        {
            // Get item info
            final String[] lineTokens = scanner.nextLine().replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
            final String name = lineTokens[0];
            final String[] infoTokens = lineTokens[1].split(",");

            final String itemDir = (_root + "/" + name.toLowerCase());

            // Create Item
            final int x = Integer.parseInt(infoTokens[0]);
            final int y = Integer.parseInt(infoTokens[1]);
            final char smallCharacter = infoTokens[2].charAt(0);
            final int attack = Integer.parseInt(infoTokens[3]);
            final int defense = Integer.parseInt(infoTokens[4]);
            final int speed = Integer.parseInt(infoTokens[5]);

            Item item = new Item(name, smallCharacter);
            item.setPositionX(x);
            item.setPositionY(y);
            item.loadLargeCharacter(itemDir, _assetManager);

            // Add item to list
            m_itemList.add(item);

            // Replace character at item x and y with item
            m_data[y][x] = smallCharacter;
        }
        scanner.close();

        // Close info file
        infoStream.close();
    }

} // class Map
