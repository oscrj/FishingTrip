package ecutb.fishingtrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

    /**
     * Main Page for the application. Accessible for everyone.
     * @return a view of index page
     */
    @GetMapping("index")
    public String home(){
        return "index";
    }

    /**
     * Make is possible to login for registered users.
     * @return a log in form.
     */
    @GetMapping("login")
    public String login(){
        return "login";
    }

    /**
     * Show a page whit message access denied.
     * @return return an access denied page for users without authorities.
     */
    @GetMapping("/accessdenied")
    public String getAccessDenied(){
        return "access-denied";
    }

}
