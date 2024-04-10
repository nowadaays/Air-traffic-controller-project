package com.nowadays.office.listner.processors;

import com.nowadays.common.bean.AirPort;
import com.nowadays.common.bean.Board;
import com.nowadays.common.bean.Route;
import com.nowadays.common.bean.RoutePoint;
import com.nowadays.common.messages.AirPortStateMessage;
import com.nowadays.common.messages.BoardStateMessage;
import com.nowadays.common.processor.MessageConverter;
import com.nowadays.common.processor.MessageProcessor;
import com.nowadays.office.provider.AirPortsProvider;
import com.nowadays.office.provider.BoardsProvider;
import com.nowadays.office.service.WaitingRoutesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("BOARD_STATE")
@RequiredArgsConstructor
public class BoardStateProcessor implements MessageProcessor<BoardStateMessage> {
    private final MessageConverter messageConverter;
    private final WaitingRoutesService waitingRoutesService;
    private final BoardsProvider boardsProvider;
    private final AirPortsProvider airPortsProvider;
    private final KafkaTemplate<String , String> kafkaTemplate;
    @Override
    public void process(String jsonMessage) {
        BoardStateMessage message = messageConverter.extractMessage(jsonMessage , BoardStateMessage.class);
        Board board = message.getBoard();
        Optional<Board> previousOpt = boardsProvider.getBoard(board.getName());
        AirPort airPort = airPortsProvider.getAirPort(board.getLocation());

        boardsProvider.addBoard(board);
        if(previousOpt.isPresent() && board.hasRoute() && !previousOpt.get().hasRoute()){
            Route route = board.getRoute();
            waitingRoutesService.remove(route);
        }

        if(previousOpt.isEmpty() || !board.isBusy() && previousOpt.get().isBusy()){
            airPort.addBoard(board.getName());
            kafkaTemplate.sendDefault(messageConverter.toJson(new AirPortStateMessage(airPort)));
        }
    }
}
