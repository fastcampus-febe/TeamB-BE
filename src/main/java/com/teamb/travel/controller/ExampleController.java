//package com.teamb.travel.controller;
//
//import com.teamb.travel.dto.ExampleReqDTO;
//import com.teamb.travel.dto.ExampleResDTO;
//import com.teamb.travel.service.ExampleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class ExampleController {
//
//    @Autowired
//    ExampleService es;
//
//    @GetMapping("/insert")
//    public ExampleResDTO mergeExample() {
//
//        ExampleReqDTO dto = new ExampleReqDTO("fish", 1800);
//        return es.merge(dto);
//    }
//
//    @GetMapping("/selectAll")
//    public List<ExampleResDTO> selectAll() {
//        return es.selectAll();
//    }
//
//    @GetMapping("/selectOne")
//    public ExampleResDTO selectOne() {
//
//        return es.selectOne(new ExampleReqDTO(1L, "fish", 1600));
//    }
//
//    @GetMapping("/deleteOne")
//    public void deleteOne() {
//        es.deleteOne(new ExampleReqDTO(1L, "fish", 1600));
//    }
//
//    @GetMapping("/deleteAll")
//    public void deleteAll() {
//        es.deleteAll();
//    }
//
//    @GetMapping("/selectByName")
//    public List<ExampleResDTO> selectByName() {
//
//        ExampleReqDTO dto = new ExampleReqDTO(0L, "fishhh", 1800);
//        return es.selectByName(dto);
//    }
//
//    @GetMapping("/selectByNameStartingWith")
//    public List<ExampleResDTO> selectByNameStartingWith() {
//
//        ExampleReqDTO dto = new ExampleReqDTO(0L, "fish", 1800);
//        return es.selectByNameStartingWith(dto);
//    }
//
//    @GetMapping("/selectByPriceLessThan")
//    public List<ExampleResDTO> selectByPriceLessThan() {
//
//        ExampleReqDTO dto = new ExampleReqDTO(0L, "fish", 1800);
//        return es.selectByPriceLessThan(dto);
//    }
//
//    @GetMapping("/selectByNameAndPrice")
//    public List<ExampleResDTO> selectByNameAndPrice() {
//
//        ExampleReqDTO dto = new ExampleReqDTO(0L, "fish", 1800);
//        return es.selectByNameAndPrice(dto);
//    }
//}
