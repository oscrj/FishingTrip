package ecutb.fishingtrip.service;

import ecutb.fishingtrip.data.FishingTripRepository;
import ecutb.fishingtrip.data.SpeciesRepository;
import ecutb.fishingtrip.dto.CreateSpecies;
import ecutb.fishingtrip.entity.Species;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    private SpeciesRepository speciesRepository;
    private FishingTripRepository fishingTripRepository;

    @Autowired
    public SpeciesServiceImpl(SpeciesRepository speciesRepository, FishingTripRepository fishingTripRepository) {
        this.speciesRepository = speciesRepository;
        this.fishingTripRepository = fishingTripRepository;
    }

    @Override
    public List<Species> findAll() {
        return speciesRepository.findAll();
    }

    @Override
    public List<Species> findByFishingTrip(String fishingTripId) {
        return speciesRepository.findByFishingTrip(fishingTripId);
    }

    @Override
    public Species newCatch(CreateSpecies form){

        /*
        Find a way too bind catch with fishingTrip ID...
         */

        Species newCatch = new Species(form.getSpecies(), form.getLength(), form.getWeight(), form.getFishingLure(), form.getDescription());

        return speciesRepository.save(newCatch);
    }

    @Override
    public Species saveAndUpdate(Species species) {
        return speciesRepository.save(species);
    }

    @Override
    public boolean delete(String speciesId) {
        speciesRepository.deleteById(speciesId);
        // check if message is deleted.
        return speciesRepository.existsById(speciesId);
    }
}
