package com.example.arhanechka.myapplication;

/**
 * Created by Arhanechka on 3/18/2018.
 */

import android.os.RemoteException;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestOverviewMenu {
    public static final String DOMAIN = "dev-14";
    public static final String LOGIN = "ivlink";
    public static final String PASSWORD = "1111";
    private static UiDevice mDevice;
    private static UiCollection mainParent;

    @BeforeClass
    public static void beforeClass() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());
        assertThat(mDevice, notNullValue());
        mDevice.wakeUp();
        // Start from the home screen
        mDevice.pressHome();
        // Search for program
        UiObject allAppsButton = mDevice.findObject(new UiSelector().textStartsWith("Baby"));
        assertNotNull(allAppsButton);
        if (allAppsButton.exists()) {
            allAppsButton.clickAndWaitForNewWindow();
        }
        //Check if "Enter Pin" page is active
        UiObject enterPin = mDevice.findObject(new UiSelector().textContains("Enter Pin"));
        // Delay for searching
        Thread.sleep(5000);
        // Call method if "Enter Pin" exists
        if (enterPin.exists()) {
            returnToLoginPage();
        }
        // otherwise call login page method
        loginPage();
        // Search register button in main menu and pass to Register page
        mainMenu();
        // Search Qverview button in Register page and pass to Overview
        register();
        //Search for main Parent for all tests
        mainParent = new UiCollection(new UiSelector().className("android.support.v7.widget.RecyclerView"));
    }

    @org.junit.Test
    public void ifListOfMenuExists() throws UiObjectNotFoundException {
        int count = mainParent.getChildCount();
        //Assert that window contains a list menus with list
        Assert.assertTrue(count > 0);
    }

    @org.junit.Test
    public void ifAlthoughOneOfParticipantsExists() throws UiObjectNotFoundException {
        UiObject firstParticipant = mainParent.getChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 2);
        //Assert that window contains a list of participants
        Assert.assertTrue(firstParticipant.exists());
    }

    @org.junit.Test
    public void ifFirstParticipantElementContainsNecessaryFields1() throws UiObjectNotFoundException {
        UiObject week = mDevice.findObject(new UiSelector().textContains("Week"));
        //Assert that participant has a necessary field
        Assert.assertTrue(week.exists());
    }

    @org.junit.Test
    public void ifFirstParticipantElementContainsNecessaryFields() throws UiObjectNotFoundException {
        UiObject lastReg = mDevice.findObject(new UiSelector().textContains("Register"));
        boolean is = lastReg.exists();
        //Assert that participant has a necessary field
        Assert.assertTrue(is);
    }

    public static void loginPage() throws UiObjectNotFoundException {
        // Search domain field
        UiObject domain = mDevice.findObject(new UiSelector().textContains("Domain"));
        if (domain.exists()) {
            domain.clearTextField();
            domain.setText(DOMAIN);
        }
        // Search username field
        UiObject username = mDevice.findObject(new UiSelector().textContains("Username"));
        if (username.exists()) {
            username.clearTextField();
            username.setText(LOGIN);
        }
        // Search password field
        UiObject rel = mDevice.findObject(new UiSelector().className("android.widget.RelativeLayout"));
        UiObject frame = rel.getChild(new UiSelector().className("android.widget.FrameLayout"));
        UiObject password = frame.getChild(new UiSelector().className("android.widget.EditText"));
        if (password.exists()) {
            password.setText(PASSWORD);
        }
        // Search login button and go to next page
        UiObject button = mDevice.findObject(new UiSelector().textStartsWith("Login"));
        button.clickAndWaitForNewWindow();
    }

    public static void returnToLoginPage() throws UiObjectNotFoundException {
        UiObject parent = mDevice.findObject(new UiSelector().className("android.widget.RelativeLayout"));
        boolean c = parent.exists();
        System.out.println(c);
        UiObject image = parent.getChild(new UiSelector().className("android.widget.ImageView"));
        boolean e = image.exists();
        image.clickAndWaitForNewWindow();
        UiObject returnToLogin = mDevice.findObject(new UiSelector().textContains("Login with password"));
        returnToLogin.clickAndWaitForNewWindow();
    }

    public static void mainMenu() throws UiObjectNotFoundException {
        UiObject register = mDevice.findObject(new UiSelector().textContains("Register"));
        if (register.exists()) {
            UiObject result = register.getFromParent(new UiSelector().className("android.widget.ImageView"));
            if (result.exists()) {
                result.clickAndWaitForNewWindow();
            }
        }
    }

    public static void register() throws UiObjectNotFoundException {
        UiObject overview = mDevice.findObject(new UiSelector().textContains("Overview"));
        if (overview.exists()) {
            UiObject result = overview.getFromParent(new UiSelector().className("android.widget.ImageView"));
            if (result.exists()) {
                result.clickAndWaitForNewWindow();
            }
        }
    }
}
