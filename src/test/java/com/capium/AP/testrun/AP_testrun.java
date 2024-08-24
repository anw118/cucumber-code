package com.capium.AP.testrun;

import org.junit.runner.RunWith;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src\\test\\resources\\AP.features\\AP_login.feature",

           glue="com.capium.AP.step_definations",
           plugin= {"pretty","html:target/cucumber-reports.html","json:target/cucumber-reports.json"},
        		    monochrome=true
		)
public class AP_testrun {
	
	

}
