package ecutb.fishingtrip.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private LocalDate date;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "fisherman_id")
    private Fisherman fisherman;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "fishingTrip")
    private List<Species> fishCaught;

    public FishingTrip(){}

    public FishingTrip(String fishingMethod, String waterType, String location, LocalDate date) {
        this.fishingMethod = fishingMethod;
        this.waterType = waterType;
        this.location = location;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Fisherman getFisherman() {
        return fisherman;
    }

    public void setFisherman(Fisherman fisherman) {
        this.fisherman = fisherman;
    }

    public List<Species> getFishCaught() {
        return fishCaught;
    }

    public void setFishCaught(List<Species> speciesList) {
        if(this.fishCaught == null) fishCaught = new ArrayList<>();
        this.fishCaught = speciesList;
    }

    // add fish to list.
    public void addCatch(Species fish){
        if(this.fishCaught == null) fishCaught = new ArrayList<>();
        fishCaught.add(fish);
        fish.setFishingTrip(this);
    }

    // remove fish from list
    public void removeCatch(Species fish){
        fish.setFishingTrip(null);
        fishCaught.remove(fish);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FishingTrip that = (FishingTrip) o;
        return Objects.equals(fishingTripId, that.fishingTripId) &&
                Objects.equals(fishingMethod, that.fishingMethod) &&
                Objects.equals(waterType, that.waterType) &&
                Objects.equals(location, that.location) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fishingTripId, fishingMethod, waterType, location, date);
    }

    @Override
    public String toString() {
        return "FishingTrip{" +
                "fishingTripId='" + fishingTripId + '\'' +
                ", fishingMethod='" + fishingMethod + '\'' +
                ", waterType='" + waterType + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                '}';
    }
}
