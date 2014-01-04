package com.hood.pictures;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PictureResizerFactory
{
    private static final Logger logger = LoggerFactory.getLogger( PictureResizerFactory.class );

    public PictureResizer createFromCommandLine( final CommandLine commandLine ) throws Exception
    {
        final int height;
        final String[] inputDirectories;
        final String outputDirectory;
        final int width;
        final String[] extensions;

        if ( commandLine.hasOption( Arguments.HEIGHT ) )
        {
            height = Integer.parseInt( commandLine.getOptionValue( Arguments.HEIGHT ) );
        }
        else
        {
            height = Defaults.HEIGHT;
        }

        if ( commandLine.hasOption( Arguments.INPUT_DIRECTORY ) )
        {
            inputDirectories = commandLine.getOptionValues( Arguments.INPUT_DIRECTORY ).clone();
        }
        else
        {
            logger.error( "Input Directory(ies) must be specified!" );
            throw new Exception( "Input Directory(ies) not specified!" );
        }

        if ( commandLine.hasOption( Arguments.OUTPUT_DIRECTORY ) )
        {
            outputDirectory = commandLine.getOptionValue( Arguments.OUTPUT_DIRECTORY );
            new File( outputDirectory ).mkdirs();
        }
        else
        {
            outputDirectory = inputDirectories[0];
        }

        if ( commandLine.hasOption( Arguments.WIDTH ) )
        {
            width = Integer.parseInt( commandLine.getOptionValue( Arguments.WIDTH ) );
        }
        else
        {
            width = Defaults.WIDTH;
        }

        if ( commandLine.hasOption( Arguments.EXTENSION ) )
        {
            extensions = commandLine.getOptionValues( Arguments.EXTENSION ).clone();
        }
        else
        {
            extensions = Defaults.EXTENSIONS;
        }

        return new PictureResizer( inputDirectories, extensions, outputDirectory, width, height );
    }
}
