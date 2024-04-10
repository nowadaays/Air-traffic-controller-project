package com.nowadays.ship.listener.processor;

import com.nowadays.common.messages.BoardStateMessage;
import com.nowadays.common.processor.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("BOARD_STATE")
public class BoardStateProcessor implements MessageProcessor<BoardStateMessage> {
    @Override
    public void process(String jsonMessage) {
    }
}
