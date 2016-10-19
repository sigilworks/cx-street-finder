package main.com.sigilworks.utils.cxstreetfinder;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Range;

import java.util.List;

/**
 * User: tim
 * Date: 10/18/16
 * Time: 10:34 PM
 */
public class CrossStreetFinder {
        private static final ImmutableListMultimap<String, AvenueEntry> ALL_AVENUES = ImmutableListMultimap.<String, AvenueEntry>builder()
            .put("1", new AvenueEntry("1st Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +3; } }))
            .put("2", new AvenueEntry("2nd Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +3; } }))
            .put("3", new AvenueEntry("3rd Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +10; } }))
            .put("4", new AvenueEntry("4th Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +8; } }))
            .put("5", new AvenueEntry("5th Ave (up to 200)", Range.atMost(200), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +13; } }))
            .put("5", new AvenueEntry("5th Ave (200-400)", Range.closedOpen(200, 400), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +16; } }))
            .put("5", new AvenueEntry("5th Ave (400-600)", Range.closedOpen(400, 600), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +18; } }))
            .put("5", new AvenueEntry("5th Ave (600-775)", Range.closedOpen(600, 775), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +20; } }))
            .put("5", new AvenueEntry("5th Ave (775-1286)", Range.closedOpen(775, 1286), new KeyNumberAlgorithm() {
                // do not divide by 2
                @Override protected int preprocess(int addressNumber) { return (int) Math.floor(addressNumber / 10); }
                @Override protected int getKeyNumber() { return -18; }
            }))
            .put("5", new AvenueEntry("5th Ave (1286-1500)", Range.closedOpen(1286, 1500), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +45; } }))
            .put("5", new AvenueEntry("5th Ave (1500-2000)", Range.greaterThan(1500), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +24; } }))
            .put("6", new AvenueEntry("6th Ave (Ave of the Americas)", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return -12; } }))
            .put("7", new AvenueEntry("7th Ave (below 110th St)", Range.atMost(1000), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +12; } }))
            .put("7", new AvenueEntry("7th Ave (above 110th St)", Range.greaterThan(1800), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +20; } }))
            .put("8", new AvenueEntry("8th Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +10; } }))
            .put("9", new AvenueEntry("9th Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +13; } }))
            .put("10", new AvenueEntry("10th Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +14; } }))
            .put("Amsterdam", new AvenueEntry("Amsterdam Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +60; } }))
            .put("Audubon", new AvenueEntry("Audubon Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +165; } }))
            .put("Broadway", new AvenueEntry("Broadway (above 23rd St)", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return -30; } }))
            .put("Central Park W", new AvenueEntry("Central Park West", Range.<Integer>all(), new KeyNumberAlgorithm() {
                // divide full number by 10
                @Override protected int preprocess(int addressNumber) { return (int) Math.floor(addressNumber / 10) / 10; }
                @Override protected int getKeyNumber() { return +10; }
            }))
            .put("Columbus", new AvenueEntry("Columbus Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +60; } }))
            .put("Convent", new AvenueEntry("Convent Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +127; } }))
            .put("Lenox", new AvenueEntry("Lenox Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +110; } }))
            .put("Lexington", new AvenueEntry("Lexington Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +22; } }))
            .put("Madison", new AvenueEntry("Madison Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +26; } }))
            .put("Park", new AvenueEntry("Park Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +35; } }))
            .put("Park Ave S", new AvenueEntry("Park Ave South", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +8; } }))
            .put("Riverside", new AvenueEntry("Riverside Drive", Range.<Integer>all(), new KeyNumberAlgorithm() {
                // divide full number by 10
                @Override protected int preprocess(int addressNumber) { return (int) Math.floor(addressNumber / 10) / 10; }
                @Override protected int getKeyNumber() { return +72; }
            }))
            .put("St Nicholas", new AvenueEntry("St Nicholas Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +110; } }))
            .put("West End", new AvenueEntry("West End Ave", Range.<Integer>all(), new KeyNumberAlgorithm() { @Override protected int getKeyNumber() { return +60; } }))
        .build();

    public String crossStreetFor(String address) {
        String avenue = getCanonicalAvenue(address);
        int addressNumber = extractAddressNum(address);
        AvenueEntry avenueEntry = findAvenueEntry(avenue, addressNumber);

        if (avenueEntry == null)
            throw new RuntimeException("No avenue entry found for " + avenue);

        Integer cxStreet = avenueEntry.getCrossStreet(addressNumber);
        if (cxStreet == null)
            throw new RuntimeException(String.format("No valid cross street for %d %s Ave", addressNumber, address));

        return formatCrossStreet(cxStreet);
    }

    private AvenueEntry findAvenueEntry(String canonicalAvenue, int addressNumber) {
        List<AvenueEntry> avenueEntries = ALL_AVENUES.get(canonicalAvenue);
        for (AvenueEntry entry : avenueEntries)
            if (entry.isApplicableTo(addressNumber))
                return entry;
        return null;
    }

    private int extractAddressNum(String address) {
        String[] parts = address.split("\\s+");
        String segment = parts[0];
        String tmp = "";

        for (char c : segment.toCharArray()) {
            if (Character.isDigit(c))
                tmp += c;
        }
        return Integer.parseInt(tmp);
    }

    private String getCanonicalAvenue(String address) {
        String segment = address.substring(address.indexOf(' ') + 1);
        String[] parts = segment.split("\\s+");
        StringBuilder sb = new StringBuilder();

        // TODO: alias check for Ave of Americas, Park Ave S, Central Park West

        for (String part : parts)
            if (!part.startsWith("Av")) {
                sb.append(sb.length() == 0 ? "" : " ");
                // numerical avenues
                if (Character.isDigit(part.charAt(0))) {
                    for (char c : segment.toCharArray()) {
                        if (Character.isDigit(c))
                            sb.append(c);
                    }
                }
                // named avenues
                else
                    sb.append(part);
            }
        return sb.toString().trim();
    }

    private String getOrdinalSuffix(int number) {
        int j = number % 10, k = number % 100;
        if (j == 1 && k != 11) return "st";
        if (j == 2 && k != 12) return "nd";
        if (j == 3 && k != 13) return "rd";
        return "th";
    }

    private String formatCrossStreet(int cxStreet) {
        String suffix = getOrdinalSuffix(cxStreet);
        return String.format("%d%s Street", cxStreet, suffix);
    }

    public static void main(String[] args) {
        final CrossStreetFinder finder = new CrossStreetFinder();
        // log(finder.crossStreetFor("568 Amsterdam Ave"));
        System.out.println(finder.crossStreetFor("2300 7th Ave"));
    }
}
