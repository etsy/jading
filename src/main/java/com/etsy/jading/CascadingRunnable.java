package com.etsy.jading;

import java.util.Properties;

import cascading.cascade.CascadeConnector;
import cascading.flow.Flow;

/**
 * CascadingRunnables can be executed using the Jading runner Main class. Your
 * implementation must take configuration and return an array of Flows, which
 * will then be planned together into a Cascade and executed.
 */
public abstract class CascadingRunnable implements Runnable {
    private String name;
    private Flow[] flows;

    /**
     * Method that accepts configuration and produces an array of Flows which
     * will be planned together into a Cascade at run time.
     * 
     * @param args Array of string arguments to your application
     * @param properties Properties that should be used when connecting Flows in
     *            your application
     * @return Array of Flows connected in your application
     */
    protected abstract Flow[] buildFlows(String[] args, Properties properties);

    public void prepare(String name, String[] args, Properties properties) {
        this.name = name;
        flows = buildFlows(args, properties);
    }

    @Override
    public void run() {
        new CascadeConnector().connect(name, flows).complete();
    }
}
