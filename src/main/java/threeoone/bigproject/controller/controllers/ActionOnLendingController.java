package threeoone.bigproject.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.LendingDetail;
import threeoone.bigproject.services.persistence.LendingPersistenceService;

/**
 * The {@code ActionOnLendingController} class handles actions related to lending operations.
 * It is responsible for saving new lending details through the lending persistence service.
 *
 * @author HUY1902
 */
@Component
@RequiredArgsConstructor
public class ActionOnLendingController {
  private final LendingPersistenceService lendingPersistenceService;

  /**
   * Registers the request receiver for saving new lending details.
   *
   * @param saveNewLending the request sender for saving new lending details
   */
  @Autowired
  private void registerRequestReceiver(RequestSender<LendingDetail> saveNewLending) {
    saveNewLending.registerReceiver(this::saveNewLending);
  }

  /**
   * Saves the new lending detail by delegating the operation to the lending persistence service.
   *
   * @param newLending the new lending detail to be saved
   */
  public void saveNewLending(LendingDetail newLending) {
    lendingPersistenceService.lend(newLending.member().getId(), newLending.document().getId());
  }
}
