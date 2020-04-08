package ecutb.fishingtrip;

import ecutb.fishingtrip.data.AppUserRepository;
import ecutb.fishingtrip.data.AppUserRoleRepository;
import ecutb.fishingtrip.data.FishingTripRepository;
import ecutb.fishingtrip.data.SpeciesRepository;
import ecutb.fishingtrip.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Seeder {

    private AppUserRepository appUserRepository;
    private AppUserRoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private FishingTripRepository fishingTripRepository;
    private SpeciesRepository speciesRepository;

    @Autowired
    public Seeder(AppUserRepository appUserRepository, AppUserRoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, FishingTripRepository fishingTripRepository, SpeciesRepository speciesRepository) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.fishingTripRepository = fishingTripRepository;
        this.speciesRepository = speciesRepository;
    }

    @PostConstruct
    @Transactional
    public void init(){

        Set<AppUserRole> roles = Arrays.stream(UserRole.values())
                .map(userRole -> roleRepository.save(new AppUserRole(userRole)))
                .collect(Collectors.toSet());

        AppUserRole user = roleRepository.findByRole(UserRole.USER).orElseThrow(() -> new IllegalArgumentException("Couldn't find role"));
        Set<AppUserRole> userRole = new HashSet<>();
        userRole.add(user);

        //  Create APP_ADMIN user:
        AppUser appAdmin = new AppUser("admin", "Foo", "Bar", "foo.bar@admin.com", passwordEncoder.encode("admin1234"),LocalDate.now());
        appAdmin.setAppUserRoles(roles);
        appUserRepository.save(appAdmin);

        // Create APP_USER user:
        AppUser appUser = new AppUser("oscrj", "Oscar","Johanneson", "oscar.johanneson@gmail.com", passwordEncoder.encode("password1"), LocalDate.now());
        appUser.setAppUserRoles(userRole);
        appUserRepository.save(appUser);

        AppUser appUser2 = new AppUser("test", "John","Doe", "john.doe@gmail.com", passwordEncoder.encode("password12"), LocalDate.now());
        appUser2.setAppUserRoles(userRole);
        appUserRepository.save(appUser2);

        AppUser appUser3 = new AppUser("test2", "Jane","Doe", "jane.doe@gmail.com", passwordEncoder.encode("password123"), LocalDate.now());
        appUser3.setAppUserRoles(userRole);
        appUserRepository.save(appUser3);

        FishingTrip trip = new FishingTrip("Spinning", "Lake", "Bergundasjön", LocalDate.now());
        trip.setAppUser(appUser);
        fishingTripRepository.save(trip);

        FishingTrip trip2 = new FishingTrip("Fly Fishing", "Pond", "Secret spot", LocalDate.now().minusDays(5));
        trip2.setAppUser(appUser);
        fishingTripRepository.save(trip2);

        FishingTrip trip3 = new FishingTrip("Trolling", "Ocean", "Kattegatt", LocalDate.now().minusDays(20));
        trip3.setAppUser(appUser);
        fishingTripRepository.save(trip3);

        Species fish = new Species("Northern pike", 85, 6.7, "Wobbler", "Cloudy day", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        fish.setFishingTrip(trip);
        speciesRepository.save(fish);

        Species fish2 = new Species("Perch", 35, 1.2, "CrankBait", "Cloudy day", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        fish2.setFishingTrip(trip);
        speciesRepository.save(fish2);

        Species fish3 = new Species("Salmon", 65, 4.6, "Wobbler", "Sunny day", LocalDateTime.now().minusDays(20).truncatedTo(ChronoUnit.MINUTES));
        fish3.setFishingTrip(trip3);
        speciesRepository.save(fish3);

        Species fish4 = new Species("Salmon", 35, 1.6, "Wobbler", "Sunny day", LocalDateTime.now().minusDays(20).truncatedTo(ChronoUnit.MINUTES));
        fish4.setFishingTrip(trip3);
        speciesRepository.save(fish4);
    }
}
