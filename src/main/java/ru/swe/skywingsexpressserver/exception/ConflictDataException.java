package ru.swe.skywingsexpressserver.exception;

/**
 * Исключение, выбрасываемое при возникновении конфликтов.
 */
public class ConflictDataException extends RuntimeException {
  /**
   * Создает новое исключение с указанным сообщением об ошибке.
   *
   * @param message сообщение об ошибке, описывающее причину исключения.
   */
  public ConflictDataException(String message) {
    super(message);
  }
}
