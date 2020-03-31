package ecutb.fishingtrip.data;

import ecutb.fishingtrip.entity.FishingTrip;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FishingTripRepository extends CrudRepository<FishingTrip, String> {
    Optional<FishingTrip> findById(String id);
    List<FishingTrip> findAll();
}
