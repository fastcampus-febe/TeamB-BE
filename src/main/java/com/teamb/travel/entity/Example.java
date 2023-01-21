//package com.teamb.travel.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Builder
//@Entity
//@Table(name = "example") // DB Table과 연결
//public class Example {
//
//    // JPA 에서는 PK가 필수적
//    @Id
//    @Column
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = true, length = 20)
//    private String name;
//
//    @Column(nullable = false, length = 20)
//    private int price;
//}
