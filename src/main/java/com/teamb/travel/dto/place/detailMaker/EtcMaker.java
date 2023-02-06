package com.teamb.travel.dto.place.detailMaker;

import com.teamb.travel.entity.IsIndoor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class EtcMaker {
    //reply
    private Double rate;
    //is_indoor
    private String inOut;

    public EtcMaker(@Nullable Double rate, @Nullable IsIndoor isIndoor) {
        if (rate == null) {
            this.rate = 0.0;
        } else {
            this.rate = rate;
        }
        if (isIndoor.getInOut() == null) {
            inOut = "";
        } else {
            inOut = isIndoor.getInOut();
        }
    }
}