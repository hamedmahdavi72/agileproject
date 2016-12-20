import com.fasterxml.jackson.databind.JsonNode;
import dao.CustomerDAOWrapper;
import models.Customer;
import models.JongoInstanceProvider;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;



import play.mvc.Result;
import play.test.WithApplication;



import static org.junit.Assert.*;
import static play.mvc.Http.RequestBuilder;
import static play.test.Helpers.*;

/**
 * Created by HamedMahdavi on 12/20/2016.
 */
public class FakeApplicationSpike extends WithApplication {
        @Test
        public void test(){
            String body = "{\"username\":\"hamed\", \"password\":\"12345678}\"}";
            JsonNode json = Json.parse(body);
            System.out.println(json.toString());
            RequestBuilder req = fakeRequest(POST,"/login/").bodyJson(json);
            Result result = route(req);
            System.out.println(contentAsString(result));



        }
}
