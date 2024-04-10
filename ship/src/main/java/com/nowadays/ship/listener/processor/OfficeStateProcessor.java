package com.nowadays.ship.listener.processor;

import com.nowadays.common.messages.BoardStateMessage;
import com.nowadays.common.messages.OfficeStateMessage;
import com.nowadays.common.processor.MessageConverter;
import com.nowadays.common.processor.MessageProcessor;
import com.nowadays.ship.provider.BoardProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("OFFICE_STATE")
@Slf4j
@RequiredArgsConstructor
public class OfficeStateProcessor implements MessageProcessor<OfficeStateMessage> {
    private final MessageConverter messageConverter;
    private final BoardProvider boardProvider;
    private final KafkaTemplate<String , String> kafkaTemplate;

    @Override
    public void process(String jsonMessage) {
        boardProvider.getBoards().forEach(board -> {
            kafkaTemplate.sendDefault(messageConverter.toJson(new BoardStateMessage(board)));
        });
    }
}
