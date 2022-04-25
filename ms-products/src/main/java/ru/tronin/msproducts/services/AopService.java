package ru.tronin.msproducts.services;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Aspect
@Slf4j
public class AopService {

    //Название метода в стиле Spring
    @After("execution(public * ru.tronin.msproducts.controllers.ProductsController.showPaginatedProducts(..))")
    public void afterShowingProductsDisplayRequestParameters(JoinPoint joinPoint){
        List<Object> args = Arrays.stream(joinPoint.getArgs()).toList();
        if (args.size() == 0){
            log.info("Method doesn't have arguments");
        }
        log.info("Arguments of method Index:");
        args.forEach(arg -> {
            if (arg instanceof Pageable argument){
                log.info("Pageable argument:" + argument);
            }
            if (arg instanceof Double argument){
                log.info("Long argument:" + argument);
            }
            if (arg instanceof String argument){
                log.info("String argument:" + argument);
            }
        });

    }
}
