package com.nowadays.office.listner.processors;

import com.nowadays.common.messages.AirPortStateMessage;
import com.nowadays.common.messages.OfficeStateMessage;
import com.nowadays.common.processor.MessageConverter;
import com.nowadays.common.processor.MessageProcessor;
import com.nowadays.office.provider.AirPortsProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("OFFICE_STATE")
@RequiredArgsConstructor
public class OfficeStateProcessor implements MessageProcessor<OfficeStateMessage> {
    private final MessageConverter messageConverter;
    private final AirPortsProvider airPortsProvider;
    private final KafkaTemplate<String , String> kafkaTemplate;
    @Override
    public void process(String jsonMessage) {
        airPortsProvider.getPorts().forEach(airPort -> {
            kafkaTemplate.sendDefault(messageConverter.toJson(new AirPortStateMessage(airPort)));
        });
    }
}
