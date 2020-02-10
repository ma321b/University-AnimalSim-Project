public class TimeTrack
{
    // step field from simulator in here
    // also all other time related methods.
    // make a new object of this class in constructor of simulator class

    private static int step;
    private static int currentTime;
    private static boolean isDay;

    public TimeTrack()
    {
        step = 0;
        currentTime = 0;
        isDay = false;
    }

    /**
     * Increments the currentTime in the simulation.
     */
    private void incrementCurrentTime()
    {
        if (currentTime < 23) {
            currentTime++;
        }
        else {
            // Rollback
            currentTime = 0;
        }
    }

    /**
     * Set the current time in simulation
     * according to the step of the simulation
     * we're currently in. An hour is 250 steps
     * long in our simulation.
     */
    public void setCurrentTime()
    {
        if (step % 250 == 0 && step != 0) {
            incrementCurrentTime();
        }
        setDayOrNight();
    }

    /**
     * Set the value of step.
     * @param stepValue the value to which the value of step
     *                  should be set.
     */
    public static void setStep(int stepValue)
    {
        step = stepValue;
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
}
