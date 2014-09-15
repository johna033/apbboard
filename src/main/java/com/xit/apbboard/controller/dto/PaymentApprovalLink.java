package com.xit.apbboard.controller.dto;

import java.io.Serializable;

/**
 * Created by
 *
 * @author homer
 * @since 15.09.14.
 */
public class PaymentApprovalLink implements Serializable {
    public String link;

    public PaymentApprovalLink(String link){ this.link = link;}
}
