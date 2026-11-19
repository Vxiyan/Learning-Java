package Vxiyan.Events;

import Vxiyan.CritClasses.Event;
import Vxiyan.CritClasses.Player;
import Vxiyan.Utils.Loggy;
import Vxiyan.Utils.Randy;

import java.util.Scanner;

public class biPathEvent extends Event {
    String eventName;

    public biPathEvent(String evNa) {
        if (evNa == null || evNa.strip().equalsIgnoreCase("")) {
            evNa = "Wormhole";
        }
        super(evNa, type.BIPATH);
        this.eventName = evNa;
    }

    @Override
    public void handle(Player player) {
        Object eventData = events.get(eventName);
        if (eventData instanceof String) {
            Loggy.println("[Event] " + eventName + ", would you like to " + eventData + " or not?");
            Scanner scanner = new Scanner(System.in);
            if (scanner.next().strip().equalsIgnoreCase((String) eventData)) {
                Loggy.println("You successfully passed the event.");
            } else {
                Loggy.println("You failed the event and took some damage.");
                player.damage(Randy.randRange(0.0, 15.0));
            }
        } else {
            Loggy.println("Event " + eventName + " failed to process. DATA: " + eventData);
        }
    }
}