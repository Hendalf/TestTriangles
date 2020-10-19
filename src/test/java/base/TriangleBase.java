package base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriangleBase {
    private static final String basePath = "https://qa-quiz.natera.com";
    private static final String userToken = "2358aaad-e09c-492a-86f5-ebae27e206ae";

    protected RequestSpecification rightRequestSpec = given()
            .baseUri(basePath)
            .header("X-User", userToken)
            .given().contentType(ContentType.JSON);

    protected RequestSpecification incorrectXUserHearedRequestSpec = given()
            .baseUri(basePath)
            .header("X-User", userToken.replace("2358aaad", "12345678"))
            .given().contentType(ContentType.JSON);

    protected RequestSpecification absentXUserHearedRequestSpec = given()
            .baseUri(basePath)
            .given().contentType(ContentType.JSON);

    protected Logger logger  = LoggerFactory.getLogger(getClass());
}
