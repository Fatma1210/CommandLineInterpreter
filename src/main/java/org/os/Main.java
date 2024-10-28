package org.os;
import org.CommandsFactory.commandsFactory;
import java.util.Scanner;
import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello in our Command Line Intrepreter :) \n");
        System.out.println("Type 'exit' to close the program or 'help' to show the list of commands.\n");
        Scanner scanner = new Scanner(System.in);
        commandsFactory Factory = new commandsFactory();
        while (true) {
            String inputCommand = scanner.nextLine();
            int SpaceIndex = inputCommand.indexOf(' ');
            String command = "", Path = "";
            if(SpaceIndex != -1){
                command = inputCommand.substring(0, SpaceIndex);
                Path = inputCommand.substring(SpaceIndex + 1);
            }
            else command = inputCommand ;
            Factory.CommandsFactory(command.toLowerCase(), Path);
        }
    }
}