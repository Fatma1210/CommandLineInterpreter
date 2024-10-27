package org.os;
import org.CommandsFactory.commandsFactory;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello in our Command Line Intrepreter :) \n");
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
