package com.nowadays.common.messages;

import com.nowadays.common.bean.AirPort;
import com.nowadays.common.bean.Source;
import com.nowadays.common.bean.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirPortStateMessage extends Message{
    private AirPort airPort;

    public AirPortStateMessage(){
        this.source = Source.AIRPORT;
        this.type = Type.STATE;
    }

    public AirPortStateMessage(AirPort airPort){
        this();
        this.airPort = airPort;
    }
}
