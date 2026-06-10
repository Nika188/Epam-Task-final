package edu.epam.fop;


import java.time.format.TextStyle;
import java.time.temporal.*;


public class FormatterConfigurerFactory {
  
  private FormatterConfigurerFactory() {}

  public static FormatterConfigurer slangBasedDate() {
      return builder -> {
          builder.appendPattern("d MMM 'of' ");
          builder.appendValueReduced(ChronoField.YEAR, 2, 4, 1931);
      };
  }
  
  public static FormatterConfigurer politeScheduler() {
      return builder -> {
          builder.appendPattern("'Scheduled on' EEEE 'at' h:mm B");
          builder.optionalStart();
          builder.appendPattern(" 'by' zzzz");
          builder.optionalEnd();
      };


  }
  
  public static FormatterConfigurer scientificTime() {
      return builder -> {
          builder.appendPattern("HH:mm:ss");
          builder.appendLiteral('.');
          builder.appendFraction(ChronoField.MICRO_OF_SECOND, 1, 6, false);
      };
  }
  
  public static FormatterConfigurer historicalCalendar() {
      return builder -> {
          builder.appendPattern("y 'of' ");
          builder.appendText(ChronoField.ERA, TextStyle.FULL);
          builder.appendLiteral(" (");
          builder.appendChronologyText(TextStyle.SHORT);
          builder.appendLiteral(")");
      };

  }
}
