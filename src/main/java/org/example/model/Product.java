package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class Product {

   private Long id;
   private String productName;
   private String productTyp;
   private BigDecimal cost;
   private String description;
   private String image;


}
