import java.util.ArrayList;
import java.util.List;

public class Node {
    private long id;
    private double lat;
    private double lon;
    private List connections;
    private Double priority = Double.MAX_VALUE;

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public Node(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        connections = new ArrayList<Long>();
    }

    public long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public List getConnections() {
        return connections;
    }

    public void addConnection(long i) {
        if (!connections.contains(i) && i != id) {
            connections.add(i);
        }
    }

}
