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

    public EtcMaker(@Nullable Reply reply, @Nullable IsIndoor isIndoor) {
        if (reply == null) {
            rate = 0.0;
        } else {
        rate = reply.isRate();
        }
        if (isIndoor == null) {
            inOut = null;
        }else{
            inOut = isIndoor.getInOut();}

    }
}
