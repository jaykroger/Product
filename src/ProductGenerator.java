import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductGenerator {
    public static void main(String[] args) {

        // Variable initialization
        Scanner in = new Scanner(System.in);
        ArrayList<String> people = new ArrayList<>();

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\PersonTestData.txt");

        String productRec = "";
        String productID = "";
        String productName = "";
        String productDesc = "";
        double productCost = 0;
        boolean done = false;

        do {

            // Assigns values from user input
            productID = SafeInput.getNonZeroLenString(in, "ID [6 digits]");
            productName = SafeInput.getNonZeroLenString(in, "Product Name");
            productDesc = SafeInput.getNonZeroLenString(in, "Product Description");
            productCost = SafeInput.getDouble(in, "Cost");


            // Initializes person object with attributes populated from user input above
            var product = new Product(productID, productName, productDesc, productCost);

            // Creates record from object and adds to ArrayList people
            productRec = product.getID() + ", " + product.getName() + ", " + product.getDescription() + ", " + product.getCost();
            people.add(productRec);

            // Checks if user is done entering input
            done = SafeInput.getYNConfirm(in, "Done?");
        } while (!done);


        // Loop to print out records from people ArrayList to be added in CSV format
        for (String person : people) {
            System.out.println(person);
        }

        // Calls toCSVDataRecord method to add all records from people ArrayList to .txt file
        toCSVDataRecord(file, people);
    }

    // Method for adding person records to .txt file
    public static void toCSVDataRecord(Path file, ArrayList<String>people) {

        // Writes records from people ArrayList to .txt file

        try
        {
            // Typical java pattern of inherited classes
            // wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Writes the file for each line in the array
            for(String rec : people)
            {
                writer.write(rec, 0, rec.length());  // syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds new line
            }

            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("\n" + "Data file written!");

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
