package threeoone.bigproject.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.LendingDetail;
import threeoone.bigproject.services.persistence.LendingPersistenceService;
import threeoone.bigproject.util.Alerts;

import java.util.NoSuchElementException;

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
  private void registerRequestReceiver(RequestSender<LendingDetail> saveNewLending,
                                       RequestSender<Integer> deleteLending) {
    saveNewLending.registerReceiver(this::saveNewLending);
    deleteLending.registerReceiver(this::deleteLendingById);
  }

  /**
   * Saves the new lending detail by delegating the operation to the lending persistence service.
   *
   * @param newLending the new lending detail to be saved
   */
  public void saveNewLending(LendingDetail newLending) {
    try {
      lendingPersistenceService.lend(newLending.member().getId(), newLending.document().getId());
    } catch (NoSuchElementException e) {
      Alerts.showAlertWarning("Error", "This document has no physical copy available at the moment");
    }
  }

  public void deleteLendingById(int ID) {
    lendingPersistenceService.delete(ID);
  }
}
