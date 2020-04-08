package ecutb.fishingtrip.controller;

import ecutb.fishingtrip.service.AppUserService;
import ecutb.fishingtrip.service.FishingTripService;
import ecutb.fishingtrip.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("admin/users")
    public String getAllUsers(){

        return "users-view";
    }
}
