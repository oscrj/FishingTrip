package ecutb.fishingtrip.controller;

import ecutb.fishingtrip.dto.CreateAppUser;
import ecutb.fishingtrip.dto.UpdateAppUser;
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

    /**
     * Will give user a view of a registration form.
     * @param model makes it possible to receive and show information from registration from.
     * @return
     */
    @GetMapping("/register")
    public String getRegisterUserFrom(Model model){
        model.addAttribute("form", new CreateAppUser());
        return "register-user";
    }

    /**
     * Register user by confirming a correct filled in Registration form.
     * @param form makes it possible to receive information from registration from.
     * @param bindingResult will store wrong inputs and display them for the user.
     * @return a new created user that will be stored in database.
     */
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

    /**
     * Show specific user its profile page.
     * @param username show the correct user its profile page.
     * @param appUser Check if the user that is logged in has the same username as username
     * @param model view the details of user whit the username.
     * @return a view of information regarding the user.
     */
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

    /**
     * Update user by a updateUser Form. only possible by ADMINS.
     * @param username find the user that will be updated och receive the old information from that user.
     * @param model show and create update to the user.
     * @return a view of a updateUser form.
     */
    @GetMapping("/users/{username}/update")
    public String getUserUpdateForm(@PathVariable("username") String username, Model model){
        UpdateAppUser updatedAppUser = new UpdateAppUser();
        AppUser appUser = appUserService.findByUserName(username).orElseThrow(IllegalArgumentException::new);

        updatedAppUser.setUserId(appUser.getUserId());
        updatedAppUser.setUserName(appUser.getUserName());
        updatedAppUser.setFirstName(appUser.getFirstName());
        updatedAppUser.setLastName(appUser.getLastName());
        updatedAppUser.setEmail(appUser.getEmail());
        updatedAppUser.setAdmin(appUser.isAdmin());

        model.addAttribute("updatedAppUserForm", appUser);
        return "update-user";
    }

    /**
     * By confirming a correcly filled out form. changes to user will be mede.
     * @param userName find the user that will be updated.
     * @param updatedAppUser store the old information about the user that will be updated. If user dont change input it will remain the same.
     * @param bindingResult store errors regarding incorrect inputs from user.
     * @return an updated user.
     */
    @PostMapping("/users/{username}/update")
    public String userUpdatedForm(@PathVariable("username") String userName,
                                  @Valid @ModelAttribute("updatedAppUserForm") UpdateAppUser updatedAppUser, BindingResult bindingResult){
        AppUser originalAppUser = appUserService.findByUserName(userName).orElseThrow(IllegalArgumentException::new);

        if(appUserService.findByUserName(updatedAppUser.getUserName()).isPresent() && !updatedAppUser.getUserName().equalsIgnoreCase(originalAppUser.getUserName())){
            FieldError error = new FieldError("updatedAppUserForm", "username", "Username is already in use");
            bindingResult.addError(error);
        }

        if(appUserService.findByEmail(updatedAppUser.getEmail()).isPresent() && !updatedAppUser.getEmail().equalsIgnoreCase(originalAppUser.getEmail())){
            FieldError error = new FieldError("updatedAppUserForm", "email", "Email is already in use");
            bindingResult.addError(error);
        }

        if(bindingResult.hasErrors()){
            return "update-user";
        }

        originalAppUser.setUserName(updatedAppUser.getUserName());
        originalAppUser.setFirstName(updatedAppUser.getFirstName());
        originalAppUser.setLastName(updatedAppUser.getLastName());
        originalAppUser.setEmail(updatedAppUser.getEmail());
        originalAppUser.setAdmin(updatedAppUser.isAdmin());

        appUserService.saveAndUpdate(originalAppUser);

        return "redirect:/users/"+originalAppUser.getUserName();
    }

    /**
     * Delete User
     * @param userName find the user you want to delete by its username.
     * @return will redirect you to users-view and show you the all the registered users.
     */
    @GetMapping("/users/{username}/delete")
    public String deleteAppUser(@PathVariable("username") String userName){
        AppUser deletedAppUser = appUserService.findByUserName(userName).orElseThrow(IllegalArgumentException::new);
        appUserService.delete(deletedAppUser.getUserId());
        return "redirect:/admin/users";
    }

}
