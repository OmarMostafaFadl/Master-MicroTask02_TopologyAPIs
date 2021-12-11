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


@SuppressWarnings({"unchecked"})

public class Topologies {

    static void ReadJSON(String FILENAME){

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(FILENAME))
        {

            Object obj = jsonParser.parse(reader);              //Read JSON File and saving it into an Object

            JSONObject topology = (JSONObject) obj;             //Transforming the obj type to JSONObject type.

            String topologyID = (String) topology.get("id");    //Getting the id key.

            topologiesIDsList.add(topologyID);                  //Adding this topologyID to the static list of all IDs for quering

            JSONArray topologyComponents = (JSONArray) topology.get("components");       //Getting the components array from the topology object
            
            List<String> componentTypeList = new ArrayList<>();            //Initilizing a list which will carry all the components' names
            List<String> componentIDList = new ArrayList<>();
            

            for(int i=0; i<topologyComponents.size(); i++){


                JSONObject component = (JSONObject) topologyComponents.get(i);
                String componentType = (String) component.get("type");
                String componentID = (String) component.get("id");


                if (componentType.equals("resistor")){                           //Checks if the component is a Resistor

                    JSONObject netlist = (JSONObject) component.get("netlist");
                    JSONObject resistance = (JSONObject) component.get("resistance");

                    String t1 = (String) netlist.get("t1");
                    String t2 = (String) netlist.get("t2");

                    Long defaultRes = (Long) resistance.get("default");
                    Long minRes = (Long) resistance.get("min");
                    Long maxRes = (Long) resistance.get("max");

                    //Saving all data in memory
                    Resistor.resistors_ids_List.add((componentID));
                    Resistor.resistors_default_resistance_list.add(defaultRes);
                    Resistor.resistors_min_resistance_list.add(minRes);
                    Resistor.resistors_max_resistance_list.add(maxRes);
                    Resistor.resistors_t1_list.add(t1);
                    Resistor.resistors_t2_list.add(t2);
                    Resistor.resistors_topology_list.add(topologyID);
                    
                }
                else if (componentType.equals("nmos")){                         //Checks if the component is a nmos

                    JSONObject netlist = (JSONObject) component.get("netlist");
                    JSONObject m = (JSONObject) component.get("m(l)");

                    String drain = (String) netlist.get("drain");
                    String gate = (String) netlist.get("gate");
                    String source = (String) netlist.get("source");

                    Double defaultM = (Double) m.get("default");
                    Long minM = (Long) m.get("min");
                    Long maxM = (Long) m.get("max"); 

                    //Saving all data in memory
                    Nmos.nmos_ids_List.add(componentID);
                    Nmos.nmos_default_m_list.add(defaultM);
                    Nmos.nmos_min_m_list.add(minM);
                    Nmos.nmos_max_m_list.add(maxM);
                    Nmos.nmos_drain_list.add(drain);
                    Nmos.nmos_gate_list.add(gate);
                    Nmos.nmos_source_list.add(source);
                    Nmos.nmos_topology_list.add(topologyID);

                }

                componentTypeList.add(componentType);
                componentIDList.add(componentID);
 
            }

        }

        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        
    }

    static void writeJSON(String FILENAME, String ID){

        JSONObject topologyDetails = new JSONObject();
        JSONArray componentsList = new JSONArray();

        int needed_index_res = Resistor.resistors_topology_list.indexOf(ID);
        int needed_index_nmos = Nmos.nmos_topology_list.indexOf(ID);

        topologyDetails.put("id", ID);
        topologyDetails.put("components", componentsList);

        JSONObject resistor = new JSONObject();
        JSONObject nmos = new JSONObject();


        //Resistor Info
        JSONObject resistorResistance = new JSONObject();
        JSONObject resistorNetlist = new JSONObject();

        //Getting the resistor info from the saved lists (memory).
        long defaultRes = Resistor.resistors_default_resistance_list.get(needed_index_res);
        long minRes = Resistor.resistors_min_resistance_list.get(needed_index_res);
        long maxRes = Resistor.resistors_max_resistance_list.get(needed_index_res);

        String resistorT1 = Resistor.resistors_t1_list.get(needed_index_res);
        String resistorT2 = Resistor.resistors_t2_list.get(needed_index_res);

        resistorResistance.put("default", defaultRes);
        resistorResistance.put("min", minRes);
        resistorResistance.put("max", maxRes);

        resistorNetlist.put("t1", resistorT1);
        resistorNetlist.put("t2", resistorT2);

        resistor.put("type", "resistor");
        resistor.put("id", Resistor.resistors_ids_List.get(needed_index_res));
        resistor.put("resistance", resistorResistance);
        resistor.put("netlist", resistorNetlist);


        //nmos Info
        JSONObject nmosM = new JSONObject();
        JSONObject nmosNetlist = new JSONObject();

        double defaultM = Nmos.nmos_default_m_list.get(needed_index_nmos);
        long minM = Nmos.nmos_min_m_list.get(needed_index_nmos);
        long maxM = Nmos.nmos_max_m_list.get(needed_index_nmos);

        String drain = Nmos.nmos_drain_list.get(needed_index_nmos);
        String gate = Nmos.nmos_gate_list.get(needed_index_nmos);
        String source = Nmos.nmos_source_list.get(needed_index_nmos);

        nmosM.put("default", defaultM);
        nmosM.put("min", minM);
        nmosM.put("max", maxM);

        nmosNetlist.put("drain", drain);
        nmosNetlist.put("gate", gate);
        nmosNetlist.put("source", source);

        nmos.put("type", "nmos");
        nmos.put("id", Nmos.nmos_ids_List.get(needed_index_nmos));
        nmos.put("m(l)", nmosM);
        nmos.put("netlist", nmosNetlist);


        componentsList.add(resistor);
        componentsList.add(nmos);


        //Write JSON file
        try (FileWriter file = new FileWriter(FILENAME)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(topologyDetails.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void queryTopologies(){

        if(topologiesIDsList.size() == 0){
            System.out.println("There are no Topologies in memory");
        }
        else{
            for(int i=0; i<topologiesIDsList.size(); i++){
                System.out.print("Topology found with ID : ");
                System.out.println(topologiesIDsList.get(i));
            }
        }


    }

    static void deleteTopology(String ID){

        int needed_index_top = topologiesIDsList.indexOf(ID);
        if(needed_index_top != -1){
            topologiesIDsList.remove(needed_index_top);
        }
        
        int needed_index = Resistor.resistors_topology_list.indexOf(ID);

        if(needed_index == -1){
            needed_index = Nmos.nmos_topology_list.indexOf(ID);

            if(needed_index == -1){
                System.out.println("The Topology Doesn't Exist !!");
            }
            else{
                Nmos.nmos_topology_list.remove(needed_index);
                Nmos.nmos_default_m_list.remove(needed_index);
                Nmos.nmos_min_m_list.remove(needed_index);
                Nmos.nmos_max_m_list.remove(needed_index);
                Nmos.nmos_drain_list.remove(needed_index);
                Nmos.nmos_gate_list.remove(needed_index);
                Nmos.nmos_source_list.remove(needed_index);
                Nmos.nmos_ids_List.remove(needed_index);
            }

        }
        else{
            Resistor.resistors_topology_list.remove(needed_index);
            Resistor.resistors_ids_List.remove(needed_index);
            Resistor.resistors_default_resistance_list.remove(needed_index);
            Resistor.resistors_min_resistance_list.remove(needed_index);
            Resistor.resistors_max_resistance_list.remove(needed_index);
            Resistor.resistors_t1_list.remove(needed_index);
            Resistor.resistors_t2_list.remove(needed_index);

            needed_index = Nmos.nmos_topology_list.indexOf(ID);

            if(needed_index == -1){
                System.out.println("Removed Topology !");
            }
            else{
                Nmos.nmos_topology_list.remove(needed_index);
                Nmos.nmos_default_m_list.remove(needed_index);
                Nmos.nmos_min_m_list.remove(needed_index);
                Nmos.nmos_max_m_list.remove(needed_index);
                Nmos.nmos_drain_list.remove(needed_index);
                Nmos.nmos_gate_list.remove(needed_index);
                Nmos.nmos_source_list.remove(needed_index);
                Nmos.nmos_ids_List.remove(needed_index);

                System.out.println("Removed Topology !");
            }

        }

    
    }
    
    static void queryDevices(String ID){

        int needed_index = Resistor.resistors_topology_list.indexOf(ID);

        if(needed_index != -1){
            System.out.println("This Topology Contains Resistors !!");
        }

        needed_index = Nmos.nmos_topology_list.indexOf(ID);

        if(needed_index != -1){
            System.out.println("This Topology Contains nmos !!");
        }

    }

    static void queryDevicesWithNetlistNode(String ID, String NetlistNode){

        if(NetlistNode == "t1" || NetlistNode == "t2"){

            int needed_index = Resistor.resistors_topology_list.indexOf(ID);
            if(needed_index == -1){
                System.out.println("This Netlist/Topology Node is not valid ");
            }
            else{
                String device = Resistor.resistors_ids_List.get(needed_index);
                System.out.println("This Netlist is connected to the Resistor : " + device);
            }

        }
        else if(NetlistNode == "drain" || NetlistNode == "source" || NetlistNode == "gate"){

            int needed_index = Nmos.nmos_topology_list.indexOf(ID);
            if(needed_index == -1){
                System.out.println("This Netlist/Topology Node is not valid ");
            }
            else{
                String device = Nmos.nmos_ids_List.get(needed_index);
                System.out.println("This Netlist is connected to the nmos : " + device);
            }
        }
        else{
            System.out.println("This Netlist Name is Not Valid !!");
        }
    }

    static List<String> topologiesIDsList = new ArrayList<>();
    
}
