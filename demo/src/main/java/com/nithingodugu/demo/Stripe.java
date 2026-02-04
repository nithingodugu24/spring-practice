package com.nithingodugu.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "payment.provider", havingValue = "stripe")
public class Stripe implements PaymentService{
    @Override
    public String pay() {
        String payment = "Stripe payment gateway";
        System.out.println(payment);
        return "STRIPE: Payment Done";
    }
}
