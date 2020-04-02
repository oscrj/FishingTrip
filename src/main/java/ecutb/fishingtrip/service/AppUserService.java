package ecutb.fishingtrip.service;

import ecutb.fishingtrip.dto.CreateAppUser;
import ecutb.fishingtrip.entity.AppUser;

import java.util.Optional;

public interface AppUserService {
    /**
     *
     * @param appUser
     * @return
     */
    AppUser saveAndUpdate(AppUser appUser);

    /**
     *
     * @param form
     * @return
     */
    AppUser registerNew(CreateAppUser form);

    /**
     *
     * @param userId
     * @return
     */
    Optional<AppUser> findById(String userId);

    /**
     *
     * @param userName
     * @return
     */
    Optional<AppUser> findByUserName(String userName);
}
