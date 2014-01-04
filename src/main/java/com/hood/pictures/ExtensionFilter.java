package com.hood.pictures;

import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Path;

public class ExtensionFilter implements Filter<Path>
{
    private final String[] extensions;

    public ExtensionFilter( final String[] extensions )
    {
        this.extensions = extensions.clone();
    }

    @Override
    public boolean accept( final Path entry ) throws IOException
    {
        for ( final String extension : this.extensions )
        {
            final String fileName = entry.toFile().getName();
            if ( fileName.endsWith( "." + extension ) )
            {
                return true;
            }
        }
        return false;
    }

}
