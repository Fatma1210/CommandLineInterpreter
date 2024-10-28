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
                " 'touch-command' options :\n" +
                "  -a: Only change the access time.\n" +
                "  -m: Only change the modification time.\n" +
                "  -c: Do not create a file if it does not exist (no error message will appear).\n" +
                "  -t: create a custom timestamp for the file.\n"+
                "mv : Move or Rename Files/Directories\n" +
                "rm options : \n" +
                "  -i: confirmation needed before the deletion is completed.\n" +
                "  -v: a detailed output for each file or directory that is deleted..\n" +
                "  -r: deletes directories.\n" +
                "cat : Concatenate and Display Files\n" +
                "> : Output Redirection\n" +
                ">> : Append Output Redirection\n" +
                "| : Pipe\n" +
                "exit : To terminate the CLI\n" +
                "help : Displays available commands and their usage.\n";
        return result ;
    }
}
