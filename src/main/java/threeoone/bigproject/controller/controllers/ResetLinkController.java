package threeoone.bigproject.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.viewcontrollers.ForgetPasswordController;
import threeoone.bigproject.services.forgotpassword.PasswordResetLinkSenderService;

@Component
@RequiredArgsConstructor
public class ResetLinkController {
    private final PasswordResetLinkSenderService passwordResetLinkSenderService;
    private final ForgetPasswordController forgetPasswordController;

    @Autowired
    private void registerRequestSenders(RequestSender<String> sendResetLinkRequestSender) {
        sendResetLinkRequestSender.registerReceiver(this::sendTo);
    }

    private void sendTo(String username) {
        boolean result = passwordResetLinkSenderService.sendResetLink(username);
        forgetPasswordController.setFlag(result);

    }

}
