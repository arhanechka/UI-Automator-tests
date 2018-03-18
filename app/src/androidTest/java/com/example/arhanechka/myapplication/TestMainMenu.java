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
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestMainMenu {
    public static final String DOMAIN = "dev-14";
    public static final String LOGIN = "ivlink";
    public static final String PASSWORD = "1111";
    private static UiDevice mDevice;
    private static UiCollection  mainParent;

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
        if(allAppsButton.exists()) {
            allAppsButton.clickAndWaitForNewWindow();
        }
        //Check if "Enter Pin" page is active
        UiObject enterPin = mDevice.findObject(new UiSelector().textContains("Enter Pin"));
        // Delay for searching
        Thread.sleep(10000);
        // Call method if "Enter Pin" exists
        if (enterPin.exists()) {
            returnToLoginPage();
        }
        // otherwise call login page method
        loginPage();
        mainParent = new UiCollection( new UiSelector().className("android.widget.RelativeLayout"));
    }

    @org.junit.Test
    public void ifMainMenuExists() throws UiObjectNotFoundException {
        UiObject mainWindow = mainParent.getChild(new UiSelector().className("android.widget.LinearLayout"));
        int count = mainWindow.getChildCount();
        //Assert that main Window contains a list with main menu buttons
        Assert.assertTrue(count>0);
        }


    @org.junit.Test
    public void ifMainMenuContainsAllLines() throws UiObjectNotFoundException {
        UiObject mainWindow = mainParent.getChild(new UiSelector().className("android.widget.LinearLayout"));
        int count = mainWindow.getChildCount();
        //Assert that main Window contains four line with main menu buttons
        Assert.assertEquals(count, 4);
    }

    @org.junit.Test
    public void ifMainMenuRegisterButtonExists() throws UiObjectNotFoundException {
        //Assert that main Window contains menu "Register"
        Assert.assertTrue(mDevice.findObject(new UiSelector().textContains("Register")).exists());
    }

    @org.junit.Test
    public void ifMainMenuManageButtonExists() throws UiObjectNotFoundException {
        //Assert that main Window contains menu "Manage"
        Assert.assertTrue(mDevice.findObject(new UiSelector().textContains("Manage")).exists());
    }
    @org.junit.Test
    public void ifMainMenuMedicalButtonExists() throws UiObjectNotFoundException {
        //Assert that main Window contains menu "Medical"
        Assert.assertTrue(mDevice.findObject(new UiSelector().textContains("Medical")).exists());
    }

    @org.junit.Test
    public void ifMainMenuLiveFeedButtonExists() throws UiObjectNotFoundException {
        //Assert that main Window contains menu "Live Feed"
        Assert.assertTrue(mDevice.findObject(new UiSelector().textContains("Live")).exists());
    }
    @org.junit.Test
    public void ifBottomMenuExists() throws UiObjectNotFoundException {
        int numberOfChildren = mainParent.getChildCount();
        System.out.println(numberOfChildren);
        //Assert, that main Window contains main and bottom menus
        Assert.assertEquals(3, numberOfChildren);
    }

    public void ifBottomMenuHasCorrectStructure() throws UiObjectNotFoundException {
        UiObject bottomMenu = mainParent.getChildByInstance(new UiSelector().className("android.widget.LinearLayout"),2);
        int count = bottomMenu.getChildCount();
        Assert.assertEquals(5, count);
        Assert.assertTrue(bottomMenu.getChild(new UiSelector().className("android.widget.ImageView")).exists());
    }

    public static void loginPage() throws UiObjectNotFoundException {
        // Search domain field
        UiObject domain = mDevice.findObject(new UiSelector().textContains("Domain"));
        if(domain.exists()) {
        domain.clearTextField();
        domain.setText(DOMAIN);
        }
        // Search username field
        UiObject username = mDevice.findObject(new UiSelector().textContains("Username"));
        if(username.exists()) {
            username.clearTextField();
            username.setText(LOGIN);
        }
        // Search password field
        UiObject rel = mDevice.findObject(new UiSelector().className("android.widget.RelativeLayout"));
        UiObject frame = rel.getChild(new UiSelector().className("android.widget.FrameLayout"));
        UiObject password = frame.getChild(new UiSelector().className("android.widget.EditText"));
        if( password.exists()) {
            password.setText(PASSWORD);
        }
        // Search login button and go to next page
        UiObject button = mDevice.findObject(new UiSelector().textStartsWith("Login"));
        button.clickAndWaitForNewWindow();
    }

    public static void returnToLoginPage() throws UiObjectNotFoundException {
       // Search arrow which opens "Login with password" menu
        UiObject parent = mDevice.findObject(new UiSelector().className("android.widget.RelativeLayout"));
        UiObject image = parent.getChild(new UiSelector().className("android.widget.ImageView"));
        image.clickAndWaitForNewWindow();
        UiObject returnToLogin = mDevice.findObject(new UiSelector().textContains("Login with password"));
        returnToLogin.clickAndWaitForNewWindow();
    }
}
