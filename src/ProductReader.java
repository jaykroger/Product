import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductReader {
    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        File selectedFile;

        try
        {
            // use the toolkit to get the current working directory of the IDE
            // Not sure if the toolkit is thread safe...
            File workingDirectory = new File(System.getProperty("user.dir"));

            // Typically, we want the user to pick the file, so we use a file chooser
            // kind of ugly code to make the chooser work with NIO.
            // Because the chooser is part of Swing it should be thread safe.

            chooser.setCurrentDirectory(workingDirectory);

            // Using the chooser adds some complexity to the code
            // We have to code the complete program within the conditional return of
            // the FileChooser because the user can close it without picking a file

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                // Typical java pattern of inherited classes
                // we wrap a BufferedReader around a lower level BufferedOutputStream

                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                // Variable initialization for table header
                String tableHeader = "ID#" + "           " + "Name" + "             " + "Description" + "                  " + "Cost";
                String headerSeperator = "==================================================================";

                // Printing table header before filling values from object
                System.out.println();
                System.out.println(tableHeader);
                System.out.println(headerSeperator);

                // Assigns values from CSV file to fields ArrayList
                // Goes by each line in the file
                while(reader.ready())
                {
                    String rec = reader.readLine();
                    String[] fields = rec.split(",", 0);

                    String productID = fields[0];
                    String productName = fields[1];
                    String productDesc = fields[2];
                    String productCost = fields[4];

                    // Must convert YOB value from String to Double
                    String formattedCost = productCost.replace(" ", "");
                    double costDouble = Integer.parseInt(formattedCost);

                    // Creates object from CSV values
                    var product = new Product(productID, productName, productDesc, costDouble);

                    // Prints table from object attributes
                    System.out.printf("%-12s", product.getID());
                    System.out.printf("%-16s", product.getName());
                    System.out.printf("%-30s", product.getDescription());
                    System.out.printf("%-6s", product.getCost());

                    System.out.println();
                }
                reader.close(); // must close the file to seal it and flush buffer
                System.out.println("\n\nData file read!");
            }

        } catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
