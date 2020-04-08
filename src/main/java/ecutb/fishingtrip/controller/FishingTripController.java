package ecutb.fishingtrip.controller;

import ecutb.fishingtrip.data.SpeciesRepository;
import ecutb.fishingtrip.dto.CreateFishingTrip;
import ecutb.fishingtrip.dto.CreateSpecies;
import ecutb.fishingtrip.dto.UpdateFishingTrip;
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

    /**
     *
     * @param fishingTripId used to receive the fishingTrip that will be updated.
     * @param model - used to receive the new updated information in view update-Trip
     * @return will return the view update-trip containing the update form for the user to fill in.
     */
    @GetMapping("/fishing/trip/update")
    public String getUpdateFishingTripForm(@RequestParam("fishingTripId") String fishingTripId, Model model){
        UpdateFishingTrip updatedFishingTrip = new UpdateFishingTrip();
        FishingTrip fishingTrip = fishingTripService.findByFishingTripId(fishingTripId).orElseThrow(IllegalArgumentException::new);

        updatedFishingTrip.setFishingTripId(fishingTrip.getFishingTripId());
        updatedFishingTrip.setFishingMethod(fishingTrip.getFishingMethod());
        updatedFishingTrip.setWaterType(fishingTrip.getWaterType());
        updatedFishingTrip.setLocation(fishingTrip.getLocation());

        model.addAttribute("updatedFishingTripForm", fishingTrip);
        return "update-trip";
    }

    /**
     *
     * @param id
     * @return
     */
    @PostMapping("/fishing/trip/update")
    public String updateFishingTripForm(@RequestParam("id") String id,
                                        @Valid @ModelAttribute("updatedFishingTripForm") UpdateFishingTrip updatedFishingTrip, BindingResult bindingResult){
        FishingTrip originalFishingTrip = fishingTripService.findByFishingTripId(id).orElseThrow(IllegalArgumentException::new);

        originalFishingTrip.setFishingMethod(updatedFishingTrip.getFishingMethod());
        originalFishingTrip.setWaterType(updatedFishingTrip.getWaterType());
        originalFishingTrip.setLocation(updatedFishingTrip.getLocation());

        fishingTripService.saveAndUpdate(originalFishingTrip);

        return "redirect:/fishing/"+id;
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

    /**
     *
     * @param fishingTripId
     * @param model
     * @return
     */
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
     * @param speciesId - will receive the correct Species to update.
     * @param fishingTripId - use this id to redirect back to the Trip the fish was caught on. (will only be past by and later used in createUpdateCatch()).
     * @param model - used to receive the new updated information in view update-catch
     * @return will return the view update-catch containing the update form for the user to fill in.
     */
    @GetMapping("/fishing/catch/update")
    public String updateCatchForm(@RequestParam("speciesId") String speciesId, @RequestParam("fishingTripId") String fishingTripId, Model model){
        UpdateSpecies updateSpecies = new UpdateSpecies();
        Species species = speciesService.findBySpeciesId(speciesId).orElseThrow(IllegalArgumentException::new);

        updateSpecies.setSpeciesId(species.getSpeciesId());
        updateSpecies.setSpecies(species.getSpecies());
        updateSpecies.setLength(species.getLength());
        updateSpecies.setWeight(species.getWeight());
        updateSpecies.setFishingLure(species.getFishingLure());
        updateSpecies.setDescription(species.getDescription());

        model.addAttribute("updateForm", species);

        //  Need fishingTrip to redirect to this fishingTrip in method createUpdateCatch.
        FishingTrip fishingTrip = fishingTripService.findByFishingTripId(fishingTripId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("fishingTrip", fishingTrip);
        return "update-catch";
    }

    /**
     *
     * @param id of the object that will be updated but will keep the same id because its the same object with updated values.
     * @param fishingTripId - use this id to redirect back to the Trip the fish was caught on.
     * @param updateSpecies - Will use the updatedSpecies to set the new values to originalSpecies.
     * @param bindingResult - If there were any validation errors when user filled in the form.
     * @return return the view fishing-trip that will show the trip with its new updated catch.
     */
    @PostMapping("/fishing/catch/update")
    public String createUpdateCatch(@RequestParam("id") String id, @RequestParam("fishingTripId") String fishingTripId,
                                    @Valid @ModelAttribute("updateForm") UpdateSpecies updateSpecies, BindingResult bindingResult){

        Species originalSpecies = speciesService.findBySpeciesId(id).orElseThrow(IllegalArgumentException::new);

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
