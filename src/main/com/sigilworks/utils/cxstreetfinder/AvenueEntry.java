package main.com.sigilworks.utils.cxstreetfinder;

import com.google.common.collect.Range;

/**
 * User: tim
 * Date: 10/18/16
 * Time: 10:41 PM
 */
public class AvenueEntry {
    private final String description;
    private final Range<Integer> addressRange;
    private final KeyNumberAlgorithm algo;

    AvenueEntry(String description, Range<Integer> addressRange, KeyNumberAlgorithm algo) {
        this.description = description;
        this.addressRange = addressRange;
        this.algo = algo;
    }

    public boolean isApplicableTo(int addressNumber) {
        return addressRange.contains(addressNumber);
    }

    public Integer getCrossStreet(int addressNumber) {
        return algo.apply(addressNumber);
    }
}
