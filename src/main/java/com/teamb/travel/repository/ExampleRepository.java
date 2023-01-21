//package com.teamb.travel.repository;
//
//import com.teamb.travel.entity.Example;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//                                                // < 제어할 Entity / PK 타입 >
//public interface ExampleRepository extends JpaRepository<Example, Long> {
//
//    public List<Example> findByName(String name); // WHERE 절 완성.. !   // where name = ?
//    public List<Example> findByNameStartingWith(String name); // where name like 'x%'
//    public List<Example> findByPriceLessThan(int price); // where price < x
//    public List<Example> findByNameAndPrice(String name, int price); // where name = ? and price = ?
//}
