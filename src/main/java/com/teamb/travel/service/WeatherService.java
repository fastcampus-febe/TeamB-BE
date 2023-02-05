package com.teamb.travel.service;

import com.teamb.travel.dto.WeatherLastResDTO;
import com.teamb.travel.dto.WeatherShortMiddleResDTO;
import com.teamb.travel.dto.WeatherResDTO;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    public WeatherResDTO findWeatherResDTOByMapXAndMapY (String mapX, String mapY) throws IOException, ParseException {
        List<WeatherShortMiddleResDTO> shortRes = selectShortWeather(mapX, mapY);
        List<WeatherShortMiddleResDTO> middleRes = selectMiddleWeather(mapX, mapY);
        List<WeatherLastResDTO> lastRes = selectLastWeather(mapX, mapY);

        WeatherResDTO weatherResDto = new WeatherResDTO(shortRes, middleRes, lastRes);
        return weatherResDto;
    }

    public List<WeatherShortMiddleResDTO> selectShortWeather(String mapX, String mapY) throws IOException, ParseException {
        List<WeatherShortMiddleResDTO> shortResDTOs = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            JSONArray shortJSONArray = selectShortWeatherJSON(mapX, mapY, i);

            // JSONArray(shortJSONArray)에서 필요한 값을 찾기 위해, index로 쓰일 값을 초기화함
            int eightAmPOPRainNum = 0;

            // 필요한 값(오전 8시의 강수확률(POP) = eightAmPOPRainNum)을 찾기 위해서
            // for문(반복문)을 돌며 JSONArray(shortJSONArray)에서 해당 index 값을 찾음
            for (int k=0; k<shortJSONArray.size(); k++) {
                JSONObject jsonObject = (JSONObject)shortJSONArray.get(k);

                if (jsonObject.get("category").equals("POP") && jsonObject.get("fcstTime").equals("0800")) {
                    eightAmPOPRainNum = k;
                    break;
                }
            }

            // 사용하는 날씨 api (https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15084084) 에서
            // eightAmPOPRainNum(오전 8시의 강수확률(POP) 데이터가 있는 index 값)을 알면 나머지 필요한 정보의 index 값도 알 수 있으므로
            // 위에서 찾은 eightAmPOPRainNum로 나머지 index 값도 구함.
            int fivePmPOPRainNum = eightAmPOPRainNum+109;
            int eightAmSKYWeatherNum = eightAmPOPRainNum-2;
            int fivePmSKYWeatherNum = eightAmPOPRainNum+107;
            int lowTempTMNNum = eightAmPOPRainNum-20;
            int highTempTMXNum = eightAmPOPRainNum+89;

            // WeatherShortMiddleResDTO로 만들기 위한 작업
            // 위에서 찾은 index 값으로 데이터를 가져온다
            LocalDate date = LocalDate.now().plusDays(i-1);
            String rainAm = getFcstValueByJSONArrayIndex(shortJSONArray, eightAmPOPRainNum);
            String rainPm = getFcstValueByJSONArrayIndex(shortJSONArray, fivePmPOPRainNum);

            String weatherAmBeforeConvert = getFcstValueByJSONArrayIndex(shortJSONArray, eightAmSKYWeatherNum);
            String weatherAm = convertWeatherToStringByWeatherNum(weatherAmBeforeConvert);
            String weatherPmBeforeConvert = getFcstValueByJSONArrayIndex(shortJSONArray, fivePmSKYWeatherNum);
            String weatherPm = convertWeatherToStringByWeatherNum(weatherPmBeforeConvert);

            String lowTemp = getFcstValueByJSONArrayIndex(shortJSONArray, lowTempTMNNum);
            String highTemp = getFcstValueByJSONArrayIndex(shortJSONArray, highTempTMXNum);

            // WeatherShortMiddleResDTO 생성
            WeatherShortMiddleResDTO shortResDTO = WeatherShortMiddleResDTO.builder()
                    .localDate(date).rainProbabilityAm(rainAm)
                    .rainProbabilityPm(rainPm).weatherAm(weatherAm)
                    .weatherPm(weatherPm).lowTemp(lowTemp).highTemp(highTemp).build();

            // List<WeatherShortMiddleResDTO> shortResDTOs에 WeatherShortMiddleResDTO를 추가하고
            shortResDTOs.add(shortResDTO);
        }

        // 그 List (List<WeatherShortMiddleResDTO> shortResDTOs) 를 반환
        return shortResDTOs;
    }

    public JSONArray selectShortWeatherJSON (String mapX, String mapY, int pageNo) throws IOException, ParseException {

        String yesterday = LocalDate.now().minusDays(1).toString().replace("-", "");

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + "51%2Bc6%2BNBpG7JFhyeZbKn4BDMVtvgE6W15oUad4G2n74%2Bv7Bo4oHKoQL%2FwLSaBnD67u%2F5CorapB5I6WMBLpXEkg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNo), "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("294", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(yesterday, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("2300", "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(Math.round(Float.parseFloat(mapY))), "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(Math.round(Float.parseFloat(mapX))), "UTF-8")); /*예보지점의 Y 좌표값*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();

        // result는 api로부터 받아온 값
        String result = sb.toString();

        JSONParser parser = new JSONParser(); // Json parser를 만들어 만들어진 문자열 데이터를 객체화
        JSONObject obj = (JSONObject) parser.parse(result);

        JSONObject parse_response = (JSONObject) obj.get("response"); // response 키를 가지고 데이터를 파싱
        JSONObject parse_body = (JSONObject) parse_response.get("body"); // response 로 부터 body 찾기
        JSONObject parse_items = (JSONObject) parse_body.get("items"); // body 로 부터 items 찾기
        JSONArray parse_item = (JSONArray) parse_items.get("item"); // items로 부터 itemlist 를 받기
        return parse_item;
    }

    public String getFcstValueByJSONArrayIndex (JSONArray jsonArray, int jsonIndex) {
        JSONObject jsonObject = (JSONObject)jsonArray.get(jsonIndex);
        return String.valueOf(jsonObject.get("fcstValue"));
    }

    public String convertWeatherToStringByWeatherNum (String stringWeatherNum) {
        double weatherNum = Double.parseDouble(stringWeatherNum);
        String weatherString = "";

        if ( weatherNum < 6 ) {
            weatherString = "맑음";
        } else if ( weatherNum < 9 ) {
            weatherString = "구름많음";
        } else if (weatherNum < 11) {
            weatherString = "흐림";
        } else {
            weatherString = "?";
        }

        return weatherString;
    }


    public List<WeatherShortMiddleResDTO> selectMiddleWeather(String mapX, String mapY) throws IOException, ParseException {
        List<WeatherShortMiddleResDTO> resDtos = new ArrayList<>();

        LocalDateTime date = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        if (date.getHour() < 18) {
            localDate = LocalDate.now().minusDays(1);
        }

        JSONObject keyObj = selectWeatherDetail(mapX, mapY);
        JSONObject keyObjTemp = selectTemp(mapX, mapY);

        for (int i = 3; i < 8; i++) {
            LocalDate dateList = localDate.plusDays(i);
            String rainAm = String.valueOf(keyObj.get("rnSt" + i + "Am"));
            String rainPm = String.valueOf(keyObj.get("rnSt" + i + "Pm"));
            String weatherAm = String.valueOf(keyObj.get("wf" + i + "Am"));
            String weatherPm = String.valueOf(keyObj.get("wf" + i + "Pm"));
            String lowTemp = String.valueOf(keyObjTemp.get("taMin" + i));
            String highTemp = String.valueOf(keyObjTemp.get("taMax" + i));

            WeatherShortMiddleResDTO dto = WeatherShortMiddleResDTO.builder()
                    .localDate(dateList).rainProbabilityAm(rainAm)
                    .rainProbabilityPm(rainPm).weatherAm(weatherAm)
                    .weatherPm(weatherPm).lowTemp(lowTemp).highTemp(highTemp).build();

            resDtos.add(dto);
        }
        return resDtos;
    }

    public List<WeatherLastResDTO> selectLastWeather(String mapX, String mapY) throws IOException, ParseException {
        List<WeatherLastResDTO> resDtos = new ArrayList<>();

        LocalDateTime date = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        if (date.getHour() < 18) {
            localDate = LocalDate.now().minusDays(1);
        }

        JSONObject keyObj = selectWeatherDetail(mapX, mapY);
        JSONObject keyObjTemp = selectTemp(mapX, mapY);

        for (int i = 8; i < 11; i++) {
            LocalDate dateList = localDate.plusDays(i);
            String rain = String.valueOf(keyObj.get("rnSt" + i));
            String weather = String.valueOf(keyObj.get("wf" + i));
            String lowTemp = String.valueOf(keyObjTemp.get("taMin" + i));
            String highTemp = String.valueOf(keyObjTemp.get("taMax" + i));

            WeatherLastResDTO dto = WeatherLastResDTO.builder()
                    .localDate(dateList).rainProbability(rain)
                    .weather(weather).lowTemp(lowTemp).highTemp(highTemp).build();

            resDtos.add(dto);
        }
        return resDtos;
    }

    public JSONObject selectWeatherDetail(String mapX, String mapY) throws IOException, ParseException {
        LocalDateTime date = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        if (date.getHour() < 18) {
            localDate = LocalDate.now().minusDays(1);
        }

        String locationCode = findLocation(mapX, mapY);
        String now = localDate.toString().replace("-", "");

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + "51%2Bc6%2BNBpG7JFhyeZbKn4BDMVtvgE6W15oUad4G2n74%2Bv7Bo4oHKoQL%2FwLSaBnD67u%2F5CorapB5I6WMBLpXEkg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("tmFc", "UTF-8") + "=" + URLEncoder.encode(now + "1800", "UTF-8")); /*날짜*/
        urlBuilder.append("&" + URLEncoder.encode("regId", "UTF-8") + "=" + URLEncoder.encode(locationCode, "UTF-8")); /*지역*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();

        String result = sb.toString();

        JSONParser parser = new JSONParser(); // Json parser를 만들어 만들어진 문자열 데이터를 객체화
        JSONObject obj = (JSONObject) parser.parse(result);
        JSONObject parse_response = (JSONObject) obj.get("response"); // response 키를 가지고 데이터를 파싱
        JSONObject parse_body = (JSONObject) parse_response.get("body"); // response 로 부터 body 찾기
        JSONObject parse_items = (JSONObject) parse_body.get("items"); // body 로 부터 items 찾기
        JSONArray parse_item = (JSONArray) parse_items.get("item"); // items로 부터 itemlist 를 받기
        return (JSONObject) parse_item.get(0);
    }

    public JSONObject selectTemp (String mapX, String mapY) throws IOException, ParseException {
        LocalDateTime date = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        if (date.getHour() < 18) {
            localDate = LocalDate.now().minusDays(1);
        }

        String locationCode = findLocation(mapX, mapY);
        String now = localDate.toString().replace("-", "");

        String locationTemp = findLocationForTemperature(locationCode);

        StringBuilder urlBuilderTemp = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa"); /*URL*/
        urlBuilderTemp.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + "51%2Bc6%2BNBpG7JFhyeZbKn4BDMVtvgE6W15oUad4G2n74%2Bv7Bo4oHKoQL%2FwLSaBnD67u%2F5CorapB5I6WMBLpXEkg%3D%3D"); /*Service Key*/
        urlBuilderTemp.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        urlBuilderTemp.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilderTemp.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilderTemp.append("&" + URLEncoder.encode("tmFc", "UTF-8") + "=" + URLEncoder.encode(now + "1800", "UTF-8")); /*날짜*/
        urlBuilderTemp.append("&" + URLEncoder.encode("regId", "UTF-8") + "=" + URLEncoder.encode(locationTemp, "UTF-8")); /*지역*/

        URL urlTemp = new URL(urlBuilderTemp.toString());
        HttpURLConnection connTemp = (HttpURLConnection) urlTemp.openConnection();
        connTemp.setRequestMethod("GET");
        connTemp.setRequestProperty("Content-type", "application/json");
        BufferedReader rdTemp;

        if (connTemp.getResponseCode() >= 200 && connTemp.getResponseCode() <= 300) {
            rdTemp = new BufferedReader(new InputStreamReader(connTemp.getInputStream()));
        } else {
            rdTemp = new BufferedReader(new InputStreamReader(connTemp.getErrorStream()));
        }

        StringBuilder sbTemp = new StringBuilder();
        String lineTemp;
        while ((lineTemp = rdTemp.readLine()) != null) {
            sbTemp.append(lineTemp);
        }

        rdTemp.close();
        connTemp.disconnect();

        String resultTemp = sbTemp.toString();

        JSONParser parserTemp = new JSONParser(); // Json parser를 만들어 만들어진 문자열 데이터를 객체화
        JSONObject objTemp = (JSONObject) parserTemp.parse(resultTemp);
        JSONObject parse_responseTemp = (JSONObject) objTemp.get("response"); // response 키를 가지고 데이터를 파싱
        JSONObject parse_bodyTemp = (JSONObject) parse_responseTemp.get("body"); // response 로 부터 body 찾기
        JSONObject parse_itemsTemp = (JSONObject) parse_bodyTemp.get("items"); // body 로 부터 items 찾기
        JSONArray parse_itemTemp = (JSONArray) parse_itemsTemp.get("item"); // items로 부터 itemlist 를 받기
        return (JSONObject) parse_itemTemp.get(0);
    }

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

    public String findLocationForTemperature(String code) {
        if (code.equals("11B00000")) {
            return "11B10101";
        } else if (code.equals("11D10000")) {
            return "11D10502";
        } else if (code.equals("11D20000")) {
            return "11D20501";
        } else if (code.equals("11C20000")) {
            return "11C20401";
        } else if (code.equals("11C10000")) {
            return "11C10301";
        } else if (code.equals("11F20000")) {
            return "11F20501";
        } else if (code.equals("11F10000")) {
            return "11F10201";
        } else if (code.equals("11H10000")) {
            return "11H10701";
        } else if (code.equals("11H20000")) {
            return "11H20201";
        } else if (code.equals("11G00000")) {
            return "11G00201";
        }
        return null;
    }
}
