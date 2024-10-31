package org.Commands;
import org.CommandsFactory.commandsFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PipeCommand {
    // split the command line into command and remaining
    public String[] SplitingCommandLine(String commandline) {
        int SpaceIndex = commandline.indexOf(' ');
        String command="",remaining = "";
        if (SpaceIndex != -1) {
            command = commandline.substring(0, SpaceIndex);
            remaining = commandline.substring(SpaceIndex + 1);
        }
        else command = commandline;
        return new String[]{command,remaining};
    }
    //////////////////////////////////////////////
    public void pipe(String firstCommand, String secondCommand) {
        try {
            // Split the first command line
            String[] commandLine = SplitingCommandLine(firstCommand);
            String command1 = commandLine[0];
            String remaining = commandLine[1];
            // Split the second command line
            commandLine = SplitingCommandLine(secondCommand);
            String command2 = commandLine[0];
            String remaining2 = commandLine[1];
            ///////////////////////////////////////
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(byteArrayOutputStream));
            // find which is the first command
            if (command1.equals("ls")) {
                commandsFactory commandsfactory = new commandsFactory();
                commandsfactory.CommandsFactory(command1, remaining);
            }
            else{
                System.setOut(originalOut);
                System.out.println("invalid command");
                return;
            }
            System.setOut(originalOut);
            List<String> output = new ArrayList<>(Arrays.asList(byteArrayOutputStream.toString().split("\n")));
            // find the second command
            switch (command2){
                case "cat":{
                    File currentDir = new File(System.getProperty("user.dir"));
                    catCommand catcommand = new catCommand(currentDir);
                    String[] parts = remaining2.split(" ");
                    for (String part : parts) {
                        output.add(part);
                    }
                    catcommand.cat(output);
                    break;
                }
                case "sort":{
                    List<String> sortedResult = sort(output);
                    for (String res : sortedResult) {
                        System.out.println(res);
                    }
                    break;
                }
                case "uniq":{
                    List<String> uniqueResult = uniq(output);
                    for (String res : uniqueResult) {
                        System.out.println(res);
                    }
                    break;
                }
                default:{
                    System.out.println("invalid command");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> sort(List<String> list){
        Collections.sort(list);
        return list;
    }
    public List<String> uniq(List<String> list){
        List<String> unique = new ArrayList<String>();
        String previous = "";
        for (String s: list){
            if (!s.equals(previous)){
                unique.add(s);
                previous = s;
            }
        }
        return unique;
    }
}

