package com.nowadays.office.controller;

import com.nowadays.common.bean.Route;
import com.nowadays.office.service.PathService;
import com.nowadays.office.service.WaitingRoutesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/routes")
public class RouteRest {
    private final WaitingRoutesService waitingRoutesService;
    private final PathService pathService;

    @PostMapping(path = "route")
    public void addRoute(@RequestBody List<String> routeLocations){
        Route route = pathService.convertLocationToRoute(routeLocations);
        waitingRoutesService.add(route);
    }
}
