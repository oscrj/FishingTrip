package ecutb.fishingtrip.service;

import ecutb.fishingtrip.data.AppUserRepository;
import ecutb.fishingtrip.data.AppUserRoleRepository;
import ecutb.fishingtrip.dto.CreateAppUser;
import ecutb.fishingtrip.entity.AppUser;
import ecutb.fishingtrip.entity.AppUserRole;
import ecutb.fishingtrip.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private AppUserRoleRepository roleRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, BCryptPasswordEncoder passwordEncoder, AppUserRoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     *
     * @param appUser
     * @return
     */
    @Override
    public AppUser saveAndUpdate(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    /**
     *
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppUser registerNew(CreateAppUser form) {
        //  Set role that later will be set to newUser.
        AppUserRole role = roleRepository.findByRole(UserRole.USER).orElseThrow(() -> new IllegalArgumentException("Couldn't find role"));
        Set<AppUserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(role);

        // Check if newUser shall be ADMIN?
        AppUser newUser = new AppUser(form.getUserName(),form.getFirstName(), form.getLastName(), form.getEmail(), passwordEncoder.encode(form.getPassword()), LocalDate.now());
        //  Save newUser in database
        newUser = appUserRepository.save(newUser);
        //  Set role to newUser using Set of roles(USER only).
        newUser.setAppUserRoles(userRoleSet);

        return newUser;

    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public Optional<AppUser> findById(String userId) {
        return appUserRepository.findById(userId);
    }

    /**
     *
     * @param userName
     * @return
     */
    @Override
    public Optional<AppUser> findByUserName(String userName) {
        return appUserRepository.findByUserNameIgnoreCase(userName);
    }
}
