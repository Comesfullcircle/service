package org.delivery.storeadmin.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping({"", "/main"})
    public ModelAndView main() {
        return new ModelAndView("main");
    }

    @GetMapping("/order")
    public ModelAndView order() {
        return new ModelAndView("order/order");
    }

    @GetMapping("/user-profile")
    public ModelAndView userprofile() {
        return new ModelAndView("user-profile");
    }
}

