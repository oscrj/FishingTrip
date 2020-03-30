package ecutb.fishingtrip.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Species {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String speciesId;
    private String species;
    private String length;
    private String weight;
    private String fishingLure;
    private String description;

    @ManyToOne()
    @JoinColumn(name = "fishingTrip_id")
    private FishingTrip fishingTrip;

    public Species(){}

    public Species(String species, String length, String weight, String fishingLure, String description) {
        this.species = species;
        this.length = length;
        this.weight = weight;
        this.fishingLure = fishingLure;
        this.description = description;
    }

    public String getSpeciesId() {
        return speciesId;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFishingLure() {
        return fishingLure;
    }

    public void setFishingLure(String fishingLure) {
        this.fishingLure = fishingLure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FishingTrip getFishingTrip() {
        return fishingTrip;
    }

    public void setFishingTrip(FishingTrip fishingTrip) {
        this.fishingTrip = fishingTrip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species1 = (Species) o;
        return Objects.equals(speciesId, species1.speciesId) &&
                Objects.equals(species, species1.species) &&
                Objects.equals(length, species1.length) &&
                Objects.equals(weight, species1.weight) &&
                Objects.equals(fishingLure, species1.fishingLure) &&
                Objects.equals(description, species1.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speciesId, species, length, weight, fishingLure, description);
    }

    @Override
    public String toString() {
        return "Species{" +
                "speciesId='" + speciesId + '\'' +
                ", species='" + species + '\'' +
                ", length='" + length + '\'' +
                ", weight='" + weight + '\'' +
                ", fishingLure='" + fishingLure + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
