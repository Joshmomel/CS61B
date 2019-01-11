import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private final double Sl = 288200;
    private final double startPixel = ((MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE) * Sl;
    private final double difference = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
    private final double latDifference = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;



    public Rasterer() {
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Double queryLrlon = params.get("lrlon");
        Double queryUllon = params.get("ullon");
        Double queryUllat = params.get("ullat");
        Double queryLrlat = params.get("lrlat");
        Double queryWidth = params.get("w");
        System.out.println(params);

        double LonDPP = calculateLonDDP(queryLrlon, queryUllon, queryWidth);
        System.out.println(LonDPP);
        int depth = calculateDepth(LonDPP);

        double[] longitude = getLongitude(depth, queryUllon, queryLrlon);
        System.out.println("longitude are " + longitude[0] + ", " + longitude[1] + " and " + longitude[2] + " , " + longitude[3]);

        double[] latitude = getLatitude(depth, queryLrlat, queryUllat);
        System.out.println("latitude are " + latitude[0] + ", " + latitude[1] + " and " + latitude[2] + " , " + latitude[3]);

        String[][] renderGrid = renderGrid(depth, (int) longitude[1], (int) longitude[3], (int) latitude[1], (int) latitude[3]);
        for (String[] strings : renderGrid) {
            for (String string : strings) {
                System.out.println(string);
            }
        }

        Map<String, Object> results = new HashMap<>();


        results.put("raster_ul_lon", longitude[0]);
        results.put("raster_ul_lat", latitude[0]);
        results.put("raster_lr_lon", longitude[2]);
        results.put("raster_lr_lat", latitude[1]);
        results.put("depth", depth);
        results.put("query_success", true);
        results.put("render_grid", renderGrid);


        return results;
    }

    private double calculateLonDDP(double lrlon, double ullon, double width) {
        double diff = lrlon - ullon;
        System.out.println("system diff is " + (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON));
        System.out.println("x0 per pixel " + startPixel);
        System.out.println("diff is " + diff);
        System.out.println("width is " + width);
        System.out.println("initial pdd is " + diff * Sl);
        System.out.println("after scaling is " + diff * Sl / width);
        double LonDpp = diff / width;
        System.out.println("Approx LonDpp is " + LonDpp);
        double feetPerPixel = LonDpp * Sl;
        System.out.println("depth is " + calculateDepth(feetPerPixel));
        return feetPerPixel;
    }

    private int calculateDepth(double feetPerpixel) {
        int count = 1;
        double initPixel = startPixel / 2;
        for (int i = 0; i < 8; i++) {
            if (feetPerpixel > initPixel) {
                return count;
            }
            initPixel = initPixel / 2;
            count += 1;
        }
        return 7;
    }

    private double[] getLatitude(int depth, double requireMinLat, double requireMaxLat) {
        double differenceInDepth = latDifference / Math.pow(2, depth);
//        System.out.println("min is " + requireMinLat);
//        System.out.println("max is " + requireMaxLat);
        int countMin = 0;
        double[] results = new double[4];
        double lat = MapServer.ROOT_ULLAT;

        for (int i = 0; i < Math.pow(2, depth); i++) {
            if (requireMaxLat > lat - differenceInDepth) {
                results[0] = lat;
                results[1] = countMin;
                break;
            }
            lat -= differenceInDepth;
            countMin += 1;
        }

        for (int i = countMin; i <= Math.pow(2, depth); i++) {
            if (i == Math.pow(2,depth)) {
                results[2] = MapServer.ROOT_LRLAT;
                results[3] = i - 1;
                break;
            }
            if (requireMinLat > lat - differenceInDepth) {
                results[2] = lat - differenceInDepth;
                results[3] = i;
                break;
            }
            lat -= differenceInDepth;
        }
        return results;
    }

    private double[] getLongitude(int depth, double requireMinLon, double requireMaxLon) {
        double differenceInDepth = difference / Math.pow(2, depth);
        System.out.println("function getLongitude diff is " + differenceInDepth);
        int countMin = 0;
        double[] results = new double[4];
        double lon = MapServer.ROOT_ULLON;

        for (int i = 0; i < Math.pow(2, depth); i++) {
            if (requireMinLon < lon + differenceInDepth) {
                results[0] = lon;
                results[1] = countMin;
                break;
            }
            lon += differenceInDepth;
            countMin += 1;
        }

        for (int i = countMin; i <= Math.pow(2, depth); i++) {
            if (i == Math.pow(2, depth)) {
                results[2] = MapServer.ROOT_LRLON;
                results[3] = i - 1;
                break;
            }
            if (requireMaxLon < lon + differenceInDepth) {
                results[2] = lon + differenceInDepth;
                results[3] = i;
                break;
            }
            lon += differenceInDepth;
        }
        return results;
    }

    private String[][] renderGrid(int depth, int lonStart, int lonEnd, int latStart, int latEnd) {
        String[][] results = new String[latEnd - latStart + 1][lonEnd - lonStart + 1];
        for (int i = latStart; i <= latEnd; i++) {
            for (int j = lonStart; j <= lonEnd; j++) {
                if (i - latStart < 0 || j - lonStart < 0) {
                    System.out.println("break happens in renderGrid");
                    break;
                } else {
                    results[i - latStart][j - lonStart]
                            = "d" + depth + "_x" + j + "_y" + i + ".png";
                }

            }
        }
        return results;
    }

}
