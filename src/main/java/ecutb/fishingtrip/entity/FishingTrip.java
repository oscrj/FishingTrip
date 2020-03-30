package ecutb.fishingtrip.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class FishingTrip {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String fishingTripId;
    private String fishingMethod;
    private String waterType;
    private String location;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "fisherman_id")
    private Fisherman fisherman;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "fishingTrip")
    private List<Species> fishCatches;

    public FishingTrip(){}

    public FishingTrip(String fishingMethod, String waterType, String location, Fisherman fisherman, List<Species> fishCatches) {
        this.fishingMethod = fishingMethod;
        this.waterType = waterType;
        this.location = location;
        this.fisherman = fisherman;
        this.fishCatches = fishCatches;
    }

    public String getFishingTripId() {
        return fishingTripId;
    }

    public String getFishingMethod() {
        return fishingMethod;
    }

    public void setFishingMethod(String fishingMethod) {
        this.fishingMethod = fishingMethod;
    }

    public String getWaterType() {
        return waterType;
    }

    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Fisherman getFisherman() {
        return fisherman;
    }

    public void setFisherman(Fisherman fisherman) {
        this.fisherman = fisherman;
    }

    public List<Species> getFishCatches() {
        return fishCatches;
    }

    public void setFishCatches(List<Species> speciesList) {
        this.fishCatches = speciesList;
    }

    // add fish to list.
    public void addCatch(Species fishCaught){
        fishCatches.add(fishCaught);
        fishCaught.setFishingTrip(this);
    }

    // remove fish from list
    public void removeCatch(Species fishCaught){
        fishCaught.setFishingTrip(null);
        fishCatches.remove(fishCaught);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FishingTrip that = (FishingTrip) o;
        return Objects.equals(fishingTripId, that.fishingTripId) &&
                Objects.equals(fishingMethod, that.fishingMethod) &&
                Objects.equals(waterType, that.waterType) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fishingTripId, fishingMethod, waterType, location);
    }

    @Override
    public String toString() {
        return "FishingTrip{" +
                "fishingTripId='" + fishingTripId + '\'' +
                ", fishingMethod='" + fishingMethod + '\'' +
                ", waterType='" + waterType + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
