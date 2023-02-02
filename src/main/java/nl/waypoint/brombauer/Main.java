package nl.waypoint.brombauer;

import nl.waypoint.brombauer.bl.DataGenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Please provide a path to a file.");
            return;
        }
        //Check if the directory of the path exists
        Path path = Path.of(args[0]);
        if(path.getParent().toFile().exists()) {
            try {
                DataGenerator.generateAndWriteToJsonFile(path);
            } catch (IOException | NoSuchAlgorithmException e) {
                System.out.println("The program ran into an error:");
                e.printStackTrace();
            }
        } else {
            System.out.println("The directory of the path does not exist.");
        }
    }
}
