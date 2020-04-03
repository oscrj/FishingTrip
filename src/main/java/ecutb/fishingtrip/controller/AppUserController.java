package ecutb.fishingtrip.controller;

import ecutb.fishingtrip.dto.CreateAppUser;
import ecutb.fishingtrip.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AppUserController {

    private AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/register")
    public String getRegisterUserFrom(Model model){
        model.addAttribute("form", new CreateAppUser());
        return "register-user";
    }

    @PostMapping("/register")
    public String registerForm(@Valid @ModelAttribute(name = "form") CreateAppUser form, BindingResult bindingResult){
        // Check with database if there already is a user with this username.
        if(appUserService.findByUserName(form.getUserName()).isPresent()){
            FieldError error = new FieldError("form", "username", "There is already a user with username: " + form.getUserName());
            bindingResult.addError(error);
        }
        // Check if password is NOT the same as confirmation password.
        if(!form.getPassword().equals(form.getConfirmPassword())){
            FieldError error = new FieldError("form", "password","Password confirmation didn't match your password");
            bindingResult.addError(error);
        }
        if(bindingResult.hasErrors()){
            return "register-user";
        }

        appUserService.registerNew(form);
        return "redirect:/login";
    }
}
