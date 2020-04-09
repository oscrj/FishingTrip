package ecutb.fishingtrip.controller;

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
import java.util.Set;

@Controller
public class FishingTripController {

    private FishingTripService fishingTripService;
    private SpeciesService speciesService;

    @Autowired
    public FishingTripController(FishingTripService fishingTripService, SpeciesService speciesService) {
        this.fishingTripService = fishingTripService;
        this.speciesService = speciesService;
    }

    /**
     * Form that will create a new FishingTrip.
     * @param model user to show and receive data from that from.
     * @return view whit fishingTrip From.
     */
    @GetMapping("/fishing/gofishing")
    public String getFishingTripFrom(Model model){
        model.addAttribute("fishingTripForm", new CreateFishingTrip());
        return "new-trip";
    }

    /**
     * Will create a new FishingTrip by correct filled out form.
     * @param fishingTripForm used to fill out and receive information to create a new Fishing Trip.
     * @param appUser used to bind the newly created FishingTrip to the user that is logged in.
     * @param bindingResult catch errors regarding wrong inputs from user.
     * @return will return a view of information of the created fishingTrip from.
     */
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

    /**
     * Show information about the fishingTrip and also makes it possible to add caught fish during the fishing trip.
     * @param fishingTripId find the specific fishing Trip by its id. Will also be used to receive all caught fish during that specific trip.
     * @param model display the fishingTrips details also display all the caught fishes.
     * @return a view of information regarding the specific fishingTrip.
     */
    @GetMapping("/fishing/{fishingTripId}")
    public String getFishingTrip(@PathVariable(name = "fishingTripId") String fishingTripId, Model model){
        // Find fishingTrip using fishingTripId.
        FishingTrip fishingTrip = fishingTripService.findByFishingTripId(fishingTripId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("fishingTrip", fishingTrip);

        // Find all caught fish from fishingTrip with this Id.
        Set<Species> fishCaught = speciesService.findByFishingTrip(fishingTripId);
        model.addAttribute("fishCaught", fishCaught);

        return "fishing-trip";
    }

    /**
     * Update fishingTrip. (Only by ADMINS).
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
     * Update already created fishingTrips
     * @param id find fishingtrip using id.
     * @param updatedFishingTrip store the old information about the fishingTrip that will be updated. If user dont change input it will remain the same.
     * @return view of fishingTrip that was updated.
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

    /**
     * Find all fishingtrips made by spicific user
     * @param appUser find all fishingTrips made by logged in user and use users username to find them.
     * @param model view of all the trips user have done.
     * @return view of all the trips user have done.
     */
    @GetMapping("/fishing/trips")
    public String findAll(@AuthenticationPrincipal UserDetails appUser, Model model){
        Set<FishingTrip> fishingTrips = fishingTripService.findByAppUser(appUser.getUsername());
        model.addAttribute("fishingTrips", fishingTrips);
        return "fishing-trips";
    }

    /**
     * Redirect to more detailed page with information about FishingTrip
     * @param fishingTripId used to redirect to correct FishingTrip page.
     * @return detaild view if that FishingTrip
     */
    @GetMapping("fishing/trips/details")
    public String details(@RequestParam("fishingTripId") String fishingTripId){
        return "redirect:/fishing/"+fishingTripId;
    }

    /**
     * Delete FishingTrip.
     * @param fishingTripId delete fishing trip by using its ID.
     * @return a view of all fishingTrips.
     */
    @GetMapping("fishing/trips/delete")
    public String deleteFishingTrip(@RequestParam("fishingTripId") String fishingTripId){
        fishingTripService.delete(fishingTripId);
        return "redirect:/admin/findtrips";
    }

    /**
     * Form to create new catch.
     * @param fishingTripId used to bind the a specific FishingTrip to the caught fish. (will be used in createNewCatch()).
     * @param model Show and receive information from the Form
     * @return a view of catchForm that user will fill in.
     */
    @GetMapping("/fishing/catch")
    public String getCatchForm(@RequestParam("fishingTripId") String fishingTripId, Model model){
        FishingTrip fishingTrip = fishingTripService.findByFishingTripId(fishingTripId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("fishingTrip", fishingTrip);
        model.addAttribute("catchForm", new Species());
        return "new-catch";
    }

    /**
     * Form to create new catch.
     * @param fishingTripId used to bind catch to specific FishingTrip
     * @param specie used to create new Species with information from catchForm.
     * @param bindingResult saves errors from incorrect inputs from user
     * @return will display view of the fishingTrip the Species were caught on and also display the catch.
     */
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

    /**
     * Delete Catch.
     * @param speciesId used to find the Species(Catch) you want to delete.
     * @param fishingTripId used to redirect back to the Trips the Species you deleted were caught on.
     * @return the view of Fishing Trip with id fishingTripId.
     */
    @GetMapping("/fishing/catch/delete")
    public String deleteCatch(@RequestParam("speciesId") String speciesId, @RequestParam("fishingTripId") String fishingTripId){
        speciesService.delete(speciesId);
        return "redirect:/fishing/"+fishingTripId;
    }
}
