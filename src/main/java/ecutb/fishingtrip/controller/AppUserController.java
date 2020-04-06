package ecutb.fishingtrip.controller;

import ecutb.fishingtrip.dto.CreateAppUser;
import ecutb.fishingtrip.entity.AppUser;
import ecutb.fishingtrip.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

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

        AppUser appUser = appUserService.registerNew(form);
        return "redirect:/login";
    }

    @GetMapping("/users/{username}")
    public String getUserView(@PathVariable(name = "username") String username, @AuthenticationPrincipal UserDetails appUser, Model model){
        if(appUser == null){
            return "redirect:/accessdenied";
        }

        if(username.equals(appUser.getUsername()) || appUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))){
            Optional<AppUser> userOptional = appUserService.findByUserName(username);

            if(userOptional.isPresent()){
                AppUser user = userOptional.get();
                model.addAttribute("user", user);
                return "user-view";
            }else{
                throw new IllegalArgumentException("Requested user could not be found");
            }
        }else{
            return "redirect:/accessdenied";
        }
    }

}
