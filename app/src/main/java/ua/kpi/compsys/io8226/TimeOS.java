package ua.kpi.compsys.io8226;

import androidx.annotation.NonNull;
import java.util.Date;


public class TimeOS {
    int hours;
    int minutes;
    int seconds;

    /**
     * Default constructor, that sets time to 00:00:00 AM
     */
    public TimeOS() {
        setTime(0, 0, 0);
    }

    /**
     * The constructor sets time that has been got as parameters
     */
    public TimeOS(int hours, int minutes, int seconds) {
        if ((hours >= 0 && hours <=23) &&
                (minutes >= 0 && minutes <= 59) &&
                (seconds >= 0 && seconds <= 59)) {

            setTime(hours, minutes, seconds);
        } else {
            System.out.println("Object TimeOS was not created! Time must be between 00:00:00 " +
                    "and 23:59:59.");
        }
    }

    /**
     * Constructors gets time from Date object transferred as parameter.
     */
    public TimeOS(Date date) {
        this.hours = date.getHours();
        this.minutes = date.getMinutes();
        this.seconds = date.getSeconds();
    }

    /**
     * Static method for adding 2 TimeOS object transferred as parameters.
     */
    public static TimeOS add2Times(TimeOS time1, TimeOS time2) {
        TimeOS resTime = new TimeOS(time1.hours, time1.minutes, time1.seconds);

        if (time1.hours + time2.hours >= 24)
            resTime.hours = (time1.hours + time2.hours) % 24;
        else
            resTime.hours = time1.hours + time2.hours;

        if (time1.minutes + time2.minutes >= 60) {
            resTime.minutes = (time1.minutes + time2.minutes) % 60;
            if (resTime.hours != 23)
                resTime.hours ++;
            else
                resTime.hours = 0;
        } else resTime.minutes = time1.minutes + time2.minutes;

        if (time1.seconds + time2.seconds >= 60) {
            resTime.seconds = (time1.seconds + time2.seconds) % 60;
            if (resTime.minutes != 59)
                resTime.minutes ++;
            else
                resTime.minutes = 0;
                if (resTime.hours != 23)
                    resTime.hours ++;
                else
                    resTime.hours = 0;
        } else resTime.seconds = time1.seconds + time2.seconds;

        return resTime;
    }


    /**
     * Static method for subtracting 2 TimeOS object transferred as parameters.
     */
    public static TimeOS subtract2Times(TimeOS time1, TimeOS time2) {
        TimeOS resTime = new TimeOS(time1.hours, time1.minutes, time1.seconds);

        if (time1.hours >= time2.hours)
            resTime.hours = time1.hours - time2.hours;
        else {
            if (time2.hours < 24)
                resTime.hours = 24 - (time2.hours - time1.hours);
            else if (time2.hours > 24)
                resTime.hours = 24 - ((time2.hours - time1.hours) % 24);
        }

        if (time1.minutes >= time2.minutes) {
            resTime.minutes = time1.minutes - time2.minutes;
        } else {
            if (time2.minutes < 60)
                resTime.minutes = 60 - (time2.minutes - time1.minutes);
            else if (time2.minutes > 60)
                resTime.minutes = 60 - ((time2.minutes - time1.minutes) % 60);
            if (resTime.hours != 0)
                resTime.hours --;
            else
                resTime.hours = 23;
        }

        if (time1.seconds >= time2.seconds) {
            resTime.seconds = time1.seconds - time2.seconds;
        } else {
            if (time2.seconds < 60)
                resTime.seconds = 60 - (time2.seconds - time1.seconds);
            else if (time2.seconds > 60)
                resTime.seconds = 60 - ((time2.seconds - time1.seconds) % 60);
            if (resTime.minutes != 0)
                resTime.minutes --;
            else {
                resTime.minutes = 59;
                if (resTime.hours != 0)
                    resTime.hours--;
                else
                    resTime.hours = 23;
            }
        }

        return resTime;
    }

    /**
     * Method for adding TimeOS object to the current instance.
     */
    public TimeOS addTime(TimeOS time) {
        TimeOS resTime = new TimeOS(hours, minutes, seconds);

        if (resTime.hours + time.hours >= 24)
            resTime.hours = (resTime.hours + time.hours) % 24;
        else
            resTime.hours += time.hours;

        if (resTime.minutes + time.minutes >= 60) {
            resTime.minutes = (resTime.minutes + time.minutes) % 60;
            if (resTime.hours != 23)
                resTime.hours ++;
            else
                resTime.hours = 0;
        } else resTime.minutes += time.minutes;

        if (resTime.seconds + time.seconds >= 60) {
            resTime.seconds = (resTime.seconds + time.seconds) % 60;
            if (resTime.minutes != 59)
                resTime.minutes ++;
            else
                resTime.minutes = 0;
            if (resTime.hours != 23)
                resTime.hours ++;
            else
                resTime.hours = 0;
        } else resTime.seconds += time.seconds;

        return resTime;
    }

    /**
     * Method for subtracting TimeOS object from the current instance.
     */
    public TimeOS subtractTime(TimeOS time) {
        TimeOS resTime = new TimeOS(hours, minutes, seconds);

        if (resTime.hours >= time.hours)
            resTime.hours -= time.hours;
        else {
            if (time.hours < 24)
                resTime.hours = 24 - (time.hours - resTime.hours);
            else if (time.hours > 24)
                resTime.hours = 24 - ((time.hours - resTime.hours) % 24);
        }

        if (resTime.minutes >= time.minutes) {
            resTime.minutes -= time.minutes;
        } else {
            if (time.minutes < 60)
                resTime.minutes = 60 - (time.minutes - resTime.minutes);
            else if (time.minutes > 60)
                resTime.minutes = 60 - ((time.minutes - resTime.minutes) % 60);
            if (resTime.hours != 0)
                resTime.hours --;
            else
                resTime.hours = 23;
        }

        if (resTime.seconds >= time.seconds) {
            resTime.seconds -= time.seconds;
        } else {
            if (time.seconds < 60)
                resTime.seconds = 60 - (time.seconds - resTime.seconds);
            else if (time.seconds > 60)
                resTime.seconds = 60 - ((time.seconds - resTime.seconds) % 60);
            if (resTime.minutes != 0)
                resTime.minutes --;
            else {
                resTime.minutes = 59;
                if (resTime.hours != 0)
                    resTime.hours--;
                else
                    resTime.hours = 23;
            }
        }

        return resTime;
    }


    @NonNull
    @Override
    public String toString() {
        String hours = "";
        String minutes = "";
        String seconds = "";
        String partOfTheDay;

        if (this.hours >= 12) {
            if (this.hours - 12 < 10 && this.hours != 12) {
                hours += "0";
                hours += this.hours - 12;
            } else if (this.hours == 12) {
                hours += 12;
            } else {
                hours += this.hours - 12;
            }
            partOfTheDay = "PM";
        } else {
            if (this.hours < 10 && this.hours != 0) {
                hours += "0";
                hours += this.hours;
            } else if (this.hours == 0) {
                hours += 12;
            } else {
                hours += this.hours;
            }
            partOfTheDay = "AM";
        }

        minutes = this.minutes > 9 ? String.valueOf(this.minutes) : ("0" + this.minutes);
        seconds = this.seconds > 9 ? String.valueOf(this.seconds) : ("0" + this.seconds);

        return hours + ":" + minutes + ":" + seconds + " " + partOfTheDay;
    }


    private void setTime(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
}
