package ecutb.fishingtrip.dto;


import javax.validation.constraints.NotBlank;

import static ecutb.fishingtrip.constants.message.ValidationMessages.*;

public class CreateFishingTrip {

    @NotBlank(message = SPECIFY_FISHING_METHOD)
    private String fishingMethod;
    @NotBlank(message = SPECIFY_WATER_TYPE)
    private String waterType;
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

    public String getCheckLocationIsEmpty(){
         if(this.location.isEmpty()){
           return location = "Secrete location";
        }
         return location;
    }
}
