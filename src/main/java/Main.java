import Model.ListImage;
import View.View;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        ListImage listImage = null;
        try {
            FileInputStream fileIn = new FileInputStream("image.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            listImage = (ListImage) in.readObject();
            in.close();
            fileIn.close();
            listImage.recovery();
        }  catch (FileNotFoundException f) {
            listImage = new ListImage();
        } catch (IOException i) {
            i.printStackTrace();
        }catch (ClassNotFoundException c) {
            System.err.println("class not found");
        }
        View view = new View(listImage);
    }
}
