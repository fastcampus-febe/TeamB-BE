- 관광지 추천 서비스 (프로젝트 기획, 개발, 리팩토링, 배포)
- **관광지 검색(Pagination), 관광지 날씨 조회, 관광지 상세정보, 리뷰** 기능 개발
- REST API로 개발 (BackEnd-FrontEnd 협업 프로젝트, View는 FrontEnd에서 개발)
- Open API 사용 : 날씨 api, 관광지 api
- API 명세서 : https://documenter.getpostman.com/view/24197140/2s8ZDcyKWn
- JDK 11, Spring Boot 2.7.8, Gradle
- MariaDB, Lombok, Spring Data JPA
- git-flow 전략 사용
- Tools : IntelliJ, DBeaver, Git, Github, GitKraken,
    Postman(Mock Server, API Docs), Swagger, Amazon EC2 (배포),
    Notion, Google Sheets, Slack, ErdCloud
- 이외 자세한 내용은 코드 및 document 폴더 참고 부탁드립니다

[ 배포 ]
- Swagger https://3.34.61.197/swagger-ui.html#
- hashtag 검색 예시
https://3.34.61.197/tourlist/hashtag?pageno=1&hashtag=%ED%8F%AD%ED%8F%AC&hashtag=%EC%95%BD%EC%88%98%ED%84%B0
- 지역 검색 예시
https://3.34.61.197/tourlist/location?pageno=1&areacode=39
