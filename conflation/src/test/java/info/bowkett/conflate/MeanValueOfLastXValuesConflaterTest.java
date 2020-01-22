package info.bowkett.conflate;

import info.bowkett.ticking.Price;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeanValueOfLastXValuesConflaterTest {
  @Test
  void ensureItCanCalculateTheMeanOfThreeValuesForTheSameValue(){
    final MeanValueOfLastXValuesConflater conflater = new
      MeanValueOfLastXValuesConflater(3);

    conflater.addValue(new Price("GB123456789", 1.2, 1.4, 1.3));
    conflater.addValue(new Price("GB123456789", 1.2, 1.4, 1.3));
    conflater.addValue(new Price("GB123456789", 1.2, 1.4, 1.3));
    final Price conflatedValue = conflater.getConflatedValue();
    final Price expectedValue = new Price("GB123456789", 1.2, 1.4, 1.3);
    assertEquals(expectedValue, conflatedValue);
  }

  @Test
  void ensureItCorrectlyCalculatesTheMeanOfThreeValues(){
    final MeanValueOfLastXValuesConflater conflater = new
      MeanValueOfLastXValuesConflater(3);

    conflater.addValue(new Price("GB123456789", 1.0, 1.4, 1.2));
    conflater.addValue(new Price("GB123456789", 2.2, 5.4, 3.8));
    conflater.addValue(new Price("GB123456789", 5.3, 5.6, 5.5));
    final Price conflatedValue = conflater.getConflatedValue();
    final Price expectedValue = new Price("GB123456789", 2.8333333333333335,
      4.133333333333334,
      3.5);
    assertEquals(expectedValue, conflatedValue);
  }

  @Test
  void ensureItWillOnlyCalculateMeanOfLastTwoValuesEvenWhenThreeAreGiven(){
    final MeanValueOfLastXValuesConflater conflater = new
      MeanValueOfLastXValuesConflater(2);

    conflater.addValue(new Price("GB123456789", 1.0, 1.4, 1.2));
    conflater.addValue(new Price("GB123456789", 2.2, 5.4, 3.8));
    conflater.addValue(new Price("GB123456789", 5.3, 5.6, 5.5));
    final Price conflatedValue = conflater.getConflatedValue();
    final Price expectedValue = new Price("GB123456789", 3.75,
      5.5,
      4.65);
    assertEquals(expectedValue, conflatedValue);

  }
}
