package ecutb.fishingtrip.controller;

import ecutb.fishingtrip.entity.AppUser;
import ecutb.fishingtrip.entity.FishingTrip;
import ecutb.fishingtrip.service.AppUserService;
import ecutb.fishingtrip.service.FishingTripService;
import ecutb.fishingtrip.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class AdminController {

    private AppUserService userService;
    private FishingTripService fishingTripService;
    private SpeciesService speciesService;

    @Autowired
    public AdminController(AppUserService userService, FishingTripService fishingTripService, SpeciesService speciesService) {
        this.userService = userService;
        this.fishingTripService = fishingTripService;
        this.speciesService = speciesService;
    }

    /**
     * Give admin a view of all users.
     * @param model make it possible to show logged in ADMIN all users i DB.
     * @return return users-view
     */
    @GetMapping("admin/users")
    public String getAllUsers(Model model){
        Set<AppUser> users = userService.findAll();
        model.addAttribute("users", users);
        return "users-view";
    }

    @GetMapping("admin/findtrips")
    public String findTripsByUserName(){
        return "search-trips";
    }

    @GetMapping("admin/trips")
    public String tripsByUsername(@RequestParam("username") String userName, Model model){
        Set<FishingTrip> fishingTrips = fishingTripService.findByAppUser(userName);
        model.addAttribute("user", userName);
        model.addAttribute("fishingTrips", fishingTrips);
        return "fishing-trips-username";
    }
}
