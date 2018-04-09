package edu.gatech.a2340.shelterme;


import android.util.Log;

import org.junit.Test;

import edu.gatech.a2340.shelterme.Model.IMessageable;
import edu.gatech.a2340.shelterme.Model.Shelter;
import edu.gatech.a2340.shelterme.Model.UserType;
import edu.gatech.a2340.shelterme.Model.ManagerFacade;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    // Michael Fan Unit Test
    @Test (expected = java.lang.ExceptionInInitializerError.class)
    public void UserManager_validateLoginInput() throws Exception {

        String output;
        IMessageable emptyEmailFailure = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                assertEquals("Email or password cannot be empty", message);
            }
        };
        IMessageable emptyPasswordFailure = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                assertEquals("Email or password cannot be empty", message);
            }
        };
        IMessageable bothFieldsEmptyFailure = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                assertEquals("Email or password cannot be empty", message);
            }
        };

        IMessageable validationSuccess = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                assertEquals(null, message);
            }
        };
        IMessageable dummySuccessCallback = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                Log.v("NOTE", "Success callback");
            }
        };

        ManagerFacade.getInstance().attemptSignIn("email@email.com", "", dummySuccessCallback, emptyPasswordFailure);
        ManagerFacade.getInstance().attemptSignIn("", "password", dummySuccessCallback, emptyEmailFailure);
        ManagerFacade.getInstance().attemptSignIn("", "", dummySuccessCallback, bothFieldsEmptyFailure);
        // because below is valid signin, an exception will be thrown since there's no Firebase instance
        ManagerFacade.getInstance().attemptSignIn("email@email.com", "password", dummySuccessCallback, validationSuccess);
    }

    // Will Said Unit Test
    @Test
    public void UserType_toStringIsCorrect() throws Exception {

        UserType shelter = UserType.SHELTER_OWNER;
        UserType user = UserType.USER;
        UserType anon = UserType.ANONYMOUS;
        UserType admin = UserType.ADMIN;

        assertEquals("Shelter owner", shelter.toString());
        assertEquals("User", user.toString());
        assertEquals("Anonymous", anon.toString());
        assertEquals("Admin", admin.toString());

    }

    // Andrew Hoyt Unit Test
    @Test
    public void Shelter_validateReservationsIsCorrect() throws Exception {
        Shelter shelter = new Shelter(0, "", 7, 1,
                "", "", "", "", "");

        IMessageable onFailure = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                message = "Test";
            }
        };

        assertEquals(true, shelter.validateReservations(1, onFailure));
        assertEquals(false, shelter.validateReservations(2, onFailure));
    }

    // Brandon Shockley Unit Test
    @Test
    public void Shelter_validateReleaseIsCorrect() {
        Shelter shelter = new Shelter(0,"",3,1, "",
                "", "", "", "");
        IMessageable onFailure = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                //Do nothing
            }
        };

        assertEquals(true, shelter.validateRelease(1, onFailure));
        assertEquals(false, shelter.validateRelease(3, onFailure));
    }

    // Kyle Xiao Unit Test
    @Test (expected = java.lang.NoClassDefFoundError.class)
    public void UserManager_validateRegistrationInputIsCorrect() {
        String output;
        IMessageable onDummySuccess = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                //This line should never run for the purposes of this test
                assertEquals(1, 2);
            }
        };
        IMessageable onFailureMismatchPassword = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                assertEquals("Passwords must match", message);
            }
        };
        IMessageable onFailureFieldEmpty = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                assertEquals("All fields must be filled", message);
            }
        };
        IMessageable onFailureShortPassword = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                assertEquals("Password must be at least 8 characters", message);
            }
        };
        IMessageable onPassValidationButFailRegistration = new IMessageable() {
            @Override
            public void runWithMessage(String message) {
                assertEquals("Registration failed", message);
            }
        };
        ManagerFacade.getInstance().attemptRegister("kylepxiao@gmail.com", "cs2340", "password", UserType.ADMIN, onDummySuccess, onFailureMismatchPassword);
        ManagerFacade.getInstance().attemptRegister("", "", "", UserType.ADMIN, onDummySuccess, onFailureFieldEmpty);
        ManagerFacade.getInstance().attemptRegister("kylepxiao@gmail.com", "cs2340", "cs2340", UserType.ADMIN, onDummySuccess, onFailureShortPassword);
        // This line should throw an exception after validation succeeds and ManagerFacade attempts to register this user
        ManagerFacade.getInstance().attemptRegister("kylepxiao@gmail.com", "password", "password", UserType.ADMIN, onDummySuccess, onPassValidationButFailRegistration);
    }

}