package com.tododev.deserializer.jackson.interfaces.subtypename;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(name="secure", value=FileListSecure.class),
        @JsonSubTypes.Type(name="insecure", value=FileListInsecure.class)})
public interface FileList {
    String list();
}
