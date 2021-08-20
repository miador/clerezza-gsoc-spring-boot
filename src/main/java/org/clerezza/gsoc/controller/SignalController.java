package org.clerezza.gsoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.clerezza.signal.file.FileOperations;
import org.apache.clerezza.signal.graph.SignalGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping( "/signal/messages" )
public class SignalController {

    private static final Logger logger = LoggerFactory.getLogger( SignalController.class );
    public static String filename = System.getProperty( "user.dir" ) + "/signal.ttl";

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public void saveMessage( @RequestBody String data ) throws IOException {
        ObjectNode json = new ObjectMapper().readValue( data, ObjectNode.class );
        createFileIfNotExist( filename );
        SignalGraph.buildGraph( json, filename );
    }

    @GetMapping
    public String printGraph() {
        return FileOperations.printGraph( filename );
    }

    /**
     * @param filename, with the given filename a new file will be created if file doesn't exist.
     *                  Will do nothing if file already exists.
     */
    public static void createFileIfNotExist( String filename ) {
        try {
            File file = new File( filename );
            if ( file.createNewFile() ) {
                logger.info( "File created at: " + "\"" + filename + "\"" );
            } else {
                logger.info( "Existing file at the directory: " + "\"" + filename + "\"" + " will be overwritten by the serializer" );
            }
        } catch ( Exception e ) {
            System.out.println( e.getLocalizedMessage() );
        }
    }

}
