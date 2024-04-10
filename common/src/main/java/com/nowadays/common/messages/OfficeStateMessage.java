package com.nowadays.common.messages;

import com.nowadays.common.bean.Source;
import com.nowadays.common.bean.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeStateMessage extends Message{
    public OfficeStateMessage(){
        this.source = Source.OFFICE;
        this.type = Type.STATE;
    }
}
