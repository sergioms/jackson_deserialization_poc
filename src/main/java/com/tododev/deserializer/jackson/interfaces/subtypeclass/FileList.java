package com.tododev.deserializer.jackson.interfaces.subtypeclass;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "class")
public interface FileList {
    String list();
}
