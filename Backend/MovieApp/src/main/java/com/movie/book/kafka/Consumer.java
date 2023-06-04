//package com.movie.book.kafka;
//
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
////import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.Message;
//import org.springframework.stereotype.Component;
//
//
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class Consumer {
//	Logger log = LoggerFactory.getLogger(Consumer.class);
//	@KafkaListener(topics="${kafka.topic}")
//	public void listen(Message<String> message) {
//		String payload = message.getPayload();
//		System.out.println(payload);
//		log.info("consumes message "+payload);
//	}
//
//}
