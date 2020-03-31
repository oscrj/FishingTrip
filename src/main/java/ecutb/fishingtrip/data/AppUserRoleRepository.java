package ecutb.fishingtrip.data;

import ecutb.fishingtrip.entity.AppUserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserRoleRepository extends CrudRepository<AppUserRole, String> {
    Optional<AppUserRole> findByRole(AppUserRole role);
}
