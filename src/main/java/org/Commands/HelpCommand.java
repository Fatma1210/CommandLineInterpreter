package org.Commands;

public class HelpCommand {
    public String help(){
        String result = "Commands:\n" +
                "pwd : Print Current Directory\n" +
                "cd : Change Directory\n" +
                "ls : List Directory Contents\n" +
                "ls -a : List All Files\n" +
                "ls -r : List Files in Reverse Order\n" +
                "mkdir : Make Directory\n" +
                "rmdir : Remove Directory\n" +
                "touch : Create a New File or Update a File's Timestamp\n" +
                "mv : Move or Rename Files/Directories\n" +
                "rm : Remove Files\n" +
                "cat : Concatenate and Display Files\n" +
                "> : Output Redirection\n" +
                ">> : Append Output Redirection\n" +
                "| : Pipe\n" +
                "exit : To terminate the CLI\n" +
                "help : Displays available commands and their usage.\n";
        return result ;
    }
}
