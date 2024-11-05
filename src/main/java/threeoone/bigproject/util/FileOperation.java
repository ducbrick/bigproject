package threeoone.bigproject.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Util for file operation
 *
 * @author HUY1902
 */
public class FileOperation {
  private static final String defaultDestPath = "src/main/resources/Document";

  /**
   * Copy from srcPath to destFolderPath (default is src/main/resources/Document)
   *
   * @param srcPath        source Path
   * @param destFolderPath destination Folder Path
   */
  public static void copyFile(String srcPath, String destFolderPath) {
    if (destFolderPath == null || destFolderPath.isEmpty()) {
      destFolderPath = defaultDestPath;
    }
    try {
      Path sourcePath = Paths.get(srcPath);
      Path destinationFolder = Paths.get(destFolderPath);
      // Create destination folder if it doesn't exist
      if (Files.notExists(destinationFolder)) {
        Files.createDirectories(destinationFolder);
      }
      Path destinationPath = destinationFolder.resolve(sourcePath.getFileName());
      // Copy the file
      Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      Alerts.showAlertWarning("Error when copy file!", e.getMessage());
    }
  }
}
