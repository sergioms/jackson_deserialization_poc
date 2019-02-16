package com.tododev.deserializer.jackson.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileList {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected List<String> files;

    protected AbstractFileList() {
        files = new ArrayList<>();
    }

    @SuppressWarnings("unused")
    public List<String> getFiles() {
        return files;
    }

    @SuppressWarnings("unused")
    public void setFiles(List<String> files) {
        this.files = files;
    }
}
