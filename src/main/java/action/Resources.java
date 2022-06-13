package action;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Resources extends CommonUtilities {

    private static Response response;
    private static RequestSpecification httpsRequest = RestAssured.given().contentType(ContentType.JSON);
    private static String userId;
    private static int numberOfPosts;
    private static String postId;
    EmailValidator validator = EmailValidator.getInstance();
    public static String baseURI = CommonUtilities.getBaseURL();

    @When("^User performs a GET call on the \"(.*)\" resource$")
    public void userPerformsAGETCallOnTheUsersResource(String resourceName) {

        switch (resourceName) {
            case "users":
                response = httpsRequest.request(Method.GET, baseURI+"users");
                break;

            case "posts":
                response = httpsRequest.request(Method.GET, baseURI+"posts");
                break;

            case "comments":
                response = httpsRequest.request(Method.GET, baseURI+"comments");
                break;
        }
    }

    @Then("^API call is successful with a status code \"(.*)\"$")
    public void apiCallIsSuccessfulWithAStatusCode(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    @When("User performs a GET call on the {string} resource with username {string}")
    public void userPerformsAGETCallOnTheResourceWithUsername(String users, String username) {
        response = httpsRequest.queryParam("username", username)
                .request(Method.GET, baseURI+"users");
    }

    @And("Extracts the Id of this username")
    public void extractsTheIdOfThisUsername() throws Throwable {
        JsonPath path = response.body().jsonPath();
        userId = path.getString("id[0]");
    }

    @When("User performs a GET call on the {string} resource with the extracted Id")
    public void userPerformsAGETCallOnTheResourceWithTheExtractedId(String arg0) {

        response = httpsRequest.queryParam("userId", userId)
                .request(Method.GET, baseURI+"posts");
    }

    @And("Extract the postIds of this username")
    public void extractThePostIdsOfThisUsername() {
        JsonPath path = response.body().jsonPath();
        numberOfPosts = path.getInt("response.size()");
    }

    @When("User performs a GET call on the {string} resource with the extracted postIds")
    public void userPerformsAGETCallOnTheResourceWithTheExtractedPostIds(String arg0) {
        JsonPath path = response.body().jsonPath();
        List<Map<String, String>> postIds = path.getList("id");
        for (int i = 0; i < numberOfPosts; i++) {
            response = httpsRequest.queryParam("postId", postIds.get(i))
                    .request(Method.GET, baseURI+"comments");
        }

    }

    @And("Validates the email address in those comments")
    public void validatesTheEmailAddressInThoseComments() {
        JsonPath path = response.body().jsonPath();
        List<Map<String, String>> emails = path.getList("email");
        int numberOfEmailIDsToBeValidated = emails.size();
        for (int i = 0; i < numberOfEmailIDsToBeValidated; i++) {
            System.out.println("The email address " + emails.get(i) + " is " + (validator.isValid(String.valueOf(emails.get(i))) ? "valid" : "invalid"));
        }
    }
}
