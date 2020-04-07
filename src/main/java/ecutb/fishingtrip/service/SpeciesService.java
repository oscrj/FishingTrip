package ecutb.fishingtrip.service;

import ecutb.fishingtrip.dto.CreateSpecies;
import ecutb.fishingtrip.entity.Species;

import java.util.List;
import java.util.Optional;

public interface SpeciesService {

    Optional<Species> findBySpeciesId(String SpeciesId);

    List<Species> findAll();

    List<Species> findByFishingTrip(String fishingTripId);

    Species newCatch(CreateSpecies form, String fishingTripId);

    Species saveAndUpdate(Species species);

    boolean delete(String speciesId);

}
