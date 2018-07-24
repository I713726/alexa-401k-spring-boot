package voya401k;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    @RequestMapping(value = "/voya401k", method = RequestMethod.POST)
    @ResponseBody
    public void process(@RequestBody Map<String, Object> payload) throws Exception {
        //TODO: send map to be processed
        //System.out.println(payload);
    }
}
