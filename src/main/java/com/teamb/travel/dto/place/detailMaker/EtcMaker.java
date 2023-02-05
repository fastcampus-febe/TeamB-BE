package com.teamb.travel.dto.detailMaker;

import com.teamb.travel.entity.IsIndoor;
import com.teamb.travel.entity.Reply;
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
        if (isIndoor == null) {
            inOut = null;
        }else{
            inOut = isIndoor.getInOut();}

    }
}