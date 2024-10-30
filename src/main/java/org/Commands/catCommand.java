package org.Commands;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class catCommand {
    private File workingDirectory;

    public catCommand(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
    public void cat(String Sel_files) throws IOException {
        try {
            StringBuilder combinedContent = new StringBuilder();
            if (Sel_files.equals("*")) {
                String[] array = workingDirectory.list();
                if (array != null) {
                    for (String file : array) {
                        if (file.endsWith(".txt")) {
                            String content = new String(Files.readAllBytes(Paths.get(workingDirectory.getAbsolutePath(), file)));
                            combinedContent.append(content).append("\n");
                        }
                    }
                }
            } else {
                String[] Sel_files_array = Sel_files.split(" ");
                for (String file : Sel_files_array) {
                    if (file.endsWith(".txt")) {
                        String content = new String(Files.readAllBytes(Paths.get(workingDirectory.getAbsolutePath(), file)));
                        combinedContent.append(content).append("\n");
                    } else if (file.equals(">")) {
                        String Combined = combinedContent.toString();
                        OutputCommand outputCommand = new OutputCommand();
                        outputCommand.output(Combined, Sel_files_array[Sel_files_array.length - 1]);
                        return;
                    } else if (file.equals(">>")) {
                        String Combined = combinedContent.toString();
                        AppendCommand appendCommand = new AppendCommand();
                        appendCommand.append(Combined, Sel_files_array[Sel_files_array.length - 1]);
                        return;
                    }
                }
            }
            String Combined = combinedContent.toString();
            if (Combined.isEmpty()) {
                System.out.println("No files found");
            } else {
                System.out.println("The combined content is: ");
                System.out.println(Combined);
            }
        } catch (IOException e) {
            System.out.println("Error when Reading files: " + e.getMessage());
        }
    }

}
