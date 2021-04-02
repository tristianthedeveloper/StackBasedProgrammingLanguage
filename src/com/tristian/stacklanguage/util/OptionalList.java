package com.tristian.stacklanguage.util;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * A class that stores optional values.
 * alright frick this gave up on it pointless
 */
public class OptionalList extends ArrayList<Optional<?>> {

    public boolean addVal(Object object) {
        this.add(Optional.of(object));
        return true;
    }
}
