package com.sonnees.quotingservice.dto;

import com.sonnees.quotingservice.entity.Category;
import com.sonnees.quotingservice.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuotingDTO {
    private String idUser;
    private Category category;
    private Long priceOrigin;
    private Date dateBy;
    private Status status;
}
