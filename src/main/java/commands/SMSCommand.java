package commands;


import events.Event;
import game.User;

public class SMSCommand{
    NotificationDetails details;
    String link;
    String templateId;
    String template;

    public SMSCommand(Event event) {
        this.details = new NotificationDetails(event.getUser(), event.getMessage());
    }

    public User getReceiver() {
        return details.getReceiver();
    }

    public String getMessage() {
        return details.getMessage();
    }
}

