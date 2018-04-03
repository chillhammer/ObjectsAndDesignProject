package edu.gatech.a2340.shelterme;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.a2340.shelterme.Model.IMessageable;
import edu.gatech.a2340.shelterme.Model.Shelter;
import edu.gatech.a2340.shelterme.Model.UserType;

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



}