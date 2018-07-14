package View;

import Model.Image;
import Model.Layout;
import Model.ListImage;
import Model.Observer;

import javax.swing.*;
import java.awt.*;

public class ImageCollectionView extends JPanel implements Observer {
    private ListImage listImage;
    private int filter;
    private int imageViewWidth = 210;

    public ImageCollectionView(ListImage listImage){
        this.listImage = listImage;
//        for(Image image: this.listImage.getImageList()){
//            JPanel imageView = new ImageView(image);
//            this.add(imageView);
//        }
//        this.filter = listImage.getRate();
        this.listImage.addObserver(this);
        update(this.listImage);

//        ImageCollectionView self = this;
//        addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                //System.out.println(getWidth()+ " " + getHeight());
//                int cols = getWidth() / imageViewWidth ;
//                self.setLayout(new GridLayout(0,cols));
//            }
//        });
    }



    @Override
    public void update(Object observable) {
        this.removeAll();
        filter = listImage.getRate();
        Layout layout = listImage.getLayout();
        for(Image image: this.listImage.getImageList()){
            if(image.getRate() < filter && filter != 0){
                continue;
            }
            JPanel imageView = new ImageView(image,layout);
            this.add(imageView);
        }
        this.filter = listImage.getRate();
        if(listImage.getLayout() == Layout.GRID){
            int cols = getWidth() / imageViewWidth;
            if(cols == 0){
                cols = 1;
            }
            this.setLayout(new GridLayout(0,cols));
        } else {
            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        }
        repaint();
        revalidate();
    }

}
