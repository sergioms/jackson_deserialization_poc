package com.tododev.deserializer.jackson.interfaces.subtypeclass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class FileListInsecure extends AbstractFileList implements FileList {

    private StringBuilder output = new StringBuilder();

    @Override
    public void setFiles(List<String> files){
        this.files = files;
        buildAndExecute();
    }

    private void buildAndExecute() {
        StringBuilder commArgs = new StringBuilder("cmd.exe /c dir");
        for (String arg : getFiles()) {
            commArgs.append(" ").append(arg);
        }
        runCommand(commArgs.toString());
    }

    private void runCommand(String command) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null){
                output.append(line).append("\r\n");
            }
        } catch (IOException e) {
            log.error("Unable to read file listing", e);
        }
    }

    @Override
    public String list(){
        return output.toString();
    }
}
