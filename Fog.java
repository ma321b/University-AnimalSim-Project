
/**
 * A subclass of weather for when it's foggy weather.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public class Fog extends Weather
{
    // current weather name
    private String weatherName;

    /**
     * Constructor for objects of class Fog
     */
    public Fog()
    {
        weatherName = "Fog";
    }

    /**
     * @return The current weather name (i.e., fog).
     */
    public String getWeatherName()
    {
        return weatherName;
    }
}
