package com.nowadays.office.job;

import com.nowadays.common.bean.AirPort;
import com.nowadays.common.bean.Board;
import com.nowadays.common.bean.Route;
import com.nowadays.common.bean.RoutePath;
import com.nowadays.common.messages.AirPortStateMessage;
import com.nowadays.common.messages.OfficeRouteMessage;
import com.nowadays.common.processor.MessageConverter;
import com.nowadays.office.provider.AirPortsProvider;
import com.nowadays.office.provider.BoardsProvider;
import com.nowadays.office.service.PathService;
import com.nowadays.office.service.WaitingRoutesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class RouteDistribyteJob {
    private final PathService pathService;
    private final BoardsProvider boardsProvider;
    private final WaitingRoutesService waitingRoutesService;
    private final KafkaTemplate<String , String> kafkaTemplate;
    private final MessageConverter messageConverter;
    private final AirPortsProvider airPortsProvider;

    @Scheduled(initialDelay = 500 , fixedDelay = 2500)
    private void distribute(){
        waitingRoutesService.list().stream().filter(Route::notAssigned).forEach(route -> {
            String startLocation = route.getPath().get(0).getFrom().getName();
            boardsProvider.getBoards().stream().filter(board -> startLocation.equals(board.getLocation()) && board.noBusy()).findFirst().ifPresent(board -> sendBoardTooRoute(route , board));

            if(route.notAssigned()){
                boardsProvider.getBoards().stream().filter(Board::noBusy).findFirst().ifPresent(board -> {
                    String currentLocation = board.getLocation();
                    if(!currentLocation.equals(startLocation)){
                        RoutePath routePath = pathService.makePath(currentLocation , startLocation);
                        route.getPath().add(0 , routePath);
                    }
                    sendBoardTooRoute(route , board);
                });
            }
        });
    }

    private void sendBoardTooRoute(Route route , Board board){
        route.setBoardName(board.getName());
        AirPort airPort = airPortsProvider.findAirPortAndRemoveBoard(board.getName());
        board.setLocation(null);
        kafkaTemplate.sendDefault(messageConverter.toJson(new OfficeRouteMessage(route)));
        kafkaTemplate.sendDefault(messageConverter.toJson(new AirPortStateMessage(airPort)));
    }
}
