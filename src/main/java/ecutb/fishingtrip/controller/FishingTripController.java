package ecutb.fishingtrip.controller;

import ecutb.fishingtrip.dto.CreateFishingTrip;
import ecutb.fishingtrip.dto.CreateSpecies;
import ecutb.fishingtrip.entity.FishingTrip;
import ecutb.fishingtrip.entity.Species;
import ecutb.fishingtrip.service.FishingTripService;
import ecutb.fishingtrip.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class FishingTripController {

    private FishingTripService fishingTripService;
    private SpeciesService speciesService;

    @Autowired
    public FishingTripController(FishingTripService fishingTripService, SpeciesService speciesService) {
        this.fishingTripService = fishingTripService;
        this.speciesService = speciesService;
    }

    @GetMapping("/fishing/gofishing")
    public String getFishingTripFrom(Model model){
        model.addAttribute("fishingTripForm", new CreateFishingTrip());
        return "new-trip";
    }

    @PostMapping("/fishing/gofishing")
    public String createFishingTripFrom(@Valid @ModelAttribute(name = "fishingTripForm") CreateFishingTrip fishingTripForm, @AuthenticationPrincipal UserDetails appUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "new-trip";
        }

        // Create new Fishing Trip by using fishingTripForm and connect this trip to user that is logged in and use that users userName.
        FishingTrip newFishingTrip = fishingTripService.newFishingTrip(fishingTripForm, appUser.getUsername());

        return "redirect:/fishing/"+newFishingTrip.getFishingTripId();
    }

    @GetMapping("/fishing/{fishingTripId}")
    public String getFishingTrip(@PathVariable(name = "fishingTripId") String fishingTripId, Model model){
        FishingTrip fishingTrip = fishingTripService.findByFishingTripId(fishingTripId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("fishingTrip", fishingTrip);
        return "fishing-trip";
    }

    @GetMapping("/fishing/{fishingTripId}/catch")
    public String getCatchForm(Model model){
        model.addAttribute("catchForm", new Species());
        return "new-catch";
    }

    @PostMapping("/fishing/{fishingTripId}/catch")
    public String createNewCatch(@Valid @ModelAttribute(name = "catchForm") CreateSpecies specie, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "new-catch";
        }

        Species newCatch = speciesService.newCatch(specie);

        // should return a different view.
        return "redirect:/index";
    }
    
}
