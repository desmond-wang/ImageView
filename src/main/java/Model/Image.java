package Model;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Image  implements Rateable{
    private File path;
    private int rate;
    private String name;
    private String creationTime;
    private transient java.awt.Image image;
    private transient java.awt.Image scaledImage;
    private ListImage listImage;

    public Image(File filepath,int rate, ListImage listImage) throws IOException {
        path = filepath;
        this.listImage = listImage;
        //init rate
        this.rate = rate;
        name = filepath.getName();
        String [] fileName =  name.split("\\.");
        if (fileName[0].length() > 20) {
            String newName = fileName[0].substring(0, 15);
            name = newName + "..." + fileName[0].substring(fileName[0].length() - 2) + "." + fileName[fileName.length - 1];
        }
            image = ImageIO.read(path);
            scaledImage = image.getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH);
            BasicFileAttributes attr = Files.readAttributes(filepath.toPath(), BasicFileAttributes.class);
            Date date = new Date(attr.creationTime().toMillis());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            creationTime = format.format(date);

    }

    public java.awt.Image getImage() {
        return image;
    }

    public java.awt.Image getScaledImage() {
        return scaledImage;
    }

    public String getName() {
        return name;
    }

    public int getRate() {
        return rate;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public boolean setRate(int rate) {
        if( this.rate == rate ){
            this.rate = 0;
            listImage.updateImageRateList(this);
            return false;
        } else {
            this.rate = rate;
            listImage.updateImageRateList(this);
            return true;
        }

    }

    public File getPath() {
        return path;
    }
}
