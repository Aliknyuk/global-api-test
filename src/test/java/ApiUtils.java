import io.restassured.http.ContentType;
import io.restassured.response.*;

import java.util.*;

import static io.restassured.RestAssured.given;

public class ApiUtils {
    private static String endPointStations ="/stations";

    public static Response getRequestToStations(){
        return  given().accept(ContentType.JSON).get(endPointStations);
    }

    public static Response getRequestToStationsWithIdNumber(int id){
        return  given().accept(ContentType.JSON)
                .and().pathParam("id",id)
                .when().get(endPointStations.concat("/{id}"));
    }

    public static Response putRequestToStationsWithIdNumber(Map<String, Object> map){
        return  given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(map)
                .and().pathParam("id",map.get("id"))
                .when().put(endPointStations.concat("/{id}"));
    }




}
