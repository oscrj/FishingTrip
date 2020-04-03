package ecutb.fishingtrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("/accessdenied")
    public String getAccessDenied(){
        return "access-denied";
    }
}
