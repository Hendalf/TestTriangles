package fixtures;

import model.Triangle;
import java.util.ArrayList;
import java.util.List;

public class TestData {
    public static ArrayList<String> getRequestDataForTriangles(){
        List<String> testDataForTriangles = new ArrayList<>();
        testDataForTriangles.add("1;2;3");
        testDataForTriangles.add("2;2;3");
        testDataForTriangles.add("3;2;3");
        testDataForTriangles.add("4;2;3");
        testDataForTriangles.add("5;2;3");
        testDataForTriangles.add("5;3;3");
        testDataForTriangles.add("5;4;3");
        testDataForTriangles.add("5;5;3");
        testDataForTriangles.add("5;6;3");
        testDataForTriangles.add("5;7;3");
        testDataForTriangles.add("5;7;4");
        return (ArrayList<String>) testDataForTriangles;
    }

    public static ArrayList<Triangle> getTriangles(ArrayList<String> ids){
        List<Triangle> testTriangles = new ArrayList<>();
        testTriangles.add(new Triangle(ids.get(0), 1.0f,2.0f,3.0f));
        testTriangles.add(new Triangle(ids.get(1), 2.0f,2.0f,3.0f));
        testTriangles.add(new Triangle(ids.get(2), 3.0f,2.0f,3.0f));
        testTriangles.add(new Triangle(ids.get(3), 4.0f,2.0f,3.0f));
        testTriangles.add(new Triangle(ids.get(4), 5.0f,2.0f,3.0f));
        testTriangles.add(new Triangle(ids.get(5), 5.0f,3.0f,3.0f));
        testTriangles.add(new Triangle(ids.get(6), 5.0f,4.0f,3.0f));
        testTriangles.add(new Triangle(ids.get(7), 5.0f,5.0f,3.0f));
        testTriangles.add(new Triangle(ids.get(8), 5.0f,6.0f,3.0f));
        testTriangles.add(new Triangle(ids.get(9), 5.0f,7.0f,3.0f));
        return (ArrayList<Triangle>) testTriangles;
    }

    public static Triangle getFirstExpectedTriangle(String id){
        return new Triangle(id, 1.0f, 2.0f, 3.0f);
    }

    public static Triangle getSecondExpectedTriangle(String id){
        return new Triangle(id, 2.0f, 2.0f, 3.0f);
    }
}
