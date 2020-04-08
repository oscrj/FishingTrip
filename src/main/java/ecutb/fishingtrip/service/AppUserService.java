package ecutb.fishingtrip.service;

import ecutb.fishingtrip.dto.CreateAppUser;
import ecutb.fishingtrip.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserService {

    /**
     * Find all appUsers in database.
     * @return a list of all appUsers.
     */
    List<AppUser> findAll();

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

    Optional<AppUser> findByEmail(String email);

    /**
     *
     * @param appUserId
     * @return
     */
    boolean delete(String appUserId);
}
