package com.nowadays.ship.listener.processor;

import com.nowadays.common.bean.Route;
import com.nowadays.common.messages.OfficeRouteMessage;
import com.nowadays.common.processor.MessageConverter;
import com.nowadays.common.processor.MessageProcessor;
import com.nowadays.ship.provider.BoardProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("OFFICE_ROUTE")
@Slf4j
@RequiredArgsConstructor
public class OfficeRouteProcessor implements MessageProcessor<OfficeRouteMessage> {

    private final MessageConverter messageConverter;
    private final BoardProvider boardProvider;
    @Override
    public void process(String jsonMessage) {
        OfficeRouteMessage msg = messageConverter.extractMessage(jsonMessage , OfficeRouteMessage.class);
        Route route = msg.getRoute();
        boardProvider.getBoards().stream().filter(board -> board.noBusy() && route.getBoardName().equals(board.getName())).findFirst().ifPresent(board -> {
            board.setRoute(route);
            board.setBusy(true);
            board.setLocation(null);
        });
    }
}
