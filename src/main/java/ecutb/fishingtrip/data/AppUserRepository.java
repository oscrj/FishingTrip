package ecutb.fishingtrip.data;

import ecutb.fishingtrip.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, String> {

    List<AppUser> findAll();

    Optional<AppUser> findByUserNameIgnoreCase(String userName);

    Optional<AppUser> findByEmailIgnoreCase(String email);
}
