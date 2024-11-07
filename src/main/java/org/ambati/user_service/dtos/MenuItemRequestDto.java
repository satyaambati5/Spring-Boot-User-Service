package org.ambati.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuItemRequestDto {
    private Long id;
    private String name;
    private String description;
    private String category;
    private double price;


}