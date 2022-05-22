package com.owntech.controller;

import com.owntech.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/api/kafka")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @RequestMapping(value = "/sample/{amount}", method = RequestMethod.GET)
    public void generateMessages(@PathVariable("amount") Integer amount) {
        producerService.sendMessage(amount);
    }
}
