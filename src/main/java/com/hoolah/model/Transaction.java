package com.hoolah.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.util.Date;

/**
 * @author Yauheni Zubovich
 */
@Data
@Wither
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private String id;
    private Date date;
    private float amount;
    private String merchant;
    private TransactionType type;
    private String relatedTransaction;
}
