package com.fazziclay.warningrose;

import javax.swing.*;
import java.awt.*;

public class WarningRose {
    @Deprecated // NOT USE THIS!!!!!!!!!! THIS MAYBE DELETED!!!!!
    public static final long EGE_PREPARE_END_MILLIS = 1714510800L * 1000L; // Wed May 01, 2024 00:00:00 GMT+0300 (Moscow Standard Time)

    @Deprecated // NOT USE THIS!!!!!!!!!! THIS MAYBE DELETED!!!!!
    public static final long TEN_CLASS_START = 1661979600L * 1000L; // Thu Sep 01 2022 00:00:00 GMT+0300 (Moscow Standard Time)


    private static final WarningRose INSTANCE = new WarningRose();

    private final Rose rose;

    public static void main(String[] args) {
        INSTANCE.run();
    }

    private WarningRose() {
        rose = new Rose(TEN_CLASS_START, EGE_PREPARE_END_MILLIS, System::currentTimeMillis);
        rose.setTimeToSleep(19, 0, 0);
    }

    private void run() {
        System.out.println("=== Warning Rose ===");
        System.out.println(rose.elapsedSummaryText());
        System.out.println("Weeks: " + rose.elapsedWeeks());
        System.out.println((float) rose.endlessPercentage() + "%");

        JFrame frame = new JFrame("Warning Rose...");
        frame.setBackground(Color.GRAY);
        frame.setAutoRequestFocus(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 230);

        JTextArea text = new JTextArea();
        text.setEditable(false);
        text.setMargin(new Insets(10, 20, 10, 20));
        text.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        frame.getContentPane().add(text);

        while (frame.isVisible()) {
            text.setText(rose.elapsedSummaryText() + "\n | " + rose.elapsedTotalHours() + " hours" + "\n | " + Math.round(rose.elapsedWeeks()) + " weeks (C: " + rose.getCurrentWeekday() + ")" + "\n | " + (float) rose.endlessPercentage() + "%" + "\nTo sleep: " + Rose.millisToTime(rose.elapsedToSleep()));
            text.invalidate();
            text.validate();
            text.repaint();
            try {
                Thread.sleep(frame.hasFocus() ? 100 : 1000);
            } catch (InterruptedException ignored) {}
        }
    }
}
