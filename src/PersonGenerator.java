import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class PersonGenerator {
    public static void main(String[] args) {

        // Variable initialization
        Scanner in = new Scanner(System.in);
        ArrayList<String> people = new ArrayList<>();

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\PersonTestData.txt");

        String personRec = "";
        String personID = "";
        String personFirstName = "";
        String personLastName = "";
        String personTitle = "";
        int personYearOfBirth = 0;
        boolean done = false;

        do {

            // Assigns values
            personID = SafeInput.getNonZeroLenString(in, "ID [6 digits]");
            personFirstName = SafeInput.getNonZeroLenString(in, "First Name");
            personLastName = SafeInput.getNonZeroLenString(in, "Last Name");
            personTitle = SafeInput.getNonZeroLenString(in, "Title");
            personYearOfBirth = SafeInput.getRangedInt(in, "Year of Birth ", 1000, 9999);

            // Adds record
            personRec = personID + ", " + personFirstName + ", " + personLastName + ", " + personTitle + ", " + personYearOfBirth;
            people.add(personRec);


            done = SafeInput.getYNConfirm(in, "Done?");
        } while (!done);

        for (String person : people) {
            System.out.println(person);
        }
        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!

            for(String rec : people)
            {
                writer.write(rec, 0, rec.length());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds the new line

            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("\n" + "Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}