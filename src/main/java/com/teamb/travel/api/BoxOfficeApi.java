//package com.teamb.travel.api;
//
//import com.teamb.travel.dto.DailyMovieDTO;
//import com.teamb.travel.dto.MovieDTO;
//import com.teamb.travel.service.MovieService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class BoxOfficeApi {
//    private MovieService ms;
//    private String key = "813f6367ffa2a50e00c46b305d629d4b";
//    private String dailyResponse;
//    private JSONParser jsonParser;
//    private Object obj;
//    private JSONObject jsonObject;
//    private JSONObject parse_boxOfficeResult;
//    private JSONObject parse_movieInfoResult;
//
//    @Autowired
//    public BoxOfficeApi(MovieService ms) {
//        this.ms = ms;
//    }
//
//    @PostConstruct
//    public void init() {
//        try {
//            // 해당 날짜 기준으로 insert 되었는지 확인함. check 시 null 이면 DAILY_BOXOFFICE, MOVIE_DETAIL 테이블에 today-1 날짜 기준으로 insert 된다.
//            if (ms.selectDateInsertChk() == null) {
//                dailyBoxOffice();  // DAILY_BOXOFFICE 테이블 update
//                movieDetail();     // MOVIE_DETAIL 테이블 update
//                movieImgSummaryFromWEB();   // MOVIE_DETAIL 테이블에 movie_img, summary update
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//
//    }
//
//    public void dailyBoxOffice() {
//
//        dailyResponse = "";
//
//        LocalDateTime time = LocalDateTime.now().minusDays(1); // 전날 박스오피스로 조회해야함.(최신) 당일 날짜로는 조회 불가능하기에.
//        String targetDt = time.format(DateTimeFormatter.ofPattern("yyyMMdd"));
//
//        String itemPerPage = "10"; //ROW 개수 (Default 가 10개임)
//
//        /*** 아래 세가지 파라미터 안던져주면 오류 발생함. */
//        String multiMovieYn = ""; //다양성영화(Y)/상업영화(N)/전체(default)
//        String repNationCd = ""; //한국영화(K)/외국영화(F)/전체(default)
//        String wideAreaCd = ""; //상영지역별 코드/전체(default)
//
//        try {
//            // KOBIS 오픈 API Rest Client를 통해 호출
//            KobisOpenAPIRestService service = new KobisOpenAPIRestService(key);
//            // 일별 박스오피스 API 서비스 호출 (boolean isJson, String targetDt, String itemPerPage,String multiMovieYn, String repNationCd, String wideAreaCd)
//            dailyResponse = service.getDailyBoxOffice(true, targetDt, itemPerPage, multiMovieYn, repNationCd, wideAreaCd);
//
//            jsonParser = new JSONParser(); //JSON Parser 객체 생성
//            obj = jsonParser.parse(dailyResponse); //Parser 로 문자열 데이터를 객체로 변환
//            jsonObject = (JSONObject) obj; //파싱한 obj 를 JSONObject 객체로 변환
//            parse_boxOfficeResult = (JSONObject) jsonObject.get("boxOfficeResult"); // parsing 하기
//
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            JSONArray parse_dailyBoxOfficeList = (JSONArray) parse_boxOfficeResult.get("dailyBoxOfficeList");   //boxOfficeResult -> dailyBoxOfficeList 꺼내기.
//            for (int i = 0; i < parse_dailyBoxOfficeList.size(); i++) {
//                JSONObject dailyBoxOffice = (JSONObject) parse_dailyBoxOfficeList.get(i);
//                //JSON object -> Java Object 로 변환
//                HashMap<String, Object> dailyResult = objectMapper.readValue(dailyBoxOffice.toString(), HashMap.class);
//
//                // DTO 로 맞춰주기. (추후 Map -> DTO 로 변환 가능하도록 DTO 내에서 작업 필요할 것으로 보임.)
//                DailyMovieDTO dailyMovieDTO = new DailyMovieDTO();
//                dailyMovieDTO.setMovieCd(Integer.parseInt(String.valueOf(dailyResult.get("movieCd"))));
//                dailyMovieDTO.setMovieNm(String.valueOf(dailyResult.get("movieNm")));
//                dailyMovieDTO.setOpenDt(LocalDate.parse(dailyResult.get("openDt").toString()));
//                dailyMovieDTO.setMovieRank(Integer.parseInt(String.valueOf(dailyResult.get("rank"))));
//                dailyMovieDTO.setAudiAcc(Long.parseLong(String.valueOf(dailyResult.get("audiAcc"))));
//
//                ms.insertDailyMovie(dailyMovieDTO); //DB 저장
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//    }
//
//    public void movieDetail() {
//
//        String detailResponse;
//
//        try {
//            // KOBIS 오픈 API Rest Client를 통해 호출
//            KobisOpenAPIRestService service = new KobisOpenAPIRestService(key);
//
//            ArrayList<HashMap<String, Object>> map = ms.selectDailyMovieCode();
//            for (int i = 0; i < map.size(); i++) {
//                // 영화 상세정보 서비스 호출 (boolean isJson, String movieCd)
//                detailResponse = service.getMovieInfo(true, map.get(i).get("movie_cd").toString());
//
//                JSONParser jsonParser = new JSONParser(); //JSON Parser 객체 생성
//
//                Object obj = jsonParser.parse(detailResponse); //Parser로 문자열 데이터를 객체로 변환
//
//                JSONObject jsonObject = (JSONObject) obj; //파싱한 obj를 JSONObject 객체로 변환
//
//                parse_movieInfoResult = (JSONObject) jsonObject.get("movieInfoResult"); // parsing하기
//                JSONObject parse_movieInfoResultList = (JSONObject) parse_movieInfoResult.get("movieInfo"); // movieInfoResult -> movieInfo 꺼내오기.
//
//                ObjectMapper objectMapper = new ObjectMapper();
//
//                HashMap<String, Object> detailResult = objectMapper.readValue(parse_movieInfoResultList.toString(), HashMap.class);
//
//                MovieDTO movieDTO = new MovieDTO();
//                movieDTO.setMovieCd(Integer.parseInt(String.valueOf(detailResult.get("movieCd"))));
//                movieDTO.setMovieNm(String.valueOf(detailResult.get("movieNm")));
//                movieDTO.setMovieNmEn(String.valueOf(detailResult.get("movieNmEn")));
//                //cmpMovieNm 추가
//                movieDTO.setCmpMovieNm(String.valueOf(detailResult.get("movieNmEn")).replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", ""));
//                movieDTO.setPrdtYear(String.valueOf(detailResult.get("prdtYear")));
//                movieDTO.setShowtime(Integer.parseInt(String.valueOf(detailResult.get("showTm"))));
//                movieDTO.setOpenDt(LocalDate.of(Integer.parseInt(detailResult.get("openDt").toString().substring(0, 4)), Integer.parseInt(detailResult.get("openDt").toString().substring(4, 6)), Integer.parseInt(detailResult.get("openDt").toString().substring(6, 8))));
//                movieDTO.setTypeNm(String.valueOf(detailResult.get("typeNm")));
//
//                ArrayList<HashMap<String, Object>> nations = (ArrayList<HashMap<String, Object>>) detailResult.get("nations");
//                movieDTO.setNationNm(String.valueOf(nations.get(0).get("nationNm")));
//
//                ArrayList<HashMap<String, Object>> genres = (ArrayList<HashMap<String, Object>>) detailResult.get("genres");
//                String genreNm = "";
//                for (int j = 0; j < genres.size(); j++) {
//                    genreNm += genres.get(j).get("genreNm") + "/";
//                }
//                movieDTO.setGenreNm(genreNm);
//
//                ArrayList<HashMap<String, Object>> directors = (ArrayList<HashMap<String, Object>>) detailResult.get("directors");
//
//                // 감독 정보가 null 인 경우도 있어서 추가함.
//                if (directors.size() == 0) {
//                    movieDTO.setDirectorNm("");
//                    movieDTO.setDirectorNmEn("");
//                } else {
//                    movieDTO.setDirectorNm(String.valueOf(directors.get(0).get("peopleNm")));
//                    movieDTO.setDirectorNmEn(String.valueOf(directors.get(0).get("peopleNmEn")));
//                }
//
//
//                ArrayList<HashMap<String, Object>> company = (ArrayList<HashMap<String, Object>>) detailResult.get("companys");
//                movieDTO.setCompanyNm(String.valueOf(company.get(0).get("companyNm")));
//
//                ArrayList<HashMap<String, Object>> audits = (ArrayList<HashMap<String, Object>>) detailResult.get("audits");
//                movieDTO.setWatchGradeNm(String.valueOf(audits.get(0).get("watchGradeNm")));
//
//                ArrayList<HashMap<String, Object>> actors = (ArrayList<HashMap<String, Object>>) detailResult.get("actors");
//                String actors_name = "";
//                String actors_name_en = "";
//                for (int j = 0; j < actors.size(); j++) {
//                    actors_name += actors.get(j).get("peopleNm") + "|";
//                    actors_name_en += actors.get(j).get("peopleNmEn") + "|";
//                }
//                movieDTO.setActorNm(actors_name);
//                movieDTO.setActorNmEn(actors_name_en);
//
//                ms.insertMovieDetail(movieDTO); //DB저장
//            }
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//    }
//
//    public void movieImgSummaryFromWEB() throws IOException, ParseException {
//        String serviceKey = "K15BMNT8V3D591V3VW76";
//        String HOST_URL = "";
//        String strListCnt = "1";
//
//        log.info(this.getClass().getName() + ".movieImgSummaryFromWEB start.");
//
//        ArrayList<MovieDTO> arrMovieDto = ms.selectMovieDtMovieNmDirNm(); // 저장되어야하는 영화제목 목록.
//        ArrayList<MovieDTO> rmvMovieDto = removeSymbols(arrMovieDto); // 특수기호 제거된 영화제목.
//
//        for (int i = 0; i < rmvMovieDto.size(); i++) {
//            StringBuilder urlBuilder = new StringBuilder("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2"); /*URL*/
//            urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode(serviceKey, "UTF-8")); /*Service Key*/
//            urlBuilder.append("&" + URLEncoder.encode("detail", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*상세보기*/
//            urlBuilder.append("&" + URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(rmvMovieDto.get(i).getMovieNm(), "UTF-8")); /*통합검색*/
//            if(!rmvMovieDto.get(i).getDirectorNm().equals("")) {
//                if(rmvMovieDto.get(i).getDirectorNm().length() > 6) {
//                    urlBuilder.append("&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(rmvMovieDto.get(i).getMovieNmEn(), "UTF-8")); /*영화 타이틀*/
//                } else {
//                    urlBuilder.append("&" + URLEncoder.encode("director", "UTF-8") + "=" + URLEncoder.encode(rmvMovieDto.get(i).getDirectorNm(), "UTF-8")); /*감독명*/
//                }
//            }
//            urlBuilder.append("&" + URLEncoder.encode("listCount", "UTF-8") + "=" + URLEncoder.encode(strListCnt, "UTF-8")); /*검색 결과 count*/
//            URL url = new URL(urlBuilder.toString());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Content-type", "application/json");
//            log.info("Response code: {}",conn.getResponseCode());
//            BufferedReader rd;
//            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            } else {
//                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//            }
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = rd.readLine()) != null) {
//                sb.append(line);
//            }
//            rd.close();
//            conn.disconnect();
//
//            JSONParser parser = new JSONParser();
//            JSONObject obj = (JSONObject) parser.parse(sb.toString());
//            JSONArray data = (JSONArray) obj.get("Data");
//            JSONObject objData = (JSONObject) data.get(0);
//            JSONArray result = (JSONArray) objData.get("Result");
//            JSONObject objResult = (JSONObject) result.get(0);
//
//            // 영화제목(영문)
//            String strMovieNmEn = String.valueOf(objResult.get("titleEng")).replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", "");
//
//            // 제작연도
//            String strProdYear = String.valueOf(objResult.get("prodYear"));
//
//            // 감독명
//            JSONObject objStaffs = (JSONObject) objResult.get("staffs");
//            JSONArray arrStaffs = (JSONArray) objStaffs.get("staff");
//            JSONObject objStaff = (JSONObject) arrStaffs.get(0);
//            String strStaffNm = String.valueOf(objStaff.get("staffNm"));
//
//            //줄거리
//            JSONObject objPlots = (JSONObject) objResult.get("plots");
//            JSONArray arrPlots = (JSONArray) objPlots.get("plot");
//            JSONObject objPlot = (JSONObject) arrPlots.get(0);
//            String strPlotText = String.valueOf(objPlot.get("plotText"));
//
//            //개봉일
//            String strRelDate = String.valueOf(objResult.get("repRlsDate"));
//
//            //포스터 url
//            String strPosterUrl = String.valueOf(objResult.get("posters"));
//
//            MovieDTO movieDTO = new MovieDTO();
//
//            movieDTO.setMovieImg(strPosterUrl);
//            movieDTO.setSummary(strPlotText);
//            movieDTO.setOpenDt(LocalDate.of(Integer.parseInt(strRelDate.substring(0, 4)), Integer.parseInt(strRelDate.substring(4, 6)), Integer.parseInt(strRelDate.substring(6, 8))));
//
//            if(rmvMovieDto.get(i).getDirectorNm().equals("")){
//                movieDTO.setDirectorNm("");
//            } else {
//                movieDTO.setDirectorNm(strStaffNm);
//            }
//            if(!strMovieNmEn.equals("")){
//                movieDTO.setCmpMovieNm(strMovieNmEn);
//            } else {
//                movieDTO.setCmpMovieNm("");
//            }
//            if(!rmvMovieDto.get(i).getPrdtYear().equals("")){
//                movieDTO.setPrdtYear(strProdYear);
//            } else {
//                movieDTO.setGenreNm("");
//            }
//            ms.updateMvDtImgAndSummary(movieDTO);
//        }
//    }
//
//
//    public ArrayList<MovieDTO> removeSymbols(ArrayList<MovieDTO> arrMovieDto) {
//        for (int i = 0; i < arrMovieDto.size(); i++) {
//            arrMovieDto.get(i).setMovieNm(arrMovieDto.get(i).getMovieNm().replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", ""));
//        }
//        return arrMovieDto;
//    }
//
//}
