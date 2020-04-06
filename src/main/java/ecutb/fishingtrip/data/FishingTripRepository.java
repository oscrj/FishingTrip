package ecutb.fishingtrip.data;

import ecutb.fishingtrip.entity.FishingTrip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FishingTripRepository extends CrudRepository<FishingTrip, String> {

    Optional<FishingTrip> findByFishingTripId(String id);

    List<FishingTrip> findAll();

    /**
     *
     * @param userName - User who have created FishingTrip.
     * @return all fishingtrips created by user with specific username
     */
    @Query("SELECT f FROM FishingTrip f WHERE f.appUser.userName = :username")
    List<FishingTrip> findByAppUser(@Param("username")String userName);

    List<FishingTrip> findByWaterType(String WaterType);

    List<FishingTrip> findByFishingMethod(String method);
}
