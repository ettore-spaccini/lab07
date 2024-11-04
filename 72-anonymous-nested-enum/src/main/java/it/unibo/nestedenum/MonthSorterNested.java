package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    private enum Month{
        JANUARY (31),
        FEBRUARY (28), 
        MARCH (31), 
        APRIL (30),
        MAY (31),
        JUNE (30),
        JULY (31),
        AUGUST (31),
        SEPTEMBER (30),
        OCTOBER (31),
        NOVEMBER (30),
        DECEMBER (31);

        private final int monthDays; 

        private Month (final int monthDays) {
            this.monthDays = monthDays; 
        }

        public static Month fromString (final String monthName) {
            Objects.requireNonNull(monthName); 
            try {
                return Month.valueOf(monthName); 
            } catch (IllegalArgumentException e) {
                Month matchableMonth = null; 
                for (Month month: Month.values()) {
                    if (month.toString().toLowerCase(Locale.ROOT).startsWith(monthName.toLowerCase(Locale.ROOT))) {
                        if (matchableMonth != null) {
                            throw new IllegalArgumentException(monthName + " is ambiguos: both " + matchableMonth + " and " + month + " are matchable with it", e); 
                        }
                        matchableMonth = month; 
                    }
                }
                if (matchableMonth == null) {
                    throw new IllegalArgumentException(monthName + " is unmatchable!", e); 
                }
                return matchableMonth; 
            }
        }

    }

    private static final class SortByMonthOrder implements Comparator<String>{

        @Override
        public int compare(final String o1, final String o2) {
            return Month.fromString(o1).compareTo(Month.fromString(o2)); 
        }
        
        
    }

    private static final class SortByDate implements Comparator<String>{

        @Override
        public int compare(final String o1, final String o2) {
            final var m1 = Month.fromString(o1);
            final var m2 = Month.fromString(o2); 
            return Integer.compare(m1.monthDays, m2.monthDays); 
        }
        
    }

}