package ecutb.fishingtrip.dto;

import javax.validation.constraints.NotBlank;

import static ecutb.fishingtrip.constants.message.ValidationMessages.SPECIFY_FISHING_METHOD;
import static ecutb.fishingtrip.constants.message.ValidationMessages.SPECIFY_WATER_TYPE;

public class UpdateFishingTrip {

    @NotBlank
    private String fishingTripId;
    @NotBlank(message = SPECIFY_FISHING_METHOD)
    private String fishingMethod;
    @NotBlank(message = SPECIFY_WATER_TYPE)
    private String waterType;
    private String location;

    public String getFishingTripId() {
        return fishingTripId;
    }

    public void setFishingTripId(String fishingTripId) {
        this.fishingTripId = fishingTripId;
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

    public String getCheckLocationIsEmpty(){
        if(this.location.isEmpty()){
            return location = "Secrete location";
        }
        return location;
    }
}
