package ecutb.fishingtrip.service;

import ecutb.fishingtrip.dto.CreateAppUser;
import ecutb.fishingtrip.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserService {

    List<AppUser> findAll();

    AppUser saveAndUpdate(AppUser appUser);

    AppUser registerNew(CreateAppUser form);

    Optional<AppUser> findById(String userId);

    Optional<AppUser> findByUserName(String userName);

    Optional<AppUser> findByEmail(String email);

    boolean delete(String appUserId);
}
