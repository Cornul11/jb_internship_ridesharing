import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


class Point {
    private float latitude, longitude;

    Point(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = latitude;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public float getLongitude() {
        return this.longitude;
    }
}

class Participants {
    private Collection<Person> passengers, drivers;

    Participants(Collection<Person> passengers, Collection<Person> drivers) {
        this.passengers = passengers;
        this.passengers = drivers;
    }

    public Collection<Person> getPassengers() {
        return this.passengers;
    }

    public Collection<Person> getDrivers() {
        return this.drivers;
    }
}

class Person {
    private UUID id;
    private Point finishPoint;

    Person(UUID id, Point finishPoint) {
        this.id = id;
        this.finishPoint = finishPoint;
    }

    public UUID getId() {
        return this.id;
    }

    public Point getFinishPoint() {
        return this.finishPoint;
    }
}

class SortByDistance implements Comparator<Person> {
    Person person;
    SortByDistance(Person person) {
        this.person = person;
    }

    public int compare(Person a, Person b) {
        return (Main.computeDistance(a.getFinishPoint(), person.getFinishPoint())).compareTo.;
    }
}

public class Main {
    private static Collection<Person> passengers;
    private static ArrayList<Person> drivers;

    public static void main(String[] args) {
        passengers = new ArrayList<>(10);
        drivers = new ArrayList<>(10);
        Main main = new Main();
        main.readPoints();

        for (Person passenger : passengers) {
            ArrayList<Person> suggestedDrivers = suggestDrivers(passenger, drivers);
            System.out.printf("Passenger point: %f, %f", passenger.getFinishPoint().getLongitude(), passenger.getFinishPoint().getLatitude());
            for (Person driver : suggestedDrivers) {
                System.out.printf("  %f, %f\n", driver.getFinishPoint().getLatitude(), driver.getFinishPoint().getLongitude());
            }
        }
    }

    protected static double computeDistance(Point a, Point b) {
        return 10000 * Math.sqrt((a.getLatitude() - a.getLongitude()) * (a.getLatitude() - a.getLongitude()) + (b.getLatitude() - b.getLongitude()) * (b.getLatitude() - b.getLongitude()));
    }

    private static ArrayList<Person> suggestDrivers(Person passenger, ArrayList<Person> drivers) {
        ArrayList<Person> suggestedDrivers = (ArrayList<Person>) drivers.clone();
        System.out.println(suggestedDrivers.size() + " " + drivers.size());
        Collections.copy(suggestedDrivers, drivers);
        suggestedDrivers.sort(new SortByDistance(passenger));
        return suggestedDrivers;
    }

    private static Point asPoint(String it) {
        String[] parts = it.split(", ");
        return new Point(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]));
    }

    private void readPoints() {
        Path pathToResource = null;

        try {
            pathToResource = Paths.get(this.getClass().getClassLoader().getResource("latlons").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List<String> allPoints = null;

        try {
            assert pathToResource != null;
            allPoints = Files.readAllLines(pathToResource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert allPoints != null;
        Collections.shuffle(allPoints);

        List<Point> points = new ArrayList<>(20);
        for (String point : allPoints) {
            points.add(asPoint(point));
        }
        for (Point point : points.subList(0, 10)) {
            passengers.add(new Person(UUID.randomUUID(), point));
        }
        for (Point point : points.subList(10, 19)) {
            drivers.add(new Person(UUID.randomUUID(), point));
        }
    }
}