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
   * Copy from srcPath to destFolderPath (default is src/main/resources/Document).
   * Rename if necessary (default is same name as copy file)
   *
   * @param srcPath        source Path
   * @param destFolderPath destination Folder Path
   * @param rename         new name for file
   */
  public static void copyFile(String srcPath, String destFolderPath, String rename) {
    if (destFolderPath == null || destFolderPath.isEmpty()) {
      destFolderPath = defaultDestPath;
    }
    try {
      Path sourcePath = Paths.get(srcPath);
      Path destinationFolder = Paths.get(destFolderPath);
      String typeFile = sourcePath.getFileName().toString().split("\\.")[1];
      if(rename == null || rename.isEmpty()) {
        rename = sourcePath.getFileName().toString().split("\\.")[0];
      }
      // Create destination folder if it doesn't exist
      if (Files.notExists(destinationFolder)) {
        Files.createDirectories(destinationFolder);
      }
      Path destinationPath = destinationFolder.resolve(rename + "." + typeFile);
      // Copy the file
      Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
//      Path newPath = destinationFolder.resolve(rename);
//      Files.move(destinationPath, newPath, StandardCopyOption.REPLACE_EXISTING);

    } catch (IOException e) {
      Alerts.showAlertWarning("Error when copy file!", e.getMessage());
    }
  }
}
