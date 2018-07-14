package Model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class ListImage implements Rateable, Serializable {
    /** The observers that are watching this model for changes. */
    private static final long serialVersionUID = 1339350;
    private transient List<Observer> observers;
    private transient List<Image> imageList;
    private List<File> fileList;
    private List<Integer> ImageRateList;
    private int rate;
    private Layout layout;

    /**
     * Create a new model.
     */
    public ListImage() {
        rate = 0;
        this.observers = new ArrayList<>();
        this.imageList = new ArrayList<>();
        this.fileList = new ArrayList<>();
        this.ImageRateList = new ArrayList<>();
        layout = Layout.GRID;
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void init(){
        if(observers == null){
            this.observers = new ArrayList<>();
            this.imageList = new ArrayList<>();
        }
    }
    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    public boolean setRate(int filter) {
        if( this.rate == filter ){
            this.rate = 0;
            return false;
        } else {
            this.rate = filter;
            return true;
        }

    }

    public void addImageList(File imagePath,int rate,boolean isRecovery){
        Image newImage;
        try {
            newImage = new Image(imagePath,rate,this);
        } catch (IOException e) {
            System.err.println("image file failed to load");
            return; // image file failed to load
        }
        imageList.add(newImage);
        if(!isRecovery) {
            fileList.add(imagePath);
            ImageRateList.add(0);
        }
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
        notifyObservers();
    }

    public void recovery(){
        init();
        for (int i = 0; i < fileList.size(); ++i){
            addImageList(fileList.get(i),ImageRateList.get(i),true);
        }
        notifyObservers();
    }


    @Override
    public int getRate() {
        return rate;
    }

    void updateImageRateList(Image image) {
        File imagePath = image.getPath();
        for(int i = 0; i < fileList.size(); ++i){
            if (fileList.get(i) == imagePath){
                ImageRateList.set(i,image.getRate());
            }
        }

    }

    void updateImage(){

    }
}
