package com.nowadays.common.processor;

import com.nowadays.common.messages.Message;

public interface MessageProcessor<T extends Message> {
    void process(String jsonMessage);
}
