package ecutb.fishingtrip.dto;

import javax.validation.constraints.NotBlank;
import static ecutb.fishingtrip.constants.message.ValidationMessages.SPECIFY_SPECIES;

public class CreateSpecies {

    @NotBlank(message = SPECIFY_SPECIES)
    private String species;
    private double length;
    private double weight;
    private String fishingLure;
    private String description;

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
}
