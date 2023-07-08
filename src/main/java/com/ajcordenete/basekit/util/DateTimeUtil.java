package com.ajcordenete.basekit.util;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class DateTimeUtil {

    public Date getCurrentDateFromInstant() {
        return Date.from(getCurrentInstant());
    }

    public Date getFutureDateFromNow(long toAdd, ChronoUnit unit) {
        return Date.from(getCurrentInstant().plus(toAdd, unit));
    }

    private Instant getCurrentInstant() {
        return Instant.now().truncatedTo(ChronoUnit.SECONDS);
    }


}
