import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Weather
{
    private Map<Integer, String> weatherMap;

    public Weather()
    {
        weatherMap = new HashMap<>();
    }

    /**
     * Initialise the Map, mapping integers
     * to different weather types.
     */
    private void setWeatherList()
    {
        weatherMap.put(0, "Sunny");
        weatherMap.put(1, "Cloudy");
        weatherMap.put(2, "Rainy");
        weatherMap.put(3, "Foggy");
        weatherMap.put(4, "Storm");
    }

    /**
     * Return the desired weather from the HashMap.
     * @param keyInteger The key to weather String.
     * @return The weather of type String.
     */
    public String getWeather(int keyInteger)
    {
        return weatherMap.get(keyInteger);
    }
}
