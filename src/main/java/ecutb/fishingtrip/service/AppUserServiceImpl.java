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

    @Override
    public Set<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser saveAndUpdate(AppUser appUser) {
        //  Set role ADMIN if the updatedAppUser is checked admin by an ADMIN.
        AppUserRole adminRole = roleRepository.findByRole(UserRole.ADMIN).orElseThrow(() -> new IllegalArgumentException("Couldn't find role"));
        Set<AppUserRole> adminRoleSet = new HashSet<>();

        //  USER, ADMIN role
        adminRoleSet.add(adminRole);

        if(appUser.isAdmin()){
            appUser.setAppUserRoles(adminRoleSet);
            return appUserRepository.save(appUser);
        }

        return appUserRepository.save(appUser);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppUser registerNew(CreateAppUser form) {
        //  Set role that later will be set to newUser.
        AppUserRole adminRole = roleRepository.findByRole(UserRole.ADMIN).orElseThrow(() -> new IllegalArgumentException("Couldn't find role"));
        Set<AppUserRole> adminRoleSet = new HashSet<>();

        AppUserRole userRole = roleRepository.findByRole(UserRole.USER).orElseThrow(() -> new IllegalArgumentException("Couldn't find role"));
        Set<AppUserRole> userRoleSet = new HashSet<>();

        //  USER, ADMIN role
        adminRoleSet.add(adminRole);
        adminRoleSet.add(userRole);

        //  USER role
        userRoleSet.add(userRole);

        AppUser newUser = new AppUser(form.getUserName(),form.getFirstName(), form.getLastName(), form.getEmail(), passwordEncoder.encode(form.getPassword()), LocalDate.now());
        //  Save newUser in database
        newUser = appUserRepository.save(newUser);

        //  Check if newUser shall be ADMIN?
        if(form.isAdmin()){
            newUser.setAppUserRoles(adminRoleSet);
            return newUser;
        }
        //  Set role to newUser using Set of roles(USER only).
        newUser.setAppUserRoles(userRoleSet);
        return newUser;
    }

    @Override
    public Optional<AppUser> findById(String userId) {
        return appUserRepository.findById(userId);
    }

    @Override
    public Optional<AppUser> findByUserName(String userName) {
        return appUserRepository.findByUserNameIgnoreCase(userName);
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public boolean delete(String appUserId) {
        appUserRepository.deleteById(appUserId);
        return appUserRepository.existsById(appUserId);
    }
}
