package com.example.docs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Do we see this JavaDoc in swagger? Its on the ProductsController
 */
@Controller
public class ThemeSwaggerController {

    @RequestMapping(value = "/themed")
    public String docs() {
        return "forward:/index.html";
    }

}
