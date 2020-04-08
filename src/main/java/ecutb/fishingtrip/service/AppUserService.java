package ecutb.fishingtrip.service;

import ecutb.fishingtrip.dto.CreateAppUser;
import ecutb.fishingtrip.entity.AppUser;


import java.util.Optional;
import java.util.Set;

public interface AppUserService {

    Set<AppUser> findAll();

    AppUser saveAndUpdate(AppUser appUser);

    AppUser registerNew(CreateAppUser form);

    Optional<AppUser> findById(String userId);

    Optional<AppUser> findByUserName(String userName);

    Optional<AppUser> findByEmail(String email);

    boolean delete(String appUserId);
}
