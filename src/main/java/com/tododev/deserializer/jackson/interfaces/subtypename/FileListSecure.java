package com.tododev.deserializer.jackson.interfaces.subtypename;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileListSecure extends AbstractFileList implements FileList {

    @Override
    public String list() {
        return buildAndExecute();
    }

    private String buildAndExecute() {
        List<String> cmd = new ArrayList<>();
        cmd.add("dir");
        cmd.addAll(getFiles());
        return runCommand(cmd);
    }

    private String runCommand(List<String> args){
        StringBuilder output = new StringBuilder();

        ProcessBuilder bldr = new ProcessBuilder(args);
        bldr.redirectErrorStream(true);
        try {
            Process process = bldr.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                output.append(line).append("\r\n");
            }
        } catch (IOException e) {
            log.error("Unable to read file listing", e);
        }
        return output.toString();
    }
}
