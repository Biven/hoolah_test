package com.hoolah.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Yauheni Zubovich
 */
@Data
@AllArgsConstructor
public class Report {

    private int transactionsCount;
    private double averageValue;
}
