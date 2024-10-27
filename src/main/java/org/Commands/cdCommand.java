package org.Commands;

import java.nio.file.*;

import java.io.*;

public class cdCommand {
    File TargetDirectory = new File(System.getProperty("user.dir")) ;
    public String cd(String inputPath) throws NoSuchFileException, IOException {
        File workingDirectory = new File(System.getProperty("user.dir"));
        if (inputPath.equals("..")) {
            String parent = workingDirectory.getParent();
            if (parent != null) {
                System.setProperty("user.dir", parent);
                System.out.println("Directory changed to: " + System.getProperty( "user.dir"));
            } else {
                throw new NoSuchFileException(workingDirectory.getAbsolutePath(), null, "Directory does not exist");
            }
        }
        else if(inputPath.equals(".")){
            System.out.println(System.getProperty("user.dir"));
        }
        else if(inputPath.equals("") || inputPath.equals("~")){
            System.setProperty("user.dir", System.getProperty("user.home"));
            System.out.println("Directory changed to: " + System.getProperty("user.dir"));
        }
        else {
            File TargetDirectory =make_Absolute(inputPath) ;
            if (TargetDirectory.exists() && TargetDirectory.isDirectory()) {
                String path =TargetDirectory.getCanonicalPath();
                System.setProperty("user.dir", path);
                System.out.println("Directory changed to: " + System.getProperty("user.dir"));
            } else {
                throw new NoSuchFileException(workingDirectory.getAbsolutePath(), null, "Directory does not exist");
            }
        }
        return System.getProperty( "user.dir") ;
    }
    public File make_Absolute(String inputPath){
        File file = new File(inputPath) ;
        if(!file.isAbsolute()){
            file = new File(TargetDirectory.getAbsolutePath() , inputPath) ;
        }
        return file ;
    }
}
