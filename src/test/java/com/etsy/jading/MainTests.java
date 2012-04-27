package com.etsy.jading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

public class MainTests {
    @Test(expected = IllegalArgumentException.class)
    public void testArgs() throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        Main.main(new String[] {});
    }

    @Test
    public void testCascadingJRubyJob() throws IOException, java.net.URISyntaxException, ClassNotFoundException,
            IllegalAccessException, InstantiationException {
        File testJob = new File(this.getClass().getClassLoader().getResource("com/etsy/jading/test_runner.rb").toURI());
        assertTrue("Expected test job in script called: " + testJob, testJob.isFile());

        File testOutputDir = new File("/tmp/jading/build/test-output/testCascadingJRubyJob");
        Main.main(new String[] { "com/etsy/jading/test_runner.rb", testOutputDir.getAbsolutePath() });

        File testOutput = new File(testOutputDir.getAbsolutePath() + "/part-00000");
        assertTrue("Expected test job output in file called: " + testOutput, testOutput.isFile());

        assertEquals("Expected test job to duplicate itself into its output", readFile(testJob), readFile(testOutput));
    }

    @Test
    public void testCascadingJob() throws IOException, java.net.URISyntaxException, ClassNotFoundException,
            IllegalAccessException, InstantiationException {
        File testInput = new File(this.getClass().getClassLoader().getResource("com/etsy/jading/test_runner.rb").toURI());
        assertTrue("Expected test job in script called: " + testInput, testInput.isFile());

        File testOutputDir = new File("/tmp/jading/build/test-output/testCascadingJob");
        Main.main(new String[] { "com.etsy.jading.WordCount", testInput.getAbsolutePath(),
                testOutputDir.getAbsolutePath() });

        File testOutput = new File(testOutputDir.getAbsolutePath() + "/part-00000");
        assertTrue("Expected test job output in file called: " + testOutput, testOutput.isFile());

        // Spot check output by making sure every do has an end
        String output = readFile(testOutput);
        assertTrue(output.contains("do\t3"));
        assertTrue(output.contains("end\t3"));
    }

    private String readFile(File file) throws IOException {
        byte[] buffer = new byte[(int) file.length()];
        BufferedInputStream f = null;
        try {
            f = new BufferedInputStream(new FileInputStream(file));
            f.read(buffer);
        } finally {
            if (f != null) {
                f.close();
            }
        }

        return new String(buffer);
    }
}
