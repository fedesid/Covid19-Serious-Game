public class Country {

    int initialPopulation;
    int currentPopulation;
    int infectedPopulation;
    int deadPopulation;

    public Country(String country){

        ReadCountries rc = new ReadCountries("src/countries.txt");

        initialPopulation = rc.countriesStats.get(country);
        currentPopulation = initialPopulation;
        infectedPopulation = 0;
        deadPopulation = 0;

    }

}
