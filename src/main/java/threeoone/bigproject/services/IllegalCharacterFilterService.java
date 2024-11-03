package threeoone.bigproject.services;

import org.springframework.stereotype.Service;

/**
 * A service to check for any illegal characters in User's loginName and password.
 * This class is a singleton bean in Spring context.
 *
 * @see threeoone.bigproject.entities.User
 * @author DUCBRICK
 */
@Service
public class IllegalCharacterFilterService {
  /**
   * Check for illegal character in a String.
   * This method checks if the input String only contains alphabet characters and/or digits.
   *
   * @param s the String to check
   *
   * @throws IllegalArgumentException when input String is NULL
   *
   * @return whether input String contains illegal characters
   */
  public boolean hasIllegalCharacter(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Attempting to check for illegal characters in a NULL String");
    }

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);

      if (Character.isAlphabetic(c)) {
        continue;
      }

      if (Character.isDigit(c)) {
        continue;
      }

      return true;
    }

    return false;
  }
}
