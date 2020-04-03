package ecutb.fishingtrip.exception;

import java.util.function.Supplier;

public class ExceptionsSupply {
    public static final Supplier<AppResourceNotFoundException> USER_NOT_FOUND = () -> new AppResourceNotFoundException("Requested user could not be found");
}
