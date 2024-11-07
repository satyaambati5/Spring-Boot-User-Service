package org.ambati.user_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuItem {
    private Long id;
    private String name;
    private String description;
    private String category;
    private double price;


}