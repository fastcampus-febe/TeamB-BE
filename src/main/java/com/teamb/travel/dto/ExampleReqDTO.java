//package com.teamb.travel.dto;
//
//import com.teamb.travel.entity.Example;
//import lombok.*;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
//public class ExampleReqDTO {
//
//    private Long id;
//    private String name;
//    private int price;
//
//    public ExampleReqDTO(String name, int price) {
//        this.name = name;
//        this.price = price;
//    }
//
//    public Example toEntity() {
//        return Example.builder()
//                .id(this.id)
//                .name(this.name)
//                .price(this.price)
//                .build();
//    }
//}
