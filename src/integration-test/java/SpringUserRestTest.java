import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.proactive.microservice_template.Application;
import org.ow2.proactive.microservice_template.model.User;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/**
 * Created by Iaroslav on 4/27/2016.
 */

@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebIntegrationTest(randomPort = true)
public class SpringUserRestTest {

    public static final String REST_SERVICE_URI = "http://localhost:8080/users/";

    /* POST and GET*/
    @Test
    public void testCreateUpdateGetUser() {
        RestTemplate restTemplate = new RestTemplate();
        User user = new  User("Marco", 18, 70000);
//        Collection<User> allUsers = restTemplate.getForObject(REST_SERVICE_URI, Collection.class);

        ResponseEntity<User> respUser = restTemplate.postForEntity(REST_SERVICE_URI, user, User.class);
        System.out.println("Test: " + respUser.getBody());

        assertThat(respUser.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(respUser.getBody(), is(user));

        user.setAge(1);
        restTemplate.put(REST_SERVICE_URI + user.getName(), user);

        User retrievedUser = restTemplate.getForObject(REST_SERVICE_URI + user.getName(), User.class);
        assertTrue(retrievedUser.equals(user));
    }
}