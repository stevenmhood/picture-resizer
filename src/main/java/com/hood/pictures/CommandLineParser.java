package com.hood.pictures;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
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
        final Option height =
                new Option( "h", Arguments.HEIGHT, true, "Desired height of resized pictures.  Defaults to 600." );
        options.addOption( height );

        final Option inputDirectory =
                new Option( "i",
                            Arguments.INPUT_DIRECTORY,
                            true,
                            "Required.  Directory to search for files, may be specified multiple times." );
        inputDirectory.setRequired( true );
        options.addOption( inputDirectory );

        final Option outputDirectory =
                new Option( "o",
                            Arguments.OUTPUT_DIRECTORY,
                            true,
                            "Directory where resized pictures are written.  Defaults to first input directory." );
        outputDirectory.setRequired( true );
        options.addOption( outputDirectory );

        final Option width =
                new Option( "w",
                            Arguments.WIDTH,
                            true,
                            "Desired maximum width of resized pictures.  Applied after height scaling if the picture is too wide.  Defaults to 800." );
        options.addOption( width );

        final Option extension =
                new Option( "x",
                            Arguments.EXTENSION,
                            true,
                            "Extension that files must have, may be specified multiple times.  Defaults to "
                                    + Defaults.EXTENSIONS + "." );
        options.addOption( extension );

        final Parser parser = new BasicParser();
        final CommandLine commandLine;
        try
        {
            commandLine = parser.parse( options, args );
        }
        catch ( final MissingOptionException e )
        {
            new HelpFormatter().printHelp( "PictureResizer", options, true );
            return null;
        }
        catch ( final ParseException e )
        {
            logger.error( "Error while parsing command line!", e );
            return null;
        }
        return commandLine;
    }
}
