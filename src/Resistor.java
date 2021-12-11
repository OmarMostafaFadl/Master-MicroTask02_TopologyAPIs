import java.util.ArrayList;
import java.util.List;


public class Resistor {

    /*
        This class includes all the lists where we the Algorithm store any resistor related
        data when reading JSON files. Also these lists help in quering and tracking the 
        data.
    */

    static List<String> resistors_ids_List = new ArrayList<>();
    static List<Long> resistors_default_resistance_list = new ArrayList<>();
    static List<Long> resistors_min_resistance_list = new ArrayList<>();
    static List<Long> resistors_max_resistance_list = new ArrayList<>();
    static List<String> resistors_t1_list = new ArrayList<>();
    static List<String> resistors_t2_list = new ArrayList<>();
    static List<String> resistors_topology_list = new ArrayList<>();
    
}
