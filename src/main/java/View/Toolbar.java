package View;

import Model.Layout;
import Model.ListImage;
import Model.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;

public class Toolbar extends JPanel implements Observer {
    private JButton grideView;
    private JButton listView;
    private JButton openFile;
    private JLabel name;
    private List<JButton> rateButton;
    private ListImage listImage;

    public Toolbar(ListImage listImage) {

        this.listImage = listImage;
        JComponent rateStar = new RateView(listImage);
        rateButton = ((RateView) rateStar).getRateButton();
        for (JButton button : rateButton){
            button.addActionListener(e -> {
                boolean reval = listImage.setRate(Integer.parseInt(button.getName())+1);

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
                listImage.notifyObservers();
            });
        }
        openFile = new JButton(Utilities.readImage("openFile.png"));
        openFile.setBorderPainted(false);
        openFile.setContentAreaFilled(false);
        openFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setFileFilter(new FileNameExtensionFilter(
                    "Image files", ImageIO.getReaderFileSuffixes()));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File[] files = fileChooser.getSelectedFiles();
                try {
                    for (File file : files) {
                        listImage.addImageList(file, 0, false);
                        listImage.notifyObservers();
                    }
                } catch (Exception e1) {
                    e1.getStackTrace();
                    //throw new RuntimeException(e1);
                }
            }
        });
        name = new JLabel("Fotag!");
        name.setFont(new Font(name.getFont().getName(), Font.BOLD, 40) );
        name.setHorizontalAlignment(JLabel.CENTER);
        JPanel changeView = new JPanel();
        changeView.setLayout(new BoxLayout(changeView, BoxLayout.X_AXIS));
        grideView = new JButton(Utilities.readImage("toggleGrid.png"));
        grideView.setBorderPainted(false);
        grideView.setContentAreaFilled(false);
        listView = new JButton(Utilities.readImage("list.png"));
        listView.setBorderPainted(false);
        listView.setContentAreaFilled(false);
        grideView.addActionListener(e-> {
            listImage.setLayout(Layout.GRID);
            listView.setIcon(Utilities.readImage("list.png"));
            grideView.setIcon(Utilities.readImage("toggleGrid.png"));
        });

        listView.addActionListener(e-> {
            listImage.setLayout(Layout.LIST);
            grideView.setIcon(Utilities.readImage("grid.png"));
            listView.setIcon(Utilities.readImage("toggleList.png"));
        });

        changeView.add(grideView);
        changeView.add(Box.createRigidArea(new Dimension(5,0)));
        changeView.add(listView);

        JPanel rightPart = new JPanel();
        rightPart.setLayout(new BoxLayout(rightPart, BoxLayout.X_AXIS));
        rightPart.add(openFile);
        rightPart.add(Box.createRigidArea(new Dimension(5,0)));
        rightPart.add(rateStar);

        setLayout(new BorderLayout());
        add(changeView, BorderLayout.WEST);
        add (name, BorderLayout.CENTER);
        add(rightPart, BorderLayout.EAST);

        this.listImage.addObserver(this);
        update(listImage);
    }



    @Override
    public void update(Object observable) {
        repaint();
    }
}
