package ecutb.fishingtrip.data;

import ecutb.fishingtrip.entity.Fisherman;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FishermanRepository extends CrudRepository<Fisherman, String> {
    Optional<Fisherman> findByUserNameIgnoreCase(String userName);
    Optional<Fisherman> findByEmailIgnoreCase(String email);
}
