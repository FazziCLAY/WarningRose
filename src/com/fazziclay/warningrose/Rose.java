package com.fazziclay.warningrose;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Supplier;

public class Rose {
    private static final int MILLISECOND = 1;
    private static final int SECOND = MILLISECOND * 1000;
    private static final int MINUTE = SECOND * 60;
    private static final int HOUR = MINUTE * 60;
    private static final int DAY = HOUR * 24;

    private final String[] weekdays = new String[] {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Unknown 1",
            "Unknown 2",
            "Unknown 3",
            "Unknown 4",
            "Unknown 5",
            "Unknown 6",
            "Unknown 7",
    };
    private final long startMillis;
    private final Calendar startCalendar; // unused...

    private final long endMillis;
    private final Calendar endCalendar;

    private final Supplier<Long> currentTimeSupplier;
    private int hourToSleep;
    private int minuteToSleep;
    private int secondToSleep;

    public Rose(long startMillis, long endMillis, Supplier<Long> currentTimeSupplier) {
        this.startMillis = startMillis;
        this.startCalendar = new GregorianCalendar();
        this.startCalendar.setTimeInMillis(startMillis);

        this.endMillis = endMillis;
        this.endCalendar = new GregorianCalendar();
        this.endCalendar.setTimeInMillis(endMillis);


        this.currentTimeSupplier = currentTimeSupplier;
    }

    public void setTimeToSleep(int hourToSleep, int minuteToSleep, int secondToSleep) {
        this.hourToSleep = hourToSleep;
        this.minuteToSleep = minuteToSleep;
        this.secondToSleep = secondToSleep;
    }

    public long elapsedTotal() {
        return endMillis - currentMs();
    }

    public float elapsedDays() {
        return (float) elapsedTotal() / DAY;
    }

    public int elapsedYears() {
        final var currentCalendar = new GregorianCalendar();
        currentCalendar.setTimeInMillis(currentMs());

        return endCalendar.get(Calendar.YEAR) - currentCalendar.get(Calendar.YEAR);
    }

    public float elapsedWeeks() {
        return (elapsedDays() + (endCalendar.get(Calendar.DAY_OF_WEEK) - 1)) / 7F;
    }

    public int elapsedTotalHours() {
        long durationInMillis = elapsedTotal();

        return (int) ((durationInMillis / (1000 * 60 * 60)) % 24) + ((int)elapsedDays() * 24);
    }

    public double endlessPercentage() {
        double b = endMillis - startMillis;
        return elapsedTotal() / b * 100D;
    }

    private long currentMs() {
        return currentTimeSupplier.get();
    }

    public String elapsedSummaryText() {
        long durationInMillis = elapsedTotal();

        int h = (int) ((durationInMillis / (1000 * 60 * 60)) % 24);
        int m = (int) ((durationInMillis / (1000 * 60)) % 60);
        int s = (int) ((durationInMillis / 1000) % 60);
        return String.format("%s days %02d:%02d:%02d", ((int)Math.floor(elapsedDays())), h, m, s);
    }

    public String getCurrentWeekday() {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(currentMs());
        return weekdays[gregorianCalendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public long elapsedToSleep() {
        long curr;
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(curr = currentMs());
        gregorianCalendar.set(Calendar.HOUR_OF_DAY, hourToSleep);
        gregorianCalendar.set(Calendar.MINUTE, minuteToSleep);
        gregorianCalendar.set(Calendar.SECOND, secondToSleep);
        gregorianCalendar.set(Calendar.MILLISECOND, 0);

        return gregorianCalendar.getTimeInMillis() - curr;
    }

    public static String millisToTime(long durationInMillis) {
        int h = (int) ((durationInMillis / (1000 * 60 * 60)) % 24);
        int m = (int) ((durationInMillis / (1000 * 60)) % 60);
        int s = (int) ((durationInMillis / 1000) % 60);
        return String.format("%02d:%02d:%02d", h, m, s);
    }
}
