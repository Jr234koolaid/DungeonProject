package edu.utsa.cs3443.dungeon.model;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 */
public class Map
{
    // Class variables
    private static final int MAX_MAP_WIDTH =    35; //
    private static final int MAX_MAP_HEIGHT =   15; //

    // Member variables
    private char[][]    m_data; //

    /**
     */
    public Map()
    {
        m_data = new char[MAX_MAP_HEIGHT][MAX_MAP_WIDTH];
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
    public void load(final String _path, AssetManager _assetManager)
    {
        // Open data file
        InputStream data;
        try
        {
            data = _assetManager.open(_path);
        }
        catch (IOException _e)
        {
            throw new RuntimeException(_e);
        }

        // Go through the file
        Scanner scanner = new Scanner(data);

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
        try
        {
            data.close();
        }
        catch (IOException _e)
        {
            throw new RuntimeException(_e);
        }
    }

} // class Map
