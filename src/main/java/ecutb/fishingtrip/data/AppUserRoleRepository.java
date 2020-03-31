package ecutb.fishingtrip.data;

import ecutb.fishingtrip.entity.AppUserRole;
import ecutb.fishingtrip.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserRoleRepository extends CrudRepository<AppUserRole, String> {
    Optional<AppUserRole> findByRole(UserRole role);
}
