package ecutb.fishingtrip.data;

import ecutb.fishingtrip.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface AppUserRepository extends CrudRepository<AppUser, String> {

    Set<AppUser> findAll();

    Optional<AppUser> findByUserNameIgnoreCase(String userName);

    Optional<AppUser> findByEmailIgnoreCase(String email);
}
