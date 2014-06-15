package com.hood.pictures;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PictureResizerFactory
{
    private static final Logger logger = LoggerFactory.getLogger( PictureResizerFactory.class );

    public PictureResizer createFromCommandLine( final CommandLine commandLine ) throws Exception
    {
        final int height;
        final List<String> inputDirectories = new ArrayList<>();
        String outputDirectory;
        final int width;
        final List<String> extensions = new ArrayList<>();

        inputDirectories.addAll( Arrays.asList( commandLine.getOptionValues( Arguments.INPUT_DIRECTORY ).clone() ) );
        outputDirectory = commandLine.getOptionValue( Arguments.OUTPUT_DIRECTORY );
        new File( outputDirectory ).mkdirs();

        if ( inputDirectories.contains( outputDirectory ) )
        {
            logger.error( "The output directory \"{}\" is included in the inputDirectories {}.  This will overwrite pictures!",
                          outputDirectory,
                          inputDirectories );
            throw new IllegalArgumentException( "Output directory cannot also be an input directory!" );
        }

        if ( commandLine.hasOption( Arguments.EXTENSION ) )
        {
            extensions.addAll( Arrays.asList( commandLine.getOptionValues( Arguments.EXTENSION ).clone() ) );
        }
        else
        {
            extensions.addAll( Defaults.EXTENSIONS );
        }

        if ( commandLine.hasOption( Arguments.HEIGHT ) )
        {
            height = Integer.parseInt( commandLine.getOptionValue( Arguments.HEIGHT ) );
        }
        else
        {
            height = Defaults.HEIGHT;
        }

        if ( commandLine.hasOption( Arguments.WIDTH ) )
        {
            width = Integer.parseInt( commandLine.getOptionValue( Arguments.WIDTH ) );
        }
        else
        {
            width = Defaults.WIDTH;
        }

        return new PictureResizer( inputDirectories, extensions, outputDirectory, width, height );
    }
}
