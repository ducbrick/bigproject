package threeoone.bigproject.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IllegalCharacterFilterServiceTest {
  private final IllegalCharacterFilterService filterService = new IllegalCharacterFilterService();

  @Test
  @DisplayName("Only digits")
  public void onlyDigits() {
    assertThat(filterService.hasIllegalCharacter("028349209412984")).isFalse();
  }

  @Test
  @DisplayName("Only alphabet characters")
  public void onlyAlphabet() {
    assertThat(filterService.hasIllegalCharacter("sdjkhfaskjdhfoqwiucsk")).isFalse();
    assertThat(filterService.hasIllegalCharacter("KASJDFAKJFASDJKFUJF")).isFalse();
    assertThat(filterService.hasIllegalCharacter("jKHytGJHHyGJHHGkjh")).isFalse();
  }

  @Test
  @DisplayName("Both alphabet and digits")
  public void bothAlphabetAndDigits() {
    assertThat(filterService.hasIllegalCharacter("82392fjwdkfsdn3984rkjn")).isFalse();
    assertThat(filterService.hasIllegalCharacter("896uTFGJhb67rtygJH65TYGHJB5YTGH")).isFalse();
  }

  @Test
  @DisplayName("NULL input")
  public void nullInput() {
    assertThatThrownBy(() -> filterService.hasIllegalCharacter(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

}