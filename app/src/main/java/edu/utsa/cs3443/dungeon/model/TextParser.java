package edu.utsa.cs3443.dungeon.model;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 */
public class TextParser
{
    private InputStream             m_stream;       //
    private Scanner                 m_scanner;      //

    /**
     */
    public TextParser(final String _path, AssetManager _assetManager) throws IOException
    {
        // Open the input stream
        m_stream = _assetManager.open(_path);

        // Create a new scanner
        m_scanner = new Scanner(m_stream);
    }

    /**
     */
    public final String next()
    {
        while (m_scanner.hasNext())
        {
            final String line = m_scanner.nextLine();
            if ((line.length() != 0) && ((line.charAt(0) != '/') && (line.charAt(1) != '/')))
                return line;

        } return null;
    }

    /**
     */
    public void close() throws IOException
    {
        // Close the scanner
        m_scanner.close();

        // Close the input stream
        m_stream.close();
    }

} // class TextParser
