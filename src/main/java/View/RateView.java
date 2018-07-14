package View;

import Model.Observer;
import Model.Rateable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RateView extends JPanel implements Observer {
    private List<JButton> rateButton;
    RateView(Rateable rateable) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        rateButton = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            int value = i;
            JButton button = new JButton(Utilities.readImage("star.png"));
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setName(value+"");
            rateButton.add(button);
            button.setPreferredSize(new Dimension(41,41));

            this.add(button);
        }
        this.add(Box.createHorizontalGlue());
        for (JButton b : rateButton) {
            b.setIcon(Utilities.readImage("star.png"));
        }
        for (int j = 0; j <  rateable.getRate(); ++j) {
            rateButton.get(j).setIcon(Utilities.readImage("markStar.png"));
        }

    }

    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the listImage
        // changes.

        repaint();
    }

    public List<JButton> getRateButton() {
        return rateButton;
    }

}
