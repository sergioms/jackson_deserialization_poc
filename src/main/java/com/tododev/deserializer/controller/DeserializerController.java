package com.tododev.deserializer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(consumes = "application/json", produces = "application/json", path = "/")
public class DeserializerController {
    private static final Logger log = LoggerFactory.getLogger(DeserializerController.class);

    @GetMapping
    public @ResponseBody
    String ping() {
        return "hi";
    }

    @PostMapping(path = "/bean/secure/files")
    public @ResponseBody
    String createListing(@RequestBody com.tododev.deserializer.jackson.bean.FileListSecure filelist) {
        return filelist.list();
    }

    @PostMapping(path = "/bean/insecure/files")
    public @ResponseBody
    String createListing(@RequestBody com.tododev.deserializer.jackson.bean.FileListInsecure filelist) {
        return filelist.list();
    }

    @PostMapping(path = "/bean/files")
    public @ResponseBody
    String createListing(@RequestBody com.tododev.deserializer.jackson.bean.FileList filelist) {
        return filelist.list();
    }

    @PostMapping(path = "/class/files")
    public String createListing(@RequestBody com.tododev.deserializer.jackson.interfaces.subtypeclass.FileList filelist) {
        return filelist.list();
    }

    @PostMapping(path = "/name/files")
    public String createListing(@RequestBody com.tododev.deserializer.jackson.interfaces.subtypename.FileList filelist) {
        return filelist.list();
    }
}
