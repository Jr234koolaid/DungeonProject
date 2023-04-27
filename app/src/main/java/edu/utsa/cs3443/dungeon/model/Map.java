package edu.utsa.cs3443.dungeon.model;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 */
public class Map
{
    // Class variables
    private static final int        MAX_MAP_WIDTH = 35;     //
    private static final int        MAX_MAP_HEIGHT = 15;    //

    // Member variables
    private char[][]                m_data;         //
    private Player                  m_player;       //
    private ArrayList<Enemy>        m_enemyList;    //
    private ArrayList<Item>         m_itemList;     //

    /**
     */
    public Map()
    {
        m_enemyList = new ArrayList<>();
        m_itemList = new ArrayList<>();
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
        final int setX = _player.getX();
        final int setY = _player.getY();

        if (m_data[setY][setX] == ' ')
        {
            // Replace player's previous spot with an empty space
            m_data[m_player.getY()][m_player.getX()] = ' ';

            // Set player spot
            m_player.setX(setX);
            m_player.setY(setY);

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
    public void loadData(final String _root, AssetManager _assetManager) throws IOException
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
    void loadPlayer(final String _root, AssetManager _assetManager) throws IOException
    {
        // Open player file
        InputStream infoStream = _assetManager.open(_root + "/" + "player.tett");

        // Go through the file
        Scanner scanner = new Scanner(infoStream);

        // Get player info
        final String[] lineTokens = scanner.nextLine().replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
        final String name = lineTokens[0];
        final String[] infoTokens = lineTokens[1].split(",");

        // Create enemy
        final int x = Integer.parseInt(infoTokens[0]);
        final int y = Integer.parseInt(infoTokens[1]);
        final char smallCharacter = infoTokens[2].charAt(0);

        m_player = new Player(name, smallCharacter);
        m_player.setX(x);
        m_player.setY(y);

        // Replace character at enemy x and y with enemy
        m_data[y][x] = smallCharacter;

        scanner.close();

        // Close info file
        infoStream.close();
    }

    /**
     */
    public void loadEnemies(final String _path, AssetManager _assetManager) throws IOException
    {
        // Clear enemy list
        m_enemyList.clear();

        // Open info file
        InputStream infoStream = _assetManager.open(_path + "/" + "enemy.tett");

        // Go through info file line by line
        Scanner scanner = new Scanner(infoStream);
        while(scanner.hasNext())
        {
            // Get enemy info
            final String[] lineTokens = scanner.nextLine().replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
            final String name = lineTokens[0];
            final String[] infoTokens = lineTokens[1].split(",");

            final String enemyDir = (_path + "/" + name.toLowerCase());

            // Create enemy
            final int x = Integer.parseInt(infoTokens[0]);
            final int y = Integer.parseInt(infoTokens[1]);
            final char smallCharacter = infoTokens[2].charAt(0);
            final int maxHP = Integer.parseInt(infoTokens[3]);

            Enemy enemy = new Enemy(name, maxHP, smallCharacter);
            enemy.setX(x);
            enemy.setY(y);
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
    public void loadItems(final String _path, AssetManager _assetManager) throws IOException
    {
        // Clear item list
        m_itemList.clear();

        // Open info file
        InputStream infoStream = _assetManager.open(_path + "/" + "item.tett");

        // Go through info file line by line
        Scanner scanner = new Scanner(infoStream);
        while(scanner.hasNext())
        {
            // Get item info
            final String[] lineTokens = scanner.nextLine().replaceAll(" ", "").replaceAll("\\}", "").split("\\{");
            final String name = lineTokens[0];
            final String[] infoTokens = lineTokens[1].split(",");

            final String itemDir = (_path + "/" + name.toLowerCase());

            // Create Item
            final int x = Integer.parseInt(infoTokens[0]);
            final int y = Integer.parseInt(infoTokens[1]);
            final char smallCharacter = infoTokens[2].charAt(0);
            final int attack = Integer.parseInt(infoTokens[3]);
            final int defense = Integer.parseInt(infoTokens[4]);
            final int speed = Integer.parseInt(infoTokens[5]);

            Item item = new Item(name, smallCharacter);
            item.setX(x);
            item.setY(y);
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
