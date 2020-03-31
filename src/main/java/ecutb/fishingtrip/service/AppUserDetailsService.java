package ecutb.fishingtrip.service;

import ecutb.fishingtrip.data.AppUserRepository;
import ecutb.fishingtrip.entity.AppUser;
import ecutb.fishingtrip.entity.AppUserRole;
import ecutb.fishingtrip.entity.UserRole;
import ecutb.fishingtrip.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private AppUserRepository appUserRepository;

    @Autowired
    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<AppUser> optionalAppUser = appUserRepository.findByUserNameIgnoreCase(userName);

        // If user was found store it in appUser else Throw user was not found UsernameNotFoundException.
        if(optionalAppUser.isPresent()){
            AppUser user = optionalAppUser.get();
            //  In AppRole -> role = "USER".
            //  In SimpleGrantedAuthority role = "ROLE_USER".
            Collection<GrantedAuthority> authorities = new HashSet<>();
            for(AppUserRole appUserRole : user.getAppUserRoles()){
                // get ENUM by .name(). Param is a String
                authorities.add(new SimpleGrantedAuthority(appUserRole.getRole().name()));
            }
            return new AppUserDetails(user, authorities);
        }
        else{
            throw new UsernameNotFoundException("User with that username not found");
        }
    }

}
