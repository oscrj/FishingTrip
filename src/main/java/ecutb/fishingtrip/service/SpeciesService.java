package ecutb.fishingtrip.service;

import ecutb.fishingtrip.dto.CreateSpecies;
import ecutb.fishingtrip.entity.Species;

import java.util.List;

public interface SpeciesService {

    List<Species> findAll();

    List<Species> findByFishingTrip(String fishingTripId);

    Species newCatch(CreateSpecies form);

    Species saveAndUpdate(Species species);

    boolean delete(String speciesId);

}
