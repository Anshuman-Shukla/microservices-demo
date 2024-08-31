package com.microservices.demo.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    private Integer id;
    private String pName;
    private String pCategory;
    private String quantity;
    private String quality;
    private String company;
    private  String pOrigin;
}
