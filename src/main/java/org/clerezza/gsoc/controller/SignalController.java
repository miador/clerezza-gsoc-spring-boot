package org.clerezza.gsoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.clerezza.signal.graph.SignalGraph;
import org.clerezza.gsoc.util.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping( "/signal/messages" )
public class SignalController {

    public static String filename = System.getProperty( "user.dir" ) + "/signal.ttl";

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public void saveMessage( @RequestBody String data ) throws IOException {
        ObjectNode json = new ObjectMapper().readValue( data, ObjectNode.class );
        FileUtils.createFileIfNotExist( filename );
        SignalGraph.buildGraph( json, filename );
    }

    @GetMapping
    public String printGraph() {
        return FileUtils.printGraph( filename );
    }

}
