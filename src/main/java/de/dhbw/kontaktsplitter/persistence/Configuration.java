package de.dhbw.kontaktsplitter.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.models.Title;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static de.dhbw.kontaktsplitter.parser.InputParser.*;

/**
 * The Class for the saving and loading the patterns
 *
 * @author Nico Dreher
 */
public class Configuration {

    /**
     * The path of the configuration folder
     */
    public static final String CONFIG_PATH = System.getProperty("user.home") + "/Kontaktsplitter-Kings/config.json";
    /**
     * The available languages
     */
    private static final List<String> LANGUAGES = Arrays.stream(Locale.getISOLanguages())
            .map(Locale::new)
            .map(Locale::getDisplayLanguage)
            .collect(Collectors.toList());
    /**
     * The known prefixes and suffixes for surnames
     */
    private static final List<String> prefixesAndSuffixes = List.of("van", "von", "zu", "vom");
    /**
     * The academic and non academic titles
     */
    private static List<Title> titles;
    /**
     * The available patterns to parse the salutations
     */
    private static List<ContactPattern> patterns;
    /**
     * The name database and the gender
     */
    private static Map<String, Gender> names;

    static {
        initialize();
    }

    /**
     * Generates the default titles and patterns
     * Loads the names from the names.txt
     */
    private static void initialize() {
        titles = new ArrayList<>();
        titles.add(new Title("Professor"));
        titles.add(new Title("Prof."));
        titles.add(new Title("Dr. h.c. mult."));
        titles.add(new Title("Dr.-Ing."));
        titles.add(new Title("Dr. rer. nat."));
        titles.add(new Title("Dr."));
        titles.add(new Title("Dipl. Ing."));
        titles.add(new Title("Dipl."));

        patterns = new ArrayList<>();
        patterns.add(new ContactPattern("Deutsch", Gender.MALE, "Herr " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Sehr geehrter Herr " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Deutsch", Gender.FEMALE, "Frau " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Sehr geehrte Frau " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));


        patterns.add(new ContactPattern("Englisch", Gender.FEMALE, "Mrs " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Dear Mrs " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Englisch", Gender.FEMALE, "Ms " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Dear Ms " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Englisch", Gender.MALE, "Mr " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Dear Mr " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Englisch", Gender.FEMALE, "Mrs. " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Dear Mrs " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Englisch", Gender.FEMALE, "Ms. " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Dear Ms " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Englisch", Gender.MALE, "Mr. " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Dear Mr " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));


        patterns.add(new ContactPattern("Italienisch", Gender.FEMALE,
                "Signora " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Gentile Signora " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(
                new ContactPattern("Italienisch", Gender.MALE, "Sig. " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                        "Egregio Signora " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));

        patterns.add(
                new ContactPattern("Französisch", Gender.FEMALE, "Mme " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                        "Madame " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Französisch", Gender.MALE, "M " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Monsieur " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(
                new ContactPattern("Französisch", Gender.FEMALE, "Mme. " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                        "Madame " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Französisch", Gender.MALE, "M. " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Monsieur " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));


        patterns.add(
                new ContactPattern("Spanisch", Gender.FEMALE, "Señora " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                        "Estimada Señora " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Spanisch", Gender.MALE, "Señor " + TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Estimada Señor " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));

        patterns.add(new ContactPattern("Deutsch", Gender.NONE, TITLE + " " + LAST_NAME + ", " + FIRST_NAME,
                "Sehr geehrte*r " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Deutsch", Gender.NONE, TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Sehr geehrte*r " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Deutsch", Gender.DIVERS, TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Sehr geehrte*r " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));

        patterns.add(
                new ContactPattern("Englisch", Gender.NONE, TITLE + " " + FIRST_NAME + " " + LAST_NAME, "Dear Sirs"));
        patterns.add(
                new ContactPattern("Englisch", Gender.DIVERS, TITLE + " " + FIRST_NAME + " " + LAST_NAME, "Dear Sirs"));

        patterns.add(new ContactPattern("Italienisch", Gender.NONE, TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Egregi Signori"));
        patterns.add(new ContactPattern("Italienisch", Gender.DIVERS, TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Egregi Signori"));

        patterns.add(new ContactPattern("Französisch", Gender.NONE, TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Messieursdames"));
        patterns.add(new ContactPattern("Französisch", Gender.DIVERS, TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Messieursdames"));

        patterns.add(new ContactPattern("Spanisch", Gender.NONE, TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Estimados Señores y Señoras"));
        patterns.add(new ContactPattern("Spanisch", Gender.DIVERS, TITLE + " " + FIRST_NAME + " " + LAST_NAME,
                "Estimados Señores y Señoras"));

        names = new HashMap<>();
        try(InputStream stream = Configuration.class.getResourceAsStream("/names/names.txt");
                InputStreamReader isr = new InputStreamReader(stream);
                BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while((line = reader.readLine()) != null) {
                line = line.trim();
                if(line.startsWith("M") || line.startsWith("F") || line.startsWith("?")) {
                    String[] parts = line.split("\\s+");
                    if(parts.length > 2) {
                        Gender gender;
                        switch(parts[0]) {
                            case "M": //Intended Fall through
                            case "?M":
                                gender = Gender.MALE;
                                break;
                            case "F":
                            case "?F":
                                gender = Gender.FEMALE;
                                break;
                            case "?":
                                gender = names.getOrDefault(parts[1], Gender.NONE);
                                break;
                            default:
                                gender = null;
                                break;
                        }
                        if(gender != null) {
                            names.put(parts[1].toLowerCase(), gender);
                        }
                    }
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Title> getTitles() {
        return new ArrayList<>(titles);
    }

    public static void setTitles(List<Title> newTitles) {
        titles = newTitles;
    }

    public static List<ContactPattern> getPatterns() {
        return new ArrayList<>(patterns);
    }

    public static void setPatterns(List<ContactPattern> newPatterns) {
        patterns = newPatterns;
    }

    /**
     * Checks if the given name is a known forename
     *
     * @param name The name to check
     * @return True if the name is in the list of known forenames
     */
    public static boolean isFirstName(String name) {
        return !getPrefixesAndSuffixes().contains(name.toLowerCase()) && names.containsKey(name.toLowerCase());
    }

    /**
     * Get the gender of a forename
     *
     * @param name
     * @return The gender or {@link Gender#NONE} if the name is unknown
     */
    public static Gender getGender(String name) {
        return names.getOrDefault(name.toLowerCase(), Gender.NONE);
    }

    public static List<String> getLanguages() {
        return new ArrayList<>(LANGUAGES);
    }

    public static List<String> getPrefixesAndSuffixes() {
        return new ArrayList<>(prefixesAndSuffixes);
    }

    /**
     * Get all known names
     *
     * @return
     */
    public static Set<String> getNames() {
        return new HashSet<>(names.keySet());
    }

    /**
     * Loads the patterns and titles from the config
     */
    public static void loadConfig() {
        if(new File(CONFIG_PATH).exists()) {
            try(FileReader reader = new FileReader(new File(CONFIG_PATH))) {
                ConfigFile file = new Gson().fromJson(reader, ConfigFile.class);
                titles = file.getTitles();
                patterns = file.getPatterns();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Configuration.saveConfig();
        }
    }

    /**
     * Saves the patterns und titles to the configuration
     */
    public static void saveConfig() {
        new File(CONFIG_PATH).getParentFile().mkdirs();
        try(FileWriter writer = new FileWriter(new File(CONFIG_PATH))) {
            ConfigFile file = new ConfigFile(getTitles(), getPatterns());
            new GsonBuilder().setPrettyPrinting().create().toJson(file, writer);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
