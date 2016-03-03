package com.example

import jdk.nashorn.internal.ir.annotations.Immutable
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.web.bind.annotation.RequestMethod.GET

@SpringBootApplication @RestController
class ShellDemoApplication {

    Message message = new Message(timestamp: new Date(), text: 'Hi there')

    @RequestMapping(value = '/message', method = GET)
    Message message() {
        if( !message.timeToLive || System.currentTimeMillis() - message.timestamp.time <= message.timeToLive){
            return message
        }
        return new Message(timestamp: new Date(), text: 'No message')
    }

    static void main(String[] args) {
        SpringApplication.run ShellDemoApplication, args
    }
}

@Immutable
class Message {
    Date timestamp
    String text
    long timeToLive

    @Override
    String toString() {
        "[$timestamp] $text"
    }
}
