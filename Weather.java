import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Weather
{
    private static Random rand;
    private static List<String> weatherList;
    private static String currentWeather;

    public Weather()
    {
        rand = new Random();
        weatherList = new ArrayList<>();
        initialiseWeatherList();
        setRandomWeather();
    }

    /**
     * Initialise the Map, mapping integers
     * to different weather types.
     */
    private void initialiseWeatherList()
    {
        weatherList.add("Sunny");
        weatherList.add("Cloudy");
        weatherList.add("Rainy");
        weatherList.add("Foggy");
        weatherList.add("Storm");
    }

    /**
     * Set a random weather in the simulation
     * (out of a list of all the weather types available)
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
    public static String getCurrentWeather()
    {
        return currentWeather;
    }
}
