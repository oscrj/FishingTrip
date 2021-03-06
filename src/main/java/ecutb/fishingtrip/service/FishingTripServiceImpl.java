package ecutb.fishingtrip.service;

import ecutb.fishingtrip.data.AppUserRepository;
import ecutb.fishingtrip.data.FishingTripRepository;
import ecutb.fishingtrip.dto.CreateFishingTrip;
import ecutb.fishingtrip.entity.AppUser;
import ecutb.fishingtrip.entity.FishingTrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FishingTripServiceImpl implements FishingTripService {

    private FishingTripRepository fishingTripRepository;
    private AppUserRepository appUserRepository;

    @Autowired
    public FishingTripServiceImpl(FishingTripRepository fishingTripRepository, AppUserRepository appUserRepository) {
        this.fishingTripRepository = fishingTripRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Set<FishingTrip> findAll() {
        return fishingTripRepository.findAll();
    }

    @Override
    public Optional<FishingTrip> findByFishingTripId(String id) {
        return fishingTripRepository.findByFishingTripId(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public FishingTrip newFishingTrip(CreateFishingTrip form, String userName) {
        AppUser loggedInUser = appUserRepository.findByUserNameIgnoreCase(userName).orElseThrow(() -> new UsernameNotFoundException("Requested user could not be found"));

        FishingTrip newFishingTrip = new FishingTrip(form.getFishingMethod(), form.getWaterType(), form.getCheckLocationIsEmpty(), LocalDate.now());
        newFishingTrip.setAppUser(loggedInUser);

        return fishingTripRepository.save(newFishingTrip);
    }

    @Override
    public FishingTrip saveAndUpdate(FishingTrip fishingTrip) {
        return fishingTripRepository.save(fishingTrip);
    }

    @Override
    public Set<FishingTrip> findByAppUser(String userName) {
        return fishingTripRepository.findByAppUser(userName);
    }


    @Override
    public boolean delete(String fishingTripId) {
        fishingTripRepository.deleteById(fishingTripId);
        return fishingTripRepository.existsById(fishingTripId);
    }
}
