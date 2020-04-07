package ecutb.fishingtrip.controller;

import ecutb.fishingtrip.dto.CreateFishingTrip;
import ecutb.fishingtrip.dto.CreateSpecies;
import ecutb.fishingtrip.dto.UpdateSpecies;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class FishingTripController {

    private FishingTripService fishingTripService;
    private SpeciesService speciesService;

    @Autowired
    public FishingTripController(FishingTripService fishingTripService, SpeciesService speciesService) {
        this.fishingTripService = fishingTripService;
        this.speciesService = speciesService;
    }

    @GetMapping("/fishing/trips")
    public String findAll(@AuthenticationPrincipal UserDetails appUser, Model model){
        List<FishingTrip> fishingTrips = fishingTripService.findByAppUser(appUser.getUsername());
        model.addAttribute("fishingTrips", fishingTrips);
        return "fishing-trips";
    }

    @GetMapping("fishing/trips/details")
    public String details(@RequestParam("fishingTripId") String fishingTripId){
        return "redirect:/fishing/"+fishingTripId;
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

        if(appUser == null){
            return "redirect:/login";
        }

        // Create new Fishing Trip by using fishingTripForm and connect this trip to user that is logged in and use that users userName.
        FishingTrip newFishingTrip = fishingTripService.newFishingTrip(fishingTripForm, appUser.getUsername());

        return "redirect:/fishing/"+newFishingTrip.getFishingTripId();
    }

    @GetMapping("/fishing/{fishingTripId}")
    public String getFishingTrip(@PathVariable(name = "fishingTripId") String fishingTripId, Model model){
        // Find fishingTrip using fishingTripId.
        FishingTrip fishingTrip = fishingTripService.findByFishingTripId(fishingTripId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("fishingTrip", fishingTrip);

        // Find all caught fish from fishingTrip with this Id.
        List<Species> fishCaught = speciesService.findByFishingTrip(fishingTripId);
        model.addAttribute("fishCaught", fishCaught);

        return "fishing-trip";
    }

    @GetMapping("/fishing/catch")
    public String getCatchForm(@RequestParam("fishingTripId") String fishingTripId, Model model){
        FishingTrip fishingTrip = fishingTripService.findByFishingTripId(fishingTripId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("fishingTrip", fishingTrip);
        model.addAttribute("catchForm", new Species());
        return "new-catch";
    }

    @PostMapping("/fishing/catch")
    public String createNewCatch(@RequestParam("fishingTripId") String fishingTripId, @Valid @ModelAttribute(name = "catchForm") CreateSpecies specie, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "new-catch";
        }

        Species newCatch = speciesService.newCatch(specie, fishingTripId);
        return "redirect:/fishing/"+fishingTripId;
    }

    /**
     * Update catch
     */
    @GetMapping("/fishing/catch/update")
    public String updateCatchForm(@RequestParam("speciesId") String speciesId, @RequestParam("fishingTripId") String fishingTripId, Model model){
        UpdateSpecies updateSpecies = new UpdateSpecies();
        Species species = speciesService.findBySpeciesId(speciesId).orElseThrow(IllegalArgumentException::new);

        updateSpecies.setSpecies(species.getSpecies());
        updateSpecies.setLength(species.getLength());
        updateSpecies.setWeight(species.getWeight());
        updateSpecies.setFishingLure(species.getFishingLure());
        updateSpecies.setDescription(species.getDescription());

        model.addAttribute("updateSpeciesForm", new UpdateSpecies());

        // Need fishingTrip to later redirect to this fishingTrip.
        FishingTrip fishingTrip = fishingTripService.findByFishingTripId(fishingTripId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("fishingTrip", fishingTrip);
        //  model.addAttribute("species", species);
        return "update-catch";
    }

    @PostMapping("/fishing/catch/update")
    public String createUpdateCatch(@RequestParam("speciesId") String speciesId, @RequestParam("fishingTripId") String fishingTripId,
                                    @Valid @ModelAttribute("updateSpeciesForm") UpdateSpecies updateSpecies, BindingResult bindingResult){

        Species originalSpecies = speciesService.findBySpeciesId(speciesId).orElseThrow(IllegalArgumentException::new);

        if(bindingResult.hasErrors()){
            return "update-catch";
        }

        originalSpecies.setSpecies(updateSpecies.getSpecies());
        originalSpecies.setLength(updateSpecies.getLength());
        originalSpecies.setWeight(updateSpecies.getWeight());
        originalSpecies.setFishingLure(updateSpecies.getFishingLure());
        originalSpecies.setDescription(updateSpecies.getDescription());

        speciesService.saveAndUpdate(originalSpecies);

        return "redirect:/fishing/"+fishingTripId;
    }


    @GetMapping("/fishing/catch/delete")
    public String deleteCatch(@RequestParam("speciesId") String speciesId, @RequestParam("fishingTripId") String fishingTripId){
        speciesService.delete(speciesId);
        return "redirect:/fishing/"+fishingTripId;
    }
}
