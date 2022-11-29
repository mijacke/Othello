package othello;

import java.awt.*;
import java.util.Timer;
import javax.swing.*;
import java.util.TimerTask;

public class MyTimer extends JLabel {
    private final TimerTask timerTask;
    private int minutes;
    private int seconds;

    // We expect that the game will not be longer than 60 minutes
    public MyTimer() {
        this.minutes = 0;
        this.seconds = 0;
        this.setForeground(Color.WHITE);
        this.setFont(new Font("sans-serif", Font.BOLD, 12));
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                setText(timerFormat(minutes, seconds));
                seconds++;
            }
        };

        this.start();
    }

    private String timerFormat(int minutes, int seconds) {
        String cas;
        if (minutes < 10 && seconds < 10) {
            cas = String.format("Play Time: %3s '0" + minutes + ":0" + seconds + "'", " ");
        } else if (seconds < 10) {
            cas = String.format("Play Time: %3s '" + minutes + ":0" + seconds + "'", " ");
        } else if (minutes < 10) {
            cas = String.format("Play Time: %3s '0" + minutes + ":" + seconds + "'", " ");
        } else {
            cas = String.format("Play Time: %3s '" + minutes + ":" + seconds + "'", " ");
        }
        return cas;
    }

    private void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(this.timerTask, 0, 1000);
    }

    public void reset() {
        this.minutes = 0;
        this.seconds = 0;
    }
}
