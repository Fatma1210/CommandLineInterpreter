package org.CommandsFactory;
import org.Commands.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NoSuchFileException;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class commandsFactory {
    public void CommandsFactory(String command , String inputPath) throws IOException {
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
                // Set the current working directory
                File currentDir = new File(System.getProperty("user.dir"));
                LsCommand lsCommand = new LsCommand(currentDir);
                boolean showHidden=false;
                boolean reverseOrder = false;
                if (inputPath != null) {
                    String[] parts = inputPath.trim().split("\\s+"); // Split by spaces
                    for (String part : parts) {
                        if (part.equals("-a")) {
                            showHidden = true;
                        }
                        if (part.equals("-r")) {
                            reverseOrder = true;
                        }
                    }
                    // Remove -a and -r from inputPath
                    inputPath = inputPath.replace("-a", "").replace("-r", "").trim();
                }
                try {
                    if (inputPath != null && !inputPath.isEmpty()) {
                        // If a path is provided, use it
                        lsCommand.ls(inputPath, showHidden,reverseOrder);
                    } else {
                        // If no path is provided, display the contents of the current directory
                        lsCommand.ls(showHidden,reverseOrder);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage()); // Handle both empty directory and not a directory exceptions
                } catch (NoSuchFileException e) {
                    System.err.println(e.getMessage()); // Handle the case where the specified file or directory does not exist
                }
                break;
            }
            case "mkdir" : {
                // Use regex to match quoted and unquoted directory names
                String regex = "\"([^\"]+)\"|([^\\s]+)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(inputPath);
                // List to hold the directory names
                List<String> newDirs = new ArrayList<>();
                // Set the current working directory
                File currentDir = new File(System.getProperty("user.dir"));
                MkdirCommand mkdirCommand = new MkdirCommand(currentDir);
                while (matcher.find()) {
                    // Check if the directory name is quoted or not
                    if (matcher.group(1) != null) {
                        // Quoted directory name
                        newDirs.add(matcher.group(1));
                    } else {
                        // Unquoted directory name
                        newDirs.add(matcher.group(2));
                    }
                }
                // Create directories
                for (String dirName : newDirs) {
                    try {
                        mkdirCommand.mkdir(dirName);
                    } catch (NoSuchFileException e) {
                        System.out.println("Parent directory does not exist: " + e.getMessage());
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                break;
            }
            case "rmdir" : {
                RmdirCommand rmdirCommand = new RmdirCommand();
                rmdirCommand.rmdir(inputPath);
                break;
            }
            case "touch": {
                TouchCommand touchCommand = new TouchCommand();
                touchCommand.touch(inputPath); // Pass the Path variable to the touch method
                break;
            }
            case "mv" : {
                MvCommand mvCommand = new MvCommand();

                // Assuming inputPath holds the source and destination paths separated by a space
                String[] paths = inputPath.split(" ");

                if (paths.length < 2) {
                    System.out.println("Error: Missing source or destination path for mv command.");
                } else {
                    String sourcePath = paths[0];
                    String destinationPath = paths[1];
                    mvCommand.mv(sourcePath, destinationPath);
                }
                break;
            }
            case "rm" : {
                Rmcommand rmCommand = new Rmcommand();
                String[] args = inputPath.split(" ");
                rmCommand.rm(args);
                break;
            }
            case "cat" : {
                File currentDir = new File(System.getProperty("user.dir"));
                catCommand catCommand = new catCommand(currentDir);
                catCommand.cat(inputPath);
                break;
            }

            case ">" :{
                String[] parts = inputPath.split(">",2);
                String text = parts[0].trim();
                String file = parts[1].trim();
                OutputCommand outputCommand = new OutputCommand();
                outputCommand.output(text,file);
                break;
            }
            case ">>" : {
                String[] parts = inputPath.split(">>",2);
                String text = parts[0].trim();
                String file = parts[1].trim();
                AppendCommand appendCommand = new AppendCommand();
                appendCommand.append(text,file);
                break;
            }
            case "|" : {
                String[] parts = inputPath.split("\\|",2);
                String firstCommand = parts[0].trim();
                String secondCommand = parts[1].trim();
                PipeCommand pipeCommand = new PipeCommand();
                pipeCommand.pipe(firstCommand,secondCommand);
                break;
            }
            default:
            System.out.println("This Command does not exist,Enter a correct command");
        }
    }
}
