
/**
 * A subclass of weather for when it's Snowy weather.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public class Snow extends Weather
{
    // current weather name
    private String weatherName;

    /**
     * Constructor for objects of class Snow
     */
    public Snow()
    {
        weatherName = "Snow";
    }

    /**
     * @return The current weather name (i.e., Snow).
     */
    public String getWeatherName()
    {
        return weatherName;
    }
}
