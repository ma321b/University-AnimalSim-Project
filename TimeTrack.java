/**
 * A class to keep track of the time and step in the simulation.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */

public class TimeTrack
{
    // step field from simulator in here
    // also all other time related methods.
    // make a new object of this class in constructor of simulator class

    private static int step;
    private static int currentTime;
    private static boolean isDay;
    private static int currentDay;
    private static int minutes;

    public TimeTrack()
    {
        reset();
    }
    
    public void reset()
    {
        step = 0;
        currentTime = 0;
        minutes = 0;
        currentDay = 1;
        isDay = false;
    }

    /**
     * Increments the currentTime (hour) in the simulation.
     */
    private void incrementCurrentTime()
    {
        if (currentTime < 23) {
            currentTime++;
        }
        else {
            // Rollback
            incrementCurrentDay();
            currentTime = 0;
        }
    }

    /**
     * Increment the current day.
     */
    private void incrementCurrentDay()
    {
        currentDay += 1;
    }

    /**
     * Increment minutes in the simulation.
     * (10 minute in simulation is 1 steps)
     */
    private static void incrementMinutes()
    {
        if (minutes < 50) {
                minutes += 10;
        }
        else {
            // Rollback
            minutes = 0;
        }
    }

    /**
     * Set the current time in simulation
     * according to the step of the simulation
     * we're currently in. An hour is 5 steps
     * long in our simulation.
     */
    public void setCurrentTime()
    {
        if (step % 6 == 0 && step != 0) {
            incrementCurrentTime();
        }
        setDayOrNight();
        incrementMinutes();
    }

    /**
     * Increment the value of step by 1.
     */
    public static void incrementStep()
    {
        step++;
    }

    /**
     * Set the time of the day as true or false
     * corresponding to the current time in the simulation.
     * (The time between 18 - 23 and 0 - 4 is considered night)
     */
    private void setDayOrNight()
    {
        isDay = currentTime >= 5 && currentTime <= 18;
    }

    /**
     * @return The current hour in the simulation.
     */
    public static int getCurrentTime()
    {
        return currentTime;
    }

    /**
     * @return The current step value
     */
    public static int getStep()
    {
        return step;
    }

    /**
     * @return The isDay field.
     */
    public static boolean getIsDay()
    {
        return isDay;
    }

    /**
     * @return The String containing current time in the HH:MM format.
     */
    private static String getTimeString()
    {
        return getHoursString() + ":" + getMinutesString();
    }

    /**
     * @return The String of hours in 2-digit format.
     */
    private static String getHoursString()
    {
        String hourString = "";
        if (currentTime < 10) {
            hourString += "0" + currentTime;
        }
        else {
            hourString += currentTime;
        }
        return hourString;
    }

    /**
     * @return The String containing minutes in a 2-digit format.
     */
    private static String getMinutesString()
    {
        String minsString = "";
        if (minutes < 10) {
            minsString += "0" + minutes;
        }
        else {
            minsString += minutes;
        }
        return minsString;
    }

    /**
     * @return The time of the day as well as
     *         whether it's daytime or night
     */
    public static String getInfo()
    {
        String returnString = getTimeString() +
                " Day: " + currentDay + " ";
        if (isDay) {
            returnString += "(Day time)";
        }
        else {
            returnString += "(Night time)";
        }
        return returnString;
    }
}
