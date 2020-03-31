package ecutb.fishingtrip.service;

import ecutb.fishingtrip.dto.CreateAppUser;
import ecutb.fishingtrip.entity.AppUser;

import java.util.Optional;

public interface AppUserService {
    AppUser save(AppUser appUser);
    AppUser registerNew(CreateAppUser form);
    Optional<AppUser> findById(String userId);
    Optional<AppUser> findByUserName(String userName);
}
