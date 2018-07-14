package View;

import Model.Image;
import Model.Layout;
import Model.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ImageView extends JPanel implements Observer {
    private List<JButton> rateButton;
    private Image image;

    ImageView(Image image, Layout layout) {
        this.image = image;
        JLabel imagePic = new JLabel();
//
//        imagePic.setAlignmentX(0); //
        imagePic.setIcon(new ImageIcon(this.image.getScaledImage()));
        JLabel name = new JLabel(image.getName());
        JLabel creationDate = new JLabel(image.getCreationTime());

        JComponent rateStar = new RateView(image);
        rateButton = ((RateView) rateStar).getRateButton();
        rateStar.setAlignmentX(0);
//
//        rateStar.setLayout(new GridLayout(1,0));
        for (JButton button : rateButton) {
            button.addActionListener(e -> {
                boolean reval = image.setRate(Integer.parseInt(button.getName()) + 1);
                if (reval) {
                    for (JButton b : rateButton) {
                        b.setIcon(Utilities.readImage("star.png"));
                    }
                    for (int j = 0; j < Integer.parseInt(button.getName()) + 1; ++j) {
                        rateButton.get(j).setIcon(Utilities.readImage("markStar.png"));
                    }
                } else {
                    for (JButton b : rateButton) {
                        b.setIcon(Utilities.readImage("star.png"));
                    }
                }
            });
        }
        imagePic.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                //open new Image windows
                JFrame ImageWindow = new JFrame();
                ImageWindow.setTitle(image.getName());
                ImageWindow.setMinimumSize(new Dimension(128, 128));
                ImageWindow.setSize(800, 600);
                ImageWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                JLabel picture = new JLabel();
                picture.setIcon(new ImageIcon(image.getImage().
                        getScaledInstance(ImageWindow.getWidth(), ImageWindow.getHeight(), java.awt.Image.SCALE_SMOOTH)));
                ImageWindow.add(picture);
                ImageWindow.setVisible(true);
            }
        });

        imagePic.setPreferredSize(new Dimension(210, 150));
        rateStar.setPreferredSize(new Dimension(200, 50));
        if (layout == Layout.GRID) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(imagePic);
            this.add(name);
            this.add(creationDate);
            this.add(rateStar);
        } else {
//            JPanel mataData = new JPanel();
//            mataData.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//            mataData.add(name);
//            mataData.add(creationDate);
//            mataData.add(rateStar);
//            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//            this.add(imagePic);
//            this.add(mataData);


            GroupLayout lout = new GroupLayout(this);
            this.setLayout(lout);
            lout.setAutoCreateContainerGaps(true);
            lout.setAutoCreateGaps(true);
            lout.setHorizontalGroup(
                    lout.createSequentialGroup()
                            .addComponent(imagePic)
                            .addGroup(lout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(name)
                                    .addComponent(creationDate)
                                    .addComponent(rateStar))
            );
            lout.setVerticalGroup(
                    lout.createSequentialGroup()
                            .addGroup(lout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(imagePic)
                                    .addGroup(lout.createSequentialGroup()
                                            .addComponent(name)
                                            .addComponent(creationDate)
                                            .addComponent(rateStar))
                            )
            );
        }
        update(image);

    }

    @Override
    public void update(Object observable) {
        repaint();
    }
}

