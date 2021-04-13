package myPkg;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files

public class Decoder {
    public int[][] read_preferences(String nameFile) {
        try {
            File myFile = new File(nameFile);
            Scanner myScan = new Scanner(myFile);
            int size = Integer.valueOf(myScan.nextLine());
            int[][] preferences = new int[size * 2][size + 1];
            int counter = 0;
            while (myScan.hasNextLine()) {
                String lines[] = myScan.nextLine().split("\\s+");
                String lines_aux[] = Arrays.copyOfRange(lines, 1, lines.length);
                int all_values[] = new int[lines_aux.length];
                for(int i = 0; i < lines_aux.length; i++)
                    all_values[i] = Integer.valueOf(lines_aux[i].replaceAll("[A-Za-z]", ""));
                preferences[counter] = all_values;
                counter++;
            }
            myScan.close();
            return preferences;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    public int read_size(String nameFile) {
        try {
            File myFile = new File(nameFile);
            Scanner myScan = new Scanner(myFile);
            int size = Integer.valueOf(myScan.nextLine());
            myScan.close();
            return size;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 0;
    }
}