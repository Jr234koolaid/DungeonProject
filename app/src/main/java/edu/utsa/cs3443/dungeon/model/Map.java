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
import java.util.ArrayList;

import edu.utsa.cs3443.dungeon.R;

/**
 */
public class Map extends TableLayout
{
    // Class variables
    protected static final int      MAX_MAP_WIDTH = 30;     //
    protected static final int      MAX_MAP_HEIGHT = 15;    //

    // Member variables
    private int                     m_nextFloor;    //
    private int                     m_nextMap;      //
    private char[][]                m_data;         //
    private final ArrayList<Door>         m_doorList;     //
    private final ArrayList<Enemy>        m_enemyList;    //
    private final ArrayList<Item>         m_itemList;     //

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

    /**
     */
    public void updatePlayer(final int _setX, final int _setY)
    {
        // Check if player can move
        if (m_data[_setY][_setX] == ' ')
        {
            // Get player
            Player player = Player.getInstance();

            final int x = player.getX();
            final int y = player.getY();

            // Replace player's previous spot with an empty space
            m_data[y][x] = ' ';

            // Set player spot
            player.setX(_setX);
            player.setY(_setY);

            m_data[_setY][_setX] = player.getSmallCharacter();
        }
    }

    /**
     */
    public void removeEnemy(final Enemy _enemy)
    {
        // Remove enemy from list
        for (final Enemy enemy : m_enemyList)
        {
            if (enemy.getName().equals(_enemy.getName()))
            {
                m_enemyList.remove(enemy);
                break;
            }
        }

        // Reset map location
        final int setX = _enemy.getX();
        final int setY = _enemy.getY();

        m_data[setY][setX] = ' ';
    }

    /**
     */
    public void removeItem(final Item _item)
    {
        // Remove item from list
        for (final Item item : m_itemList)
        {
            if (item.getName().equals(_item.getName()))
            {
                m_itemList.remove(item);
                break;
            }
        }

        // Reset map location
        final int setX = _item.getX();
        final int setY = _item.getY();

        m_data[setY][setX] = ' ';
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
        final float textWidth = 12.f;
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
                textView.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                //TODO: fix this
                // textView.setTextColor(context.getColorStateList(R.color.teal_700));
                textView.setTextColor(0xFF03DAC5);
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
        // Go through map file
        TextParser parser = new TextParser((_root + "/" + "map.tinf"), _assetManager);

        // Get map info
        final String line = parser.next();
        if (line != null)
        {
            final String[] lineTokens = line.replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
            final String name = lineTokens[0];
            final String[] infoTokens = lineTokens[1].split(",");

            // Set map info
            m_nextFloor = Integer.parseInt(infoTokens[0]);
            m_nextMap = Integer.parseInt(infoTokens[1]);
        }

        // Close parser
        parser.close();
    }

    /**
     */
    public void loadMap(final String _root, AssetManager _assetManager) throws IOException
    {
        // Allocate data
        m_data = new char[MAX_MAP_HEIGHT][MAX_MAP_WIDTH];

        // Go through map data file
        TextParser parser = new TextParser((_root + "/" + "map.tmap"), _assetManager);

        // Get map data
        int sizeY = 0;

        String line = null;
        while (((line = parser.next()) != null) && (sizeY < MAX_MAP_HEIGHT))
        {
            // Get characters from string
            int sizeX = 0;

            final int min = Math.min(line.length(), MAX_MAP_WIDTH);
            for (; sizeX < min; sizeX++)
                m_data[sizeY][sizeX] = line.charAt(sizeX);

            // Fill in missing characters
            if (sizeX != MAX_MAP_WIDTH)
            {
                for (; sizeX < MAX_MAP_WIDTH; sizeX++)
                    m_data[sizeY][sizeX] = ' ';
            }

            // Increment size
            sizeY++;
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

        // Close parser
        parser.close();
    }

    /*
     */
    public void loadPlayer(final String _root, AssetManager _assetManager) throws IOException
    {
        // Go through player file
        TextParser parser = new TextParser((_root + "/" + "player.tinf"), _assetManager);

        // Get player info
        final String line = parser.next();
        if (line != null)
        {
            final String[] lineTokens = line.replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
            final String name = lineTokens[0];
            final String[] infoTokens = lineTokens[1].split(",");

            // Add to player info
            final int x = Integer.parseInt(infoTokens[0]);
            final int y = Integer.parseInt(infoTokens[1]);

            Player player = Player.getInstance();
            player.setX(x);
            player.setY(y);

            // Replace character at player x and y with player
            m_data[y][x] = player.getSmallCharacter();
        }

        // Close the parser
        parser.close();
    }

    /*
     */
    public void loadDoors(final String _root, AssetManager _assetManager) throws IOException
    {
        // Clear door list
        m_doorList.clear();

        // Go through door file
        TextParser parser = new TextParser((_root + "/" + "door.tinf"), _assetManager);

        // Get door info
        String line = null;
        while ((line = parser.next()) != null)
        {
            final String[] lineTokens = line.replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
            final String name = lineTokens[0];
            final String[] infoTokens = lineTokens[1].split(",");

            // Create door
            final int x = Integer.parseInt(infoTokens[0]);
            final int y = Integer.parseInt(infoTokens[1]);
            final char smallCharacter = infoTokens[2].charAt(0);

            Door door = new Door(name, smallCharacter);
            door.setX(x);
            door.setY(y);

            // Add door to list
            m_doorList.add(door);

            // Replace character at door x and y with door
            m_data[y][x] = smallCharacter;
        }

        // Close parser
        parser.close();
    }

    /**
     */
    public void loadEnemies(final String _root, AssetManager _assetManager) throws IOException
    {
        // Clear enemy list
        m_enemyList.clear();

        // Go through enemy file
        TextParser parser = new TextParser((_root + "/" + "enemy.tinf"), _assetManager);

        // Get enemy info
        String line = null;
        while ((line = parser.next()) != null)
        {
            final String[] lineTokens = line.replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
            final String name = lineTokens[0];
            final String[] infoTokens = lineTokens[1].split(",");

            final String enemyDir = (_root + "/" + name.toLowerCase());

            // Create enemy
            final int x = Integer.parseInt(infoTokens[0]);
            final int y = Integer.parseInt(infoTokens[1]);
            final char smallCharacter = infoTokens[2].charAt(0);
            final int maxHP = Integer.parseInt(infoTokens[3]);
            final int attack = Integer.parseInt(infoTokens[4]);

            Enemy enemy = new Enemy(name, maxHP, smallCharacter, attack);
            enemy.setX(x);
            enemy.setY(y);
            enemy.loadLargeCharacter((enemyDir + "/large_character.tett"), _assetManager);

            // Add enemy to list
            m_enemyList.add(enemy);

            // Replace character at enemy x and y with enemy
            m_data[y][x] = smallCharacter;
        }

        // Close parser
        parser.close();
    }

    /**
     */
    public void loadItems(final String mapDir, final String armorDir, final String weaponDir, AssetManager _assetManager) throws IOException
    {
        // Clear item list
        m_itemList.clear();

        // Create the generators
        WeaponGenerator wGen = new WeaponGenerator(weaponDir, _assetManager);
        ArmorGenerator aGen = new ArmorGenerator(armorDir, _assetManager);
        ModifierGenerator mGen = new ModifierGenerator();


        // Go through item file
        TextParser parser = new TextParser((mapDir + "/" + "item.tinf"), _assetManager);

        // Get item info
        String line = null;
        while ((line = parser.next()) != null)
        {
            final String[] lineTokens = line.replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
            final String name = lineTokens[0];
            final String[] infoTokens = lineTokens[1].split(",");

            // Create Item
            final int x = Integer.parseInt(infoTokens[0]);
            final int y = Integer.parseInt(infoTokens[1]);

            // Generate items
            switch(name)
            {
                case "WEAPON":
                {
                    // Get randomly generated weapon
                    Weapon weapon = wGen.generate();
                    weapon.setX(x);
                    weapon.setY(y);
                    weapon.applyModifier(mGen.generate());

                    // Add to list
                    m_itemList.add(weapon);

                    // Replace character at item x and y with item
                    m_data[y][x] = weapon.getSmallCharacter();

                } break;

                case "ARMOR":
                {
                    // Get randomly generated armor
                    Armor armor = aGen.generate();
                    armor.setX(x);
                    armor.setY(y);
                    armor.applyModifier(mGen.generate());

                    // Add to list
                    m_itemList.add(armor);
                    
                    // Replace character at item x and y with item
                    m_data[y][x] = armor.getSmallCharacter();
                    
                } break;
                
                case "HEALING":
                    HealingItem hItem = new HealingItem("potion", _assetManager);
                    hItem.setX(x);
                    hItem.setY(y);

                    m_itemList.add(hItem);

                    m_data[y][x] = hItem.getSmallCharacter();
                    break;

                default:
                    break;
            }
        }

        // Close parser
        parser.close();
    }

} // class Map
