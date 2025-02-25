package services;

import commands.SMSCommand;
import game.User;

public class SMSService {
    private void sendSMS(User user, String message) {
        // todo: SMS is sent here
    }

    public void send(SMSCommand command) {
        sendSMS(command.getReceiver(), command.getMessage());
    }

}
