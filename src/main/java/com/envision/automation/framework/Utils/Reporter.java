package com.envision.automation.framework.Utils;

public class Reporter {
    public  static void logPassedStep(String message){
        ExtentTestManager.getTest().log(LogStatus.PASS,message);

    }
    public static void logFailedStep(String message) {
        ExtentTestManager.getTest().log(LogStatus.FAIL,message);
    }
}
