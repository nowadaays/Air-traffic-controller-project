package com.nowadays.common.messages;

import com.nowadays.common.bean.Source;
import com.nowadays.common.bean.Type;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Message {
    protected Type type;
    protected Source source;

    public String getCode(){
        return source.name() + "_" + type.name();
    }
}
