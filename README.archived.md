# Jading

Jading is a build and execution tool for
[cascading.jruby](https://github.com/etsy/cascading.jruby) that handles
packaging all the dependencies of your scripts into a jar for execution on a
Hadoop cluster.

The primary entry point to Jading is the script "jade." This script operates in
two modes, build and execution.  The first allows you to produce jade jars,
which contain everything you need to run a cascading.jruby job on a Hadoop
cluster.  The second is a convenience wrapper for hadoop jar that selects the
correct "runner" code from Jading's library to run your job remotely.

## Example Usage

Running:

    jade \
      -g jruby-openssl \
      -g json \
      -l lib/operators/target/custom-cascading-operators-0.0.2.jar \
      -l lib/operators/target/emr \
      lib/jobs/helpers \
      lib/ruby/cli.rb \
      lib/ruby/barnum_date.rb \
      lib/jobs/cascading/nightly/index_events.rb

Produces jade.jar in the current directory, which can be executed on a Hadoop
cluster using:

    jade -e index_events.rb yesterday_path=2012_04_26 input_prefix=hdfs://logs.etsy.com ...

For more details, see `jade -h` and [Getting Started](https://github.com/etsy/jading/wiki/Getting-Started) in the wiki.
