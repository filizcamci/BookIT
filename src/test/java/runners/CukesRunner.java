package runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {
				"pretty", //prints information on console in a nice format
				"html:target/default-cucumber-reports", //generates html report every time we run the cukes runner
				"json:target/cucumber.json", //creates json report
				"junit:target/cucumber.xml",
				"rerun:target/rerun.txt"
		
		},
		
		tags="@temp",
		features= { "src/test/resources/features"}, 
		glue= {"step_definitions" }
		//,dryRun = true
		)
public class CukesRunner {

}
