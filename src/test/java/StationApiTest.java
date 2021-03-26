import io.restassured.response.Response;
import org.junit.jupiter.api.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class StationApiTest {

    public static final String URL = "https://my-json-server.typicode.com/broadcastplatforms/rest-api-test";

    @BeforeEach
    void setUp() {
        baseURI = URL;
    }

    @DisplayName("Get all stations and verify size of response")
    @Test()
    void testGetStations() {
        // create a test which gets all stations by calling the /stations endpoint

        Response response = ApiUtils.getRequestToStations();
        assertTrue(response.statusCode()==200);

        List<String> nameOfTheStations = response.jsonPath().getList("name");


        //Assertion: we can verify in many different ways, 2 of them;

        assertEquals("Verify that number of station is 6",6, nameOfTheStations.size());

        assertEquals("Verify that the last id is 6",6,response.jsonPath().getInt("id[5]"));


    }

    @DisplayName("Get the station with id 1")
    @Test()
    void testGetStation() {
        // create a test which gets the station with id 1
        Response responseForStation = ApiUtils.getRequestToStationsWithIdNumber(1);
        assertTrue(responseForStation.statusCode()==200);

        Response responseAllStations = ApiUtils.getRequestToStations();

        //validation


        assertEquals("Verify if id number is as expected",1,responseForStation.jsonPath().getInt("id"));

        //or we can combine with each other and make the validation
        assertEquals(responseAllStations.jsonPath().getString("name[0]"),responseForStation.jsonPath().getString("name"));


    }

    @DisplayName("Update the name of a station")
    @Test()
    void testUpdateStation() {
        // create a test which updates the name of Capital FM (ID 1) to Capital London
        String newName="Capital London";
        Map<String, Object> updateBodyMap=new HashMap();
        updateBodyMap.put("id",1);
        updateBodyMap.put("name",newName);

        Response responseForUpdate = ApiUtils.putRequestToStationsWithIdNumber(updateBodyMap);
//        responseForUpdate.prettyPrint();

        //verification for putResponse payload
        assertTrue("Verify if status code is correct: ",responseForUpdate.statusCode()==200);
        assertEquals("Verify if id numbers are match: ",updateBodyMap.get("id"),responseForUpdate.path("id"));
        assertEquals("Verify if station names are match",responseForUpdate.path("name"),newName);

        //verification through get request for updated station
        Response responseForUpdatedStation = ApiUtils.getRequestToStationsWithIdNumber((Integer) updateBodyMap.get("id"));
        assertTrue("Verify if status code is correct",responseForUpdatedStation.statusCode()==200);

        //verification
        assertEquals("Verify that if the station id is matched",responseForUpdate.jsonPath().getInt("id"),responseForUpdatedStation.jsonPath().getInt("id"));
        assertEquals("Verify that if the staion name is updated:", responseForUpdate.jsonPath().getString("name"),responseForUpdatedStation.jsonPath().getString("name"));


    }
}
