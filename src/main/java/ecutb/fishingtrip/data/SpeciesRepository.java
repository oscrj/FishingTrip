package ecutb.fishingtrip.data;

import ecutb.fishingtrip.entity.Species;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpeciesRepository extends CrudRepository<Species, String> {

    Optional<Species> findById(String id);

    List<Species> findAll();

    /**
     * @param fishingTripId id of the trip where fish were caught.
     * @return a list of every caught fish if that trip.
     */
    @Query("SELECT c FROM Species c WHERE c.fishingTrip.fishingTripId = :fishingTripId")
    List<Species> findByFishingTrip(@Param("fishingTripId")String fishingTripId);

}
