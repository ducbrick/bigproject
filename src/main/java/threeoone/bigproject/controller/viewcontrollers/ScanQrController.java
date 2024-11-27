package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.services.QrImageService;
import threeoone.bigproject.util.Alerts;

/**
 * Test class for QR Scan Online
 *
 * @author HUY1902
 */
@Component
@FxmlView("ScanQr.fxml")
@RequiredArgsConstructor
public class ScanQrController implements ViewController{
  private final QrImageService qrImageService;

  @FXML
  private ImageView qrImage;

  @FXML
  private AnchorPane root;

  /**
   * @return
   */
  @Override
  public Parent getParent() {
    return root;
  }

  /**
   *
   */
  @Override
  public void show() {
    try {
      qrImage.setImage(qrImageService.getNewestQrImage());
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!!!", e.getMessage());
    }
  }
}
