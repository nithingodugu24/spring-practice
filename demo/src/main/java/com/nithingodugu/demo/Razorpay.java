package com.nithingodugu.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "payment.provider", havingValue = "razorpay")
public class Razorpay implements PaymentService{

    @Override
    public String pay() {
        String payment = "Razopay payment gateway";
        System.out.println(payment);
        return "RAZORPAY: Payment Done";
    }
}
