package ecutb.fishingtrip.service;

import ecutb.fishingtrip.dto.CreateFishingTrip;
import ecutb.fishingtrip.entity.FishingTrip;

import java.util.List;
import java.util.Optional;

public interface FishingTripService{

    List<FishingTrip> findAll();

    Optional<FishingTrip> findByFishingTripId(String id);

    FishingTrip newFishingTrip(CreateFishingTrip form, String userName);

    FishingTrip saveAndUpdate(FishingTrip fishingTrip);

    List<FishingTrip> findByAppUser(String userName);

    boolean delete(String fishingTripId);

}
