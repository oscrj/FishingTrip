package ecutb.fishingtrip.service;

import ecutb.fishingtrip.dto.CreateSpecies;
import ecutb.fishingtrip.entity.Species;

import java.util.Optional;
import java.util.Set;

public interface SpeciesService {

    Optional<Species> findBySpeciesId(String SpeciesId);

    Set<Species> findAll();

    Set<Species> findByFishingTrip(String fishingTripId);

    Species newCatch(CreateSpecies form, String fishingTripId);

    Species saveAndUpdate(Species species);

    boolean delete(String speciesId);

}
