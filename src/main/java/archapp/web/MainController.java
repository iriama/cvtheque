package archapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/")
    public final String index() {
        return "index.html";
    }

}
