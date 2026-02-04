package com.nithingodugu.demo;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.nio.file.PathMatcher;

@SpringBootApplication
public class App implements CommandLineRunner {
    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
//        Vehicle obj = (Vehicle) context.getBean("vehicleType");
//        obj.drive();

        SpringApplication.run(App.class, args);
    }

    private final PaymentService ps;

    App(PaymentService ps){
        this.ps = ps;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running ");
        System.out.println(ps.pay());
    }
}
