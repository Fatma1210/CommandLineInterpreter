package org.CommandsFactory;
import org.Commands.*;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class commandsFactory {
    public void CommandsFactory(String command , String inputPath) {
        switch (command) {
            case "cd": {
                cdCommand cdCommand = new cdCommand();
                try {
                    cdCommand.cd(inputPath) ;
                } catch (NoSuchFileException e) {
                    System.out.println("Directory does not exist. Please try again.");
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Invalid directory input. Please enter a valid path.");
                } catch (IOException e) {
                    System.out.println("An I/O error occurred: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred: " + e.getMessage());
                }
                break;
            }
            case "pwd" : {
                PwdCommand pwdCommand = new PwdCommand() ;
                System.out.println(pwdCommand.Pwd());
                break;
            }
            case "exit" : {
                ExitCommand exitCommand = new ExitCommand() ;
                exitCommand.Exit();
                break;
            }
            case "help" :{
                HelpCommand helpCommand = new HelpCommand() ;
                System.out.println(helpCommand.help());
                 break;
            }
            case "ls" : {

            }
            case "ls -a" : {

            }
            case "ls -r" : {

            }
            case "mkdir" : {
            }
            case "rmdir" : {
            }
            case "touch" : {}
            case "mv" : {}
            case "rm" : {}
            case "cat" : {}
            case ">" :{ }
            case ">>" : {}
            case "|" : {}
            default:
            System.out.println("This Command does not exist,Enter a correct command");
        }
    }
}
