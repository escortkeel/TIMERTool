
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main extends JFrame {

    public static final int INITALTIMEPANELLENGTH = 100000;
    public static final int INITALLINKPANELLENGTH = 2500;
    public static final int INITALWINDOWTIME = 0;
    public static final int WINDOWLENGTH = 990;
    public static final int DURATIONSCLAINGFACTOR = 250;

    public static void main(String[] args) {
        new Main().setVisible(true);
    }

    public Main() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(1000, 1500);

            JLayeredPane jp = new JLayeredPane();            

            final TimeManager manager = new TimeManager(new File("input.csv"), INITALTIMEPANELLENGTH, INITALLINKPANELLENGTH, INITALWINDOWTIME, WINDOWLENGTH, DURATIONSCLAINGFACTOR);

            LinkPanelTop linkPanelTop = new LinkPanelTop(manager);
            linkPanelTop.setPreferredSize(new Dimension(manager.getTimePanelTotalLength(), 300));
            
            LinkPanelBottom linkPanelBottom = new LinkPanelBottom(manager);
            linkPanelBottom.setPreferredSize(new Dimension(manager.getTimePanelTotalLength(), 300));

            final TimePanel timePanel = new TimePanel(manager, linkPanelTop, linkPanelBottom);
            timePanel.setPreferredSize(new Dimension(manager.getTimePanelTotalLength(), 100));

            final JSlider durationScalingFactorBar = new JSlider();
            durationScalingFactorBar.setMinimum(1);
            durationScalingFactorBar.setMaximum(100);
            durationScalingFactorBar.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    manager.setDurationScalingFactor(durationScalingFactorBar.getValue());
                    timePanel.repaint();
                }
                
            });
            durationScalingFactorBar.setBounds(5, 700, 200, 50);
            
            JScrollPane linksTop = new JScrollPane(linkPanelTop);
            linksTop.setBounds(300, 500, 1000, 250);
            linksTop.setBorder(BorderFactory.createEmptyBorder());
            linksTop.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
            linksTop.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
            
            JScrollPane linksBottom = new JScrollPane(linkPanelBottom);
            linksBottom.setBounds(300, 775, 1000, 250);
            linksBottom.setBorder(BorderFactory.createEmptyBorder());
            linksBottom.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
            linksBottom.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);

            JScrollPane times = new JScrollPane(timePanel);
            times.setBounds(300, 675, 1000, 100);
            times.setBorder(BorderFactory.createEmptyBorder());
            times.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
            times.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);

            TransparentPanel tp = new TransparentPanel(manager);
            tp.setBounds(300, 675, 1000, 100);
            tp.setOpaque(false);

            jp.add(linksTop, new Integer(0));
            jp.add(linksBottom, new Integer(0));
            jp.add(times, new Integer(1));
            jp.add(tp, new Integer(2));
            jp.add(durationScalingFactorBar, new Integer(3));
            
            add(jp);
        } catch (IOException io) {
        }
    }
}
