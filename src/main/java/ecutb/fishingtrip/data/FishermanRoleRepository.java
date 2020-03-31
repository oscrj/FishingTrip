package ecutb.fishingtrip.data;

import ecutb.fishingtrip.entity.FishermanRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FishermanRoleRepository extends CrudRepository<FishermanRole, String> {
    Optional<FishermanRole> findByRole(FishermanRole role);
}
