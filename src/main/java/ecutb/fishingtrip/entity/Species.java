package ecutb.fishingtrip.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Species {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String speciesId;
    private String species;
    private double length;
    private double weight;
    private String fishingLure;
    private String description;
    private LocalDateTime timeStamp;

    @ManyToOne()
    @JoinColumn(name = "fishingTrip_id")
    private FishingTrip fishingTrip;

    public Species(){}

    public Species(String species, double length, double weight, String fishingLure, String description, LocalDateTime timeStamp) {
        this.species = species;
        this.length = length;
        this.weight = weight;
        this.fishingLure = fishingLure;
        this.description = description;
        this.timeStamp = timeStamp;
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

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
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

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public FishingTrip getFishingTrip() {
        return fishingTrip;
    }

    public void setFishingTrip(FishingTrip fishingTrip) {
        this.fishingTrip = fishingTrip;
    }

    /**
     * Format LocalDateTime
     * @return will only return the hour and minute the Species where created.
     */
    public String getFormattedDateTime(){
        return timeStamp.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species1 = (Species) o;
        return Double.compare(species1.length, length) == 0 &&
                Double.compare(species1.weight, weight) == 0 &&
                Objects.equals(speciesId, species1.speciesId) &&
                Objects.equals(species, species1.species) &&
                Objects.equals(fishingLure, species1.fishingLure) &&
                Objects.equals(description, species1.description) &&
                Objects.equals(timeStamp, species1.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speciesId, species, length, weight, fishingLure, description, timeStamp);
    }

    @Override
    public String toString() {
        return "Species{" +
                "speciesId='" + speciesId + '\'' +
                ", species='" + species + '\'' +
                ", length=" + length +
                ", weight=" + weight +
                ", fishingLure='" + fishingLure + '\'' +
                ", description='" + description + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
