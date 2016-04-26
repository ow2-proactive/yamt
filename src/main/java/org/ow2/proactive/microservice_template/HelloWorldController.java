package org.ow2.proactive.microservice_template;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Iaroslav on 4/26/2016.
 */
@RestController
public class HelloWorldController {

    @RequestMapping(value = "/hello", method= RequestMethod.GET)
    public HelloWorldResponse sayHello(@RequestParam(value = "name", required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return new HelloWorldResponse(name);
        }
        else {
            return new HelloWorldResponse("Toto");
        }

    }

}
