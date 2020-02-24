import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The abstract class for weathers in the simulation.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public abstract class Weather
{
    private static Random rand;
    private static Weather currentWeather;
    private static ArrayList<Weather> weatherList = new ArrayList<>();

    public Weather()
    {
        rand = new Random();
    }
    
    /**
     * Add the weather to the weather list.
     * @param weather The weather object to be added.
     */
    public static void addWeather(Weather weather)
    {
        weatherList.add(weather);
    }

    /**
     * Set the currentWeather to some weather object
     * @param weather The Weather to be assigned.
     */
    public static void setWeather(Weather weather)
    {
        currentWeather = weather;
    }
    
    /**
     * Set a random weather in the simulation
     * (from a list of all the weather types available).
     */
    private static void setRandomWeather()
    {
        int randomNum = rand.nextInt(weatherList.size());
        currentWeather = weatherList.get(randomNum);
    }

    /**
     * Changes the current weather and sets a
     * random weather randomly, after every 10 steps in the simulation
     * (i.e., not always, so as to implement an entirely random behaviour).
     */
    public static void changeWeather()
    {
        if (TimeTrack.getStep() % 100 == 0) {
            if (rand.nextBoolean()) {
                setRandomWeather();
            }
        }
    }

    /**
     * @return The current weather in the simulation
     */
    public static Weather getCurrentWeather()
    {
        return currentWeather;
    }

    /**
     * Abstract method to get the weatherName of current weather
     * @return The current weather name.
     */
    public abstract String getWeatherName();
}
