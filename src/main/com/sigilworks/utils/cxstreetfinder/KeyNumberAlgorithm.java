package main.com.sigilworks.utils.cxstreetfinder;

/**
 * User: tim
 * Date: 10/18/16
 * Time: 10:41 PM
 */

import java.util.function.Function;

public abstract class KeyNumberAlgorithm implements Function<Integer, Integer> {
    protected abstract int getKeyNumber();

    protected int adjust(int addressNumber, int rawAddressNumber) {
        return addressNumber;
    }

    protected int preprocess(int addressNumber) {
        // drop last digit, divide by two
        return (int) Math.floor(addressNumber / 10) / 2;
    }

    @Override
    public Integer apply(Integer addressNumber) {
        return adjust(preprocess(addressNumber), addressNumber) + getKeyNumber();
    }
}