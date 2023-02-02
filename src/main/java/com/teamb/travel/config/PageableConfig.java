//package com.teamb.travel.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.data.web.SortHandlerMethodArgumentResolver;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        SortHandlerMethodArgumentResolver sortArgumentResolver = new SortHandlerMethodArgumentResolver();
//
////        sort 요청 시 요청 파라미터를 수정할 수 있습니다. Default는 sort이며 예제에서는 sortBy로 수정
////        sortArgumentResolver.setSortParameter("sortBy");
//
////        정렬조건 값을 전달할 때 정렬조건필드 property와 정렬기준 property를 구분하는 구분자를 설정할 수 있습니다. Default는 ",' 로 구분하며, 예제에서는 "-"로 수정
////        sortArgumentResolver.setPropertyDelimiter("-");
//
//
//        PageableHandlerMethodArgumentResolver pageableArgumentResolver = new PageableHandlerMethodArgumentResolver(sortArgumentResolver);
//
////        page 기본값을 1로 설정하게 합니다. Pageable 파라미터 중 page는 페이지 번호를 뜻하는데,
////        1페이지가 0으로 인식됩니다. 그래서 화면에서 쓰이는 페이지 번호랑 상이한 케이스가 있어
////        이 값을 true로 설정하면 화면에서 쓰이는 페이지 번호와 동일하게 1페이지부터 시작할 수 있도록 합니다.
//        pageableArgumentResolver.setOneIndexedParameters(true);
//
////        Paging 요청에 대해 한 번에 많은 갯수를 요청하는 경우를 대비하여, 최대 요청 가능한 size를 설정
////        pageableArgumentResolver.setMaxPageSize(500);
//
////        페이지 요청이 없는 경우 기본적으로 요청되는 페이징 정보를 설정
//        pageableArgumentResolver.setFallbackPageable(PageRequest.of(0,12));
//
//        pageableArgumentResolver.setPageParameterName("pageno");
//
//        argumentResolvers.add(pageableArgumentResolver);
//
//    }
//}
