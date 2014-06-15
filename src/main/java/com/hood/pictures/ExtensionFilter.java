package com.hood.pictures;

import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Path;
import java.util.List;

public class ExtensionFilter implements Filter<Path>
{
    private final List<String> extensions;

    public ExtensionFilter( final List<String> extensions )
    {
        this.extensions = extensions;
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
