package ecutb.fishingtrip.data;

import ecutb.fishingtrip.entity.Species;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SpeciesRepository extends CrudRepository<Species, String> {

    Optional<Species> findById(String id);

    List<Species> findAll();
}
