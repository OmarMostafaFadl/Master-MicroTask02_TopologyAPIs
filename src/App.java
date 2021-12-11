import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings({"unused", "unchecked"})


public class App {

    public static void main(String[] args) throws Exception {

        Topologies topObj = new Topologies();

        Topologies.queryTopologies();
        

    }       
}

