package tests;

import base.TriangleBase;
import fixtures.TestData;
import io.restassured.response.Response;
import model.EndPoints;
import model.Result;
import model.Triangle;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.Assert;
import util.MathUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PositiveTests extends TriangleBase {
    protected List<Triangle> expectedTriangles = new ArrayList<>();
    protected List<String> testTriangles = TestData.getRequestDataForTriangles();

    @Test(priority = 1)
    public void addTriangleWithDefaultSeparator(){
        JSONObject requestBody = new JSONObject();
        Triangle actualTriangle = rightRequestSpec.body(requestBody.put("input", testTriangles.get(0)).toString())
                .when()
                .post(EndPoints.addTriangle).then().statusCode(200).extract().body().as(Triangle.class);

        Triangle expectedTriangle = TestData.getFirstExpectedTriangle(actualTriangle.id);
        Assert.assertEquals(actualTriangle, expectedTriangle);
        expectedTriangles.add(actualTriangle);
    }

    @Test(priority = 2)
    public void getTriangle(){
        Triangle actualTriangle = rightRequestSpec.when().get(EndPoints.getTriangle, expectedTriangles.get(0).id).
                then().statusCode(200).extract().body().as(Triangle.class);

        Triangle expectedTriangle = TestData.getFirstExpectedTriangle(expectedTriangles.get(0).id);
        Assert.assertEquals(actualTriangle, expectedTriangle);
    }

    @Test(priority = 3)
    public void getTrianglePerimeter(){
        Result actualPerimiter = rightRequestSpec.when().get("/triangle/" + expectedTriangles.get(0).id + "/perimeter").
                then().statusCode(200).extract().body().as(Result.class);

        Triangle expectedTriangle = TestData.getFirstExpectedTriangle(expectedTriangles.get(0).id);
        float expectedPerimeter = expectedTriangle.firstSide + expectedTriangle.secondSide + expectedTriangle.thirdSide;
        Assert.assertEquals(actualPerimiter.result, expectedPerimeter);
    }

    @Test(priority = 4)
    public void getTriangleArea(){
        Result actualArea = rightRequestSpec.when().get("/triangle/" + expectedTriangles.get(0).id + "/area").
                then().statusCode(200).extract().body().as(Result.class);

        Triangle expectedTriangle = TestData.getFirstExpectedTriangle(expectedTriangles.get(0).id);
        double expectedArea = MathUtil.getAreaOfTriangle(
                expectedTriangle.firstSide,
                expectedTriangle.secondSide,
                expectedTriangle.thirdSide);
        Assert.assertEquals(actualArea.result, expectedArea);
    }

    @Test(priority = 5)
    public void addTriangleWithCustomSeparator(){
        JSONObject requestBody = new JSONObject();
        Triangle actualTriangle = rightRequestSpec.body(requestBody.put("separator", "%").put("input", "2%2%3").toString())
                .when()
                .post(EndPoints.addTriangle).then().statusCode(200).extract().body().as(Triangle.class);

        Triangle expectedTriangle = TestData.getSecondExpectedTriangle(actualTriangle.id);
        Assert.assertEquals(actualTriangle, expectedTriangle);
        expectedTriangles.add(actualTriangle);
    }

    @Test(priority = 6)
    public void addMaximumPossibleTriangles() {
        for(int i = 2; i<testTriangles.size()-1; i ++){
            JSONObject requestBody = new JSONObject();
            Triangle actualTriangle = rightRequestSpec.body(requestBody.put("input", testTriangles.get(i)).toString())
                    .when()
                    .post(EndPoints.addTriangle).then().statusCode(200).extract().body().as(Triangle.class);
            expectedTriangles.add(actualTriangle);
        }
    }

    @Test(priority = 7)
    public void getAllTriangles(){
        Response response = rightRequestSpec.when().get(EndPoints.getAllTriangles);
        int statusCode = response.statusCode();
        Assert.assertEquals(statusCode, 200);
        List<Triangle> actualTriangles = getActualData(response);
        Collections.sort(expectedTriangles);
        Assert.assertEquals(actualTriangles, expectedTriangles);
    }

    @Test(priority = 8)
    public void deleteTriangles(){
        for(int i = 0; i < expectedTriangles.size(); i ++){
            rightRequestSpec.when().delete("/triangle/" + expectedTriangles.get(i).id).
                    then().statusCode(200);
            logger.info("Deleted " + i + " triangle with id: " + expectedTriangles.get(i).id);
        }
    }

    private ArrayList<Triangle> getActualData(Response response){
        List<Triangle> actualData = new ArrayList<>();
        List<String> ids = response.jsonPath().getList("id");
        List<Float> firstSides = response.jsonPath().getList("firstSide");
        List<Float> secondSides = response.jsonPath().getList("secondSide");
        List<Float> thirdSides = response.jsonPath().getList("thirdSide");

        for(int i = 0; i < ids.size(); i++){
            actualData.add(new Triangle(ids.get(i), firstSides.get(i), secondSides.get(i), thirdSides.get(i)));
        }
        Collections.sort(actualData);
        return (ArrayList<Triangle>) actualData;
    }
}
