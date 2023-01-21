//package com.teamb.travel.service;
//
//import com.teamb.travel.dto.ExampleReqDTO;
//import com.teamb.travel.dto.ExampleResDTO;
//import com.teamb.travel.entity.Example;
//import com.teamb.travel.repository.ExampleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class ExampleService {
//
//    @Autowired
//    ExampleRepository repo;
//
//    public ExampleResDTO merge(ExampleReqDTO req) {
//        Example ex = req.toEntity();
//        ex = repo.save(ex); // INSERT Query
//
//        return new ExampleResDTO(ex.getName());
//    }
//
//    public List<ExampleResDTO> selectAll() {
//        List<Example> result = repo.findAll();
//
//        return result.stream()
//                .map(example -> new ExampleResDTO(example.getName()))
//                .collect(Collectors.toList());
//    }
//
//    public ExampleResDTO selectOne(ExampleReqDTO req) {
//        Example res = repo.findById(req.getId()).orElse(null); // PK로 검색
//        return new ExampleResDTO(res.getName());
//    }
//
//    public void deleteOne(ExampleReqDTO req) {
//        repo.deleteById(req.getId());
//    }
//
//    public void deleteAll() {
//        repo.deleteAll();
//    }
//
//    public List<ExampleResDTO> selectByName(ExampleReqDTO dto) {
//        List<Example> result = repo.findByName(dto.getName());
//
//        return result.stream()
//                .map(example -> new ExampleResDTO(example.getName()))
//                .collect(Collectors.toList());
//    }
//
//    public List<ExampleResDTO> selectByNameStartingWith(ExampleReqDTO dto) {
//        List<Example> result = repo.findByNameStartingWith(dto.getName());
//
//        return result.stream()
//                .map(example -> new ExampleResDTO(example.getName()))
//                .collect(Collectors.toList());
//    }
//
//    public List<ExampleResDTO> selectByPriceLessThan(ExampleReqDTO dto) {
//        List<Example> result = repo.findByPriceLessThan(dto.getPrice());
//
//        return result.stream()
//                .map(example -> new ExampleResDTO(example.getName()))
//                .collect(Collectors.toList());
//    }
//
//    public List<ExampleResDTO> selectByNameAndPrice(ExampleReqDTO dto) {
//        List<Example> result = repo.findByNameAndPrice(dto.getName(), dto.getPrice());
//
//        return result.stream()
//                .map(example -> new ExampleResDTO(example.getName()))
//                .collect(Collectors.toList());
//    }
//
//}
//
