package View;

import Model.Layout;
import Model.ListImage;
import Model.Observer;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.*;

public class View extends JFrame implements Observer {

    private ListImage listImage;

    private int width;
    private int height;

    /**
     * Create a new View.View.
     */
    public View(ListImage listImage) {
        // Set up the window.
        width = 800;
        height = 600;
        this.setTitle("Fotag!");
        this.setMinimumSize(new Dimension(128, 128));
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // Hook up this observer so that it will be notified when the listImage
        // changes.
        this.listImage = listImage;
        JPanel toolBar = new Toolbar(this.listImage);
        JPanel imageCollection = new ImageCollectionView(this.listImage);


        add(toolBar, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(imageCollection, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll,BorderLayout.CENTER);

        View self = this;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(self.listImage.getImageList().isEmpty()){
                    System.exit(0);
                }
                try
                {
                    //Saving of object in a file
                    FileOutputStream file = new FileOutputStream("image.ser");
                    ObjectOutputStream out = new ObjectOutputStream(file);

                    // Method for serialization of object
                    out.writeObject(self.listImage);

                    out.close();
                    file.close();

                    //System.out.println("Object has been serialized in to file: image.ser");

                }
                catch(IOException ex)
                {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(listImage.getLayout() == Layout.LIST){
                    self.setMinimumSize(new Dimension(440,250));
                } else {
                    //System.out.println("width:" + getWidth() + " height:" + getHeight());
                    self.setMinimumSize(new Dimension(225,320));
                    int cols = getWidth() / 210;
                    if(cols == 0){
                        cols = 1;
                    }
                    imageCollection.setLayout(new GridLayout(0, cols));
                }
            }
        });

        setVisible(true);
    }

    /**
     * Update with data from the listImage.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the listImage
        // changes.
        System.out.println("Model.ListImage changed!");
    }
}
