package edu.gatech.a2340.shelterme.Model;

/**
 * This interface serves as a callback,
 * allowing the model to pass error
 * information to the controller/view
 * to display as they wish
 */
public interface IMessageable {
    void runWithMessage(String message);
}
