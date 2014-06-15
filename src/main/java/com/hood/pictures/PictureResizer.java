package com.hood.pictures;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.cli.CommandLine;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PictureResizer
{
    private static final Logger logger = LoggerFactory.getLogger( PictureResizer.class );

    public static void main( final String[] args ) throws Exception
    {
        final CommandLine commandLine = new CommandLineParser().parseCommandLine( args );
        if ( commandLine == null )
        {
            System.exit( 1 );
        }
        final PictureResizer pictureResizer = new PictureResizerFactory().createFromCommandLine( commandLine );
        pictureResizer.resize();
    }

    private final List<String> inputDirectories;
    private final List<String> extensions;
    private final String outputDirectory;
    private final int width;
    private final int height;

    /**
     * Resize all pictures in @{code inputDirectories} that have any listed {@code extensions}. Output them to
     * {@code outputDirectory} with a width and height no greater than {@code width} and {@code height}.
     * 
     * @param inputDirectories
     *            Directories to search for pictures
     * @param extensions
     *            Extensions that pictures may have
     * @param outputDirectory
     *            Directory for resized pictures
     * @param width
     *            Maximum width for resized pictures. This is applied after the maximum
     * @param height
     */
    public PictureResizer( final List<String> inputDirectories,
                           final List<String> extensions,
                           final String outputDirectory,
                           final int width,
                           final int height )
    {
        this.inputDirectories = inputDirectories;
        this.extensions = extensions;
        this.outputDirectory = outputDirectory;
        this.width = width;
        this.height = height;
    }

    public void resize() throws IOException
    {
        final DirectoryStream.Filter<Path> extensionFilter = new ExtensionFilter( this.extensions );

        for ( final String inputDirectory : this.inputDirectories )
        {
            final Path inputPath = FileSystems.getDefault().getPath( inputDirectory );

            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream( inputPath, extensionFilter ))
            {
                for ( final Path path : directoryStream )
                {
                    logger.info( "Reading image: {}", path );
                    final BufferedImage originalImage = ImageIO.read( path.toFile() );

                    logger.info( "Image size: {}x{}", originalImage.getWidth(), originalImage.getHeight() );

                    final BufferedImage scaledImage;
                    logger.info( "Width factor: {}; Height factor: {}",
                                 originalImage.getWidth() / (double) this.width,
                                 originalImage.getHeight() / (double) this.height );
                    if ( originalImage.getHeight() / (double) this.height > originalImage.getWidth()
                            / (double) this.width )
                    {
                        scaledImage =
                                Scalr.resize( originalImage, Method.ULTRA_QUALITY, Mode.FIT_TO_HEIGHT, this.height );
                        logger.info( "New dimensions: {}x{}", scaledImage.getWidth(), scaledImage.getHeight() );
                    }
                    else
                    {
                        scaledImage = Scalr.resize( originalImage, Method.ULTRA_QUALITY, Mode.FIT_TO_WIDTH, this.width );
                        logger.info( "New dimensions: {}x{}", scaledImage.getWidth(), scaledImage.getHeight() );
                    }

                    final String outputFileName =
                            this.outputDirectory + FileSystems.getDefault().getSeparator() + path.toFile().getName();
                    logger.info( "Writing image: {}", outputFileName );
                    ImageIO.write( scaledImage, "JPG", new File( outputFileName ) );
                }
            }
        }
    }
}
