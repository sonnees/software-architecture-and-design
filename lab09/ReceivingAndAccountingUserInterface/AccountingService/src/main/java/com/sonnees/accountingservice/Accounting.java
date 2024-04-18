package com.sonnees.accountingservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("accounting")
public class Accounting {
    @Field(targetType = FieldType.STRING)
    private UUID id;

    private Date date;
    private double priceBy;

    public Accounting(AccountingDTO info) {
        this.id = info.getId();
        this.date = new Date();
        this.priceBy = info.getPriceBy();
    }
}
