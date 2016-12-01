package oyster.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardResource {

    private static Map<String, List<Integer>> zoneStations = new HashMap<>();

    public static Map<String, List<Integer>>  getMapOfStationAndZones(){

        zoneStations.put("Holborn", Arrays.asList(1));
        zoneStations.put("Earl’s Court", Arrays.asList(1, 2));
        zoneStations.put("Wimbledon", Arrays.asList(3));
        zoneStations.put("Hammersmith", Arrays.asList(2));
        return zoneStations;
    }
}
