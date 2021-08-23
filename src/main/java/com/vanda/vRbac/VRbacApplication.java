package com.vanda.vRbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.servlet.annotation.WebListener;


@SpringBootApplication
@ServletComponentScan
public class VRbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(VRbacApplication.class, args);
    }

}
