import java.util.ArrayList;
import java.util.List;

public class Nmos {

    /*
        This class includes all the lists where we the Algorithm store any nmos related
        data when reading JSON files. Also these lists help in quering and tracking the 
        data.
    */

    static List<String> nmos_ids_List = new ArrayList<>();
    static List<Double> nmos_default_m_list = new ArrayList<>();
    static List<Long> nmos_min_m_list = new ArrayList<>();
    static List<Long> nmos_max_m_list = new ArrayList<>();
    static List<String> nmos_drain_list = new ArrayList<>();
    static List<String> nmos_gate_list = new ArrayList<>();
    static List<String> nmos_source_list = new ArrayList<>();
    static List<String> nmos_topology_list = new ArrayList<>();
    
}
