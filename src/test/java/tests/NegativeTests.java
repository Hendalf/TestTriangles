package tests;

import base.TriangleBase;
import fixtures.TestData;
import model.EndPoints;
import model.Triangle;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

public class NegativeTests  extends TriangleBase {
    protected List<String> testTriangles = TestData.getRequestDataForTriangles();
    protected List<String> idList = new ArrayList<>();

    @Test(priority = 1)
    public void sendQueryWithoutXUsersHeader(){
        JSONObject requestBody = new JSONObject();
        absentXUserHearedRequestSpec.body(requestBody.put("input", testTriangles.get(0)).toString())
                .when()
                .post(EndPoints.addTriangle).then().statusCode(401);
    }

    @Test(priority = 2)
    public void sendQueryWithIncorrectXUsersHeader(){
        JSONObject requestBody = new JSONObject();
        incorrectXUserHearedRequestSpec.body(requestBody.put("input", testTriangles.get(0)).toString())
                .when()
                .post(EndPoints.addTriangle).then().statusCode(401);
    }

    @Test(priority = 3)
    public void addATriangleWithoutInput(){
        JSONObject requestBody = new JSONObject();
        rightRequestSpec.body(requestBody.put("separator", "%").toString())
                .when()
                .post(EndPoints.addTriangle).then().statusCode(422);
    }

    @Test(priority = 4)
    public void addATriangleWithEmptyInput(){
        JSONObject requestBody = new JSONObject();
        rightRequestSpec.body(requestBody.put("input", "").toString())
                .when()
                .post(EndPoints.addTriangle).then().statusCode(422);
    }

    @Test(priority = 5)
    public void addATriangleWithInputZero(){
        JSONObject requestBody = new JSONObject();
        rightRequestSpec.body(requestBody.put("input", "0;2;3").toString())
                .when()
                .post(EndPoints.addTriangle).then().statusCode(422);
    }

    @Test(priority = 6)
    public void addATriangleWithInputIsString(){
        JSONObject requestBody = new JSONObject();
        rightRequestSpec.body(requestBody.put("input", "2;five;3").toString())
                .when()
                .post(EndPoints.addTriangle).then().statusCode(422);
    }

    @Test(priority = 7)
    public void addATriangleWithIncorrectSeparator(){
        JSONObject requestBody = new JSONObject();
        rightRequestSpec.body(requestBody.put("separator", "%").put("input", "2;2;3").toString())
                .when()
                .post(EndPoints.addTriangle).then().statusCode(422);
    }

    @Test(priority = 8)
    public void addATriangleWithDifferentFromDefaultSeparator(){
        JSONObject requestBody = new JSONObject();
        rightRequestSpec.body(requestBody.put("input", "2%2%3").toString())
                .when()
                .post(EndPoints.addTriangle).then().statusCode(422);
    }

    @Test(priority = 9)
    public void getTriangleWithIncorrectId(){
        rightRequestSpec.when().get(EndPoints.getTriangle, "1111111111111111111").
                then().statusCode(404);
    }

    @Test(priority = 10)
    public void deleteTriangleWithIncorrectId(){
        rightRequestSpec.when().delete("/triangle/" + "1111111111111111111").
                then().statusCode(404);
    }

    @Test(priority = 11)
    public void getTrianglePerimeterWithIncorrectId(){
        rightRequestSpec.when().get("/triangle/" + "1111111111111111111").
                then().statusCode(404);
    }

    @Test(priority = 12)
    public void getTriangleAreaWithIncorrectId(){
        rightRequestSpec.when().get("/triangle/" + "1111111111111111111").
                then().statusCode(404);
    }

    @Test(priority = 13)
    public void addMoreThanMaximumPossibleTriangles(){
        JSONObject requestBody = new JSONObject();
        for(int i = 0; i<testTriangles.size()-1; i ++){
            Triangle triangle = rightRequestSpec.body(requestBody.put("input", testTriangles.get(i)).toString())
                    .when()
                    .post(EndPoints.addTriangle).then().statusCode(200).extract().body().as(Triangle.class);;
            idList.add(triangle.id);
        }
        rightRequestSpec.body(requestBody.put("input", testTriangles.get(10)).toString())
                .when()
                .post(EndPoints.addTriangle).then().statusCode(422);
    }

    @AfterTest
    private void deleteAllTriangles(){
        for(String id : idList){
            rightRequestSpec.when().delete("/triangle/" + id).
                    then().statusCode(200);
        }
    }
}
