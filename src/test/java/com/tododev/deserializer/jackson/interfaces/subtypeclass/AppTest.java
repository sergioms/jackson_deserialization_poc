package com.tododev.deserializer.jackson.interfaces.subtypeclass;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import static org.junit.Assert.*;

public class AppTest
{
    private final static Logger log = LoggerFactory.getLogger(AppTest.class);
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private static String JSON_LIST_FILES_CLASSID = "{\"class\":\"com.tododev.deserializer.jackson.interfaces.subtypeclass.FileListSecure\",\"files\":[\"C:\\\\\"]}";

    public final static String JSON_SYSUTIL_DEF_TYP = "{\"fileList\":[\"com.tododev.deserializer.jackson.interfaces.subtypeclass.FileListSecure\",{\"files\":[\"java.util.ArrayList\",[\"C:\\\\\"]]}]}";

    private static String JSON_SYSUTIL_CLASSID = "{\"fileList\":{\"class\":\"com.tododev.deserializer.jackson.interfaces.subtypeclass.FileListSecure\",\"files\":[\"C:\\\\\"]}}";

    @Test(expected = InvalidTypeIdException.class)
    public void deserializeInsecure () throws IOException
    {
        FileListInsecure task = objectMapper.readValue(com.tododev.deserializer.jackson.bean.AppTest.JSON_LIST_FILES,
                FileListInsecure.class);
    }

    @Test(expected = InvalidTypeIdException.class)
    public void deserializeSecureNoExec () throws IOException
    {
        FileListSecure task = objectMapper.readValue(com.tododev.deserializer.jackson.bean.AppTest.JSON_LIST_FILES,
                FileListSecure.class);
    }

    @Test(expected = InvalidTypeIdException.class)
    public void willNotDeserializeFileListInterface() throws IOException
    {
        FileList task = objectMapper.readValue(com.tododev.deserializer.jackson.bean.AppTest.JSON_LIST_FILES,
                FileList.class);
    }

    @Test
    public void FileListInterfaceClassId() throws IOException
    {
        FileList fileList = objectMapper.readValue(JSON_LIST_FILES_CLASSID, FileList.class);
        assertNotNull(fileList);
        assertTrue(fileList instanceof FileListSecure);
        assertFalse(fileList instanceof FileListInsecure);
    }

    @Test(expected = InvalidTypeIdException.class)
    public void willNotDeserializeSysUtils() throws IOException
    {
        SysUtils utils = objectMapper.readValue(com.tododev.deserializer.jackson.bean.AppTest.JSON_SYSUTIL,
                SysUtils.class);
    }

    @Test
    public void polimorphismAndClassIdAreNotTheSame(){
        assertNotEquals(JSON_SYSUTIL_CLASSID, com.tododev.deserializer.jackson.bean.AppTest.JSON_SYSUTIL_DEF_TYP);
    }

    @Test
    public void deserializeSysUtilsClassId() throws IOException{
        SysUtils utils = objectMapper.readValue(JSON_SYSUTIL_CLASSID, SysUtils.class);
        assertNotNull(utils);
        assertTrue(utils.getFileList() instanceof FileListSecure);
        assertFalse(utils.getFileList() instanceof FileListInsecure);
    }

    @Test
    public void enablePolimorphismForMapper() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();
        SysUtils utils = objectMapper.readValue(JSON_SYSUTIL_DEF_TYP, SysUtils.class);
        assertNotNull(utils);
        assertTrue(utils.getFileList() instanceof FileListSecure);
        assertFalse(utils.getFileList() instanceof FileListInsecure);
    }


}
