package pharma.Service;

public class HarvesinFormula {

    private static final double EARTH_RADIUS_KM = 6371.0;
    private HarvesinFormula() {


    }


    public static double calculate_harvesinDistance(double lat1, double lng_1, double lat2, double lng_2) {
        if (Math.abs(lat1) > 90 || Math.abs(lat2) > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees");
        }
        if (Math.abs(lng_1) > 180 || Math.abs(lng_2) > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees");
        }



        double lat1_rad = Math.toRadians(lat1);
        double lat2_rand = Math.toRadians(lat2);
        double delta_lat_rand = Math.toRadians(lat2 - lat1);
        double delta_lng_rand = Math.toRadians(lng_2 - lng_1);
        double a = Math.pow(Math.sin(delta_lat_rand / 2), 2) + Math.cos(lat1_rad) * Math.cos(lat2_rand) *
                Math.pow(Math.sin(delta_lng_rand / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM*c;
    }
}





