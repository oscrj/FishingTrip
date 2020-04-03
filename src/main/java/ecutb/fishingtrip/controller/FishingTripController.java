package ecutb.fishingtrip.controller;

import ecutb.fishingtrip.dto.CreateFishingTrip;
import ecutb.fishingtrip.entity.FishingTrip;
import ecutb.fishingtrip.entity.Species;
import ecutb.fishingtrip.service.FishingTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class FishingTripController {

    private FishingTripService fishingTripService;

    @Autowired
    public FishingTripController(FishingTripService fishingTripService) {
        this.fishingTripService = fishingTripService;
    }

    @GetMapping("gofishing")
    public String getFishingTripFrom(Model model){
        model.addAttribute("fishingTripForm", new CreateFishingTrip());
        return "new-trip";
    }

    @PostMapping("gofishing")
    public String createFishingTripFrom(@Valid @ModelAttribute(name = "fishingTripForm") CreateFishingTrip fishingTripForm, @AuthenticationPrincipal UserDetails appUser, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "new-trip";
        }

        // Create new Fishing Trip by using fishingTripForm and connect this trip to user that is logged in and use that users userName.
        FishingTrip newFishingTrip = fishingTripService.newFishingTrip(fishingTripForm, appUser.getUsername());

        // should return a different view.
        return "redirect:/index";
    }

    @GetMapping("gofishing/catch")
    public String getCatchForm(Model model){
        model.addAttribute("catchForm", new Species());
        return "new-catch";
    }


}
