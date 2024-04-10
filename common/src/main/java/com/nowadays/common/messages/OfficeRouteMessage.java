package com.nowadays.common.messages;

import com.nowadays.common.bean.Route;
import com.nowadays.common.bean.Source;
import com.nowadays.common.bean.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeRouteMessage extends Message{
    private Route route;

    public OfficeRouteMessage(){
        this.source = Source.OFFICE;
        this.type = Type.ROUTE;
    }

    public OfficeRouteMessage(Route val){
        this();
        this.route = val;
    }
}
