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

    /**
     * Constructor for a custom <code>timer</code> used in the game. <br>
     * It initializes seconds and minutes (we expect that the game will not be longer
     * than 60 minutes), timerTask with the overridden timerTask method run(),
     * sets the foreground color, font and starts the timer (which is also formatted).
     */
    public MyTimer() {
        this.seconds = 0;
        this.minutes = 0;
        this.setForeground(Color.WHITE);
        this.setFont(new Font("sans-serif", Font.BOLD, 12));
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                if (MyTimer.this.seconds == 60) {
                    MyTimer.this.seconds = 0;
                    MyTimer.this.minutes++;
                }
                setText(timerFormat(MyTimer.this.minutes, MyTimer.this.seconds));
                MyTimer.this.seconds++;
            }
        };

        this.start();
    }

    /**
     * Method for formatting the displayed timer.
     *
     * @param minutes the minutes that are currently displayed
     * @param seconds the seconds that are currently displayed
     * @return a formatted time
     */
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

    /**
     * Method which initializes the Timer and schedules the specified task
     * for repeated fixed-rate execution which is set to 1 second (1000ms).
     */
    private void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(this.timerTask, 0, 1000);
    }

    /**
     * Method that resets the timer to 0 minutes and seconds.
     */
    public void reset() {
        this.minutes = 0;
        this.seconds = 0;
    }
}
