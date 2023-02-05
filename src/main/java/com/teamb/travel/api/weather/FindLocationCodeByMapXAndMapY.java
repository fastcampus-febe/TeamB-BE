package com.teamb.travel.api.weather;

import org.springframework.stereotype.Component;

@Component
public class FindLocationCodeByMapXAndMapY {

    // 위도, 경도로 지역을 찾아 지역코드를 반환함.
    // MiddleLastWeatherDetailApi와 TempApi에서 사용함.

    public String findLocation(String mapX, String mapY) {
        if ((mapX.compareTo("124.6163553056") >= 0 && mapX.compareTo("127.7542483851") <= 0) && (mapY.compareTo("36.9044335076") >= 0 && mapY.compareTo("38.2366849576") <= 0)) {
            return "11B00000";
        } else if ((mapX.compareTo("127.1357007146") >= 0 && mapX.compareTo("128.9551308193") <= 0) && (mapY.compareTo("37.0795432684") >= 0 && mapY.compareTo("38.322303828") <= 0)) {
            return "11D10000";
        } else if ((mapX.compareTo("128.3019017658") >= 0 && mapX.compareTo("129.3615827014") <= 0) && (mapY.compareTo("37.0872205462") >= 0 && mapY.compareTo("38.5439669684") <= 0)) {
            return "11D20000";
        } else if ((mapX.compareTo("126.0085674237") >= 0 && mapX.compareTo("127.5945843255") <= 0) && (mapY.compareTo("35.9940288724") >= 0 && mapY.compareTo("37.0517084677") <= 0)) {
            return "11C20000";
        } else if ((mapX.compareTo("127.3223365761") >= 0 && mapX.compareTo("128.5107838513") <= 0) && (mapY.compareTo("36.0414926756") >= 0 && mapY.compareTo("37.2282590969") <= 0)) {
            return "11C10000";
        } else if ((mapX.compareTo("125.1105924951") >= 0 && mapX.compareTo("128.7392429984") <= 0) && (mapY.compareTo("33.9866161191") >= 0 && mapY.compareTo("36.9824570262") <= 0)) {
            return "11F20000";
        } else if ((mapX.compareTo("125.9686621609") >= 0 && mapX.compareTo("127.8825896044") <= 0) && (mapY.compareTo("35.3174723644") >= 0 && mapY.compareTo("36.1382803303") <= 0)) {
            return "11F10000";
        } else if ((mapX.compareTo("127.8331932123") >= 0 && mapX.compareTo("131.86968908") <= 0) && (mapY.compareTo("35.4577017849") >= 0 && mapY.compareTo("37.5494187347") <= 0)) {
            return "11H10000";
        } else if ((mapX.compareTo("127.6072321881") >= 0 && mapX.compareTo("129.4605502797") <= 0) && (mapY.compareTo("34.5644870946") >= 0 && mapY.compareTo("35.8844465003") <= 0)) {
            return "11H20000";
        } else if ((mapX.compareTo("126.1475739333") >= 0 && mapX.compareTo("126.9685512558") <= 0) && (mapY.compareTo("33.1170102919") >= 0 && mapY.compareTo("33.9612503055") <= 0)) {
            return "11G00000";
        }

        return null;
    }
}
