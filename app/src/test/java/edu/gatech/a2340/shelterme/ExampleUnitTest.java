package edu.gatech.a2340.shelterme;

import org.junit.Test;

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




}