package com.hood.pictures;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandLineParser
{
    private static final Logger logger = LoggerFactory.getLogger( CommandLineParser.class );

    public CommandLine parseCommandLine( final String[] args )
    {
        final Options options = new Options();
        options.addOption( "h", Arguments.HEIGHT, true, "Desired height of resized pictures.  Defaults to 600." );
        options.addOption( "i",
                           Arguments.INPUT_DIRECTORY,
                           true,
                           "Directory to search for files, may be specified multiple times." );
        options.addOption( "o",
                           Arguments.OUTPUT_DIRECTORY,
                           true,
                           "Directory where resized pictures are written.  Defaults to first input directory." );
        options.addOption( "w",
                           Arguments.WIDTH,
                           true,
                           "Desired maximum width of resized pictures.  Applied after height scaling if the picture is too wide.  Defaults to 800." );
        options.addOption( "x",
                           Arguments.EXTENSION,
                           true,
                           "Extension that files must have, may be specified multiple times.  Defaults to "
                                   + Defaults.EXTENSIONS.toString() + "." );

        final Parser parser = new BasicParser();
        final CommandLine commandLine;
        try
        {
            commandLine = parser.parse( options, args );
        }
        catch ( final ParseException e )
        {
            logger.error( "Error while parsing command line!", e );
            return null;
        }
        return commandLine;
    }
}
