package org.m2ci.msp.jtgt.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.m2ci.msp.jtgt.Annotation;
import org.m2ci.msp.jtgt.TextGrid;
import org.m2ci.msp.jtgt.annotation.IntervalAnnotation;

import java.io.IOException;

import org.m2ci.msp.jtgt.tier.IntervalTier;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 *
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public class TextGridSerializerTest {

    @DataProvider
    Object[][] textGrids() {
        return new String[][] {{"tg1.TextGrid"}, {"tg1_crlf.TextGrid"}};
    }

    @Test(dataProvider = "textGrids")
    public void testLoadingTextGridWithoutException(String testResourceName) throws TextGridIOException {
        InputStream input = this.getClass().getResourceAsStream(testResourceName);
        String string_tg = new Scanner(input, "UTF-8").useDelimiter("\\A").next();

        TextGridSerializer tgs = new TextGridSerializer();
        Assert.assertNotNull(tgs.fromString(string_tg));
    }

    @Test(enabled = false)
    public void testDumpingTextGrid() throws IOException {
        String input_resource_name = "tg1.TextGrid";
        InputStream input = this.getClass().getResourceAsStream(input_resource_name);
        String string_input = new Scanner(input, "UTF-8").useDelimiter("\\A").next();
        String validated_resource_name = "tg1_validated.TextGrid";
        InputStream validated = this.getClass().getResourceAsStream(validated_resource_name);
        String string_validated = new Scanner(validated, "UTF-8").useDelimiter("\\A").next();

        // Render the input
        TextGridSerializer tgs = new TextGridSerializer();
        TextGrid tg = tgs.fromString(string_input);

        Assert.assertEquals(tgs.toString(tg), string_validated);
    }

    @Test
    public void testEquals() throws IOException {

        TextGridSerializer tgs = new TextGridSerializer();

        // Input
        String input_resource_name = "tg1.TextGrid";
        InputStream input = this.getClass().getResourceAsStream(input_resource_name);
        String string_input = new Scanner(input, "UTF-8").useDelimiter("\\A").next();
        TextGrid tg_input = tgs.fromString(string_input);


        // Validating
        String validated_resource_name = "tg1_validated.TextGrid";
        InputStream validated = this.getClass().getResourceAsStream(validated_resource_name);
        String string_validated = new Scanner(validated, "UTF-8").useDelimiter("\\A").next();
        TextGrid tg_validated = tgs.fromString(string_validated);


        Assert.assertEquals(tg_validated, tg_input);
    }

    @Test(timeOut = 1000)
    public void testToStringPerformance() throws TextGridIOException {
        // create TextGrid with many intervals
        int xmax = 10000;
        ArrayList<Annotation> intervals = new ArrayList<>();
        for (int i = 0; i < xmax; i++) {
            intervals.add(new IntervalAnnotation(i, i + 1, String.valueOf(i)));
        }
        IntervalTier tier = new IntervalTier("lots", 0, xmax, intervals);
        TextGrid tg = new TextGrid(0, xmax);
        tg.addTier(tier);
        // dump it to String
        Assert.assertNotNull(new TextGridSerializer().toString(tg));
    }
}


/* TextGridSerializerTest.java ends here */
