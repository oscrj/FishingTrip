package ecutb.fishingtrip.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ecutb.fishingtrip.constants.message.ValidationMessages.*;

public class CreateFishingTrip {

    @NotBlank(message = SPECIFY_FISHING_METHOD)
    private String fishingMethod;
    @NotBlank(message = SPECIFY_WATER_TYPE)
    @Size(min = 2, max = 150, message = WATER_FORMAT_MESSAGE)
    private String waterType;
    @Size(min = 1, max = 255, message = LOCATION_FORMAT_MESSAGE)
    private String location;

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
}
