package com.etsy.jading;

import java.util.Properties;
import java.util.regex.Matcher;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.operation.Aggregator;
import cascading.operation.Function;
import cascading.operation.aggregator.Count;
import cascading.operation.regex.RegexGenerator;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.scheme.hadoop.TextLine;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.util.Pair;

public class WordCount extends CascadingRunnable {
    @Override
    public Flow[] buildFlows(String[] args, Properties properties) {
        String inputPath = args[0];
        String outputPath = args[1];

        // the 'head' of the pipe assembly
        Pipe assembly = new Pipe("wordcount");
        FlowDef flowDef = FlowDef.flowDef();

        // define source and sink Taps.
        TextLine sourceScheme = new TextLine(new Fields("line"));
        Tap source = new Hfs(sourceScheme, inputPath);
        flowDef.addSource(assembly, source);

        TextLine sinkScheme = new TextLine(new Fields("word", "count"));
        Tap sink = new Hfs(sinkScheme, outputPath, SinkMode.REPLACE);
        flowDef.addSink(assembly, sink);

        // For each input Tuple
        // using a regular expression
        // parse out each word into a new Tuple with the field name "word"
        String regex = "(?<!\\pL)(?=\\pL)[^ ]*(?<=\\pL)(?!\\pL)";
        Function<Pair<Matcher, Tuple>> function = new RegexGenerator(new Fields("word"), regex);
        assembly = new Each(assembly, new Fields("line"), function);

        // group the Tuple stream by the "word" value
        assembly = new GroupBy(assembly, new Fields("word"));

        // For every Tuple group
        // count the number of occurrences of "word" and store result in
        // a field named "count"
        Aggregator<Pair<Long[], Tuple>> count = new Count(new Fields("count"));
        assembly = new Every(assembly, count);

        flowDef.addTail(assembly);

        // tell Hadoop which jar file to use
        FlowConnector.setApplicationJarClass(properties, WordCount.class);

        // plan a new Flow from the assembly using the source and sink Taps
        FlowConnector flowConnector = new HadoopFlowConnector(properties);
        return new Flow[] { flowConnector.connect(flowDef) };
    }
}
