package ecutb.fishingtrip.service;

import ecutb.fishingtrip.dto.CreateFishingTrip;
import ecutb.fishingtrip.entity.FishingTrip;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FishingTripService{

    Set<FishingTrip> findAll();

    Optional<FishingTrip> findByFishingTripId(String id);

    FishingTrip newFishingTrip(CreateFishingTrip form, String userName);

    FishingTrip saveAndUpdate(FishingTrip fishingTrip);

    Set<FishingTrip> findByAppUser(String userName);

    boolean delete(String fishingTripId);

}
