# Jading

Jading is a script and library used to package
[cascading.jruby](https://github.com/etsy/cascading.jruby) scripts for
execution via hadoop jar.

## Example Usage

Running:
    jade \
      -g jruby-openssl \
      -g json \
      -l lib/operators/target/custom-cascading-operators-0.0.2.jar \
      -l lib/operators/target/emr \
      lib/jobs/helpers \
      lib/ruby/fdo.rb \
      lib/jobs/cascading/nightly/index_events.rb

Produces jade.jar in the current directory, which can be executed on a Hadoop
cluster using:
    hadoop jar jade.jar com.etsy.jading.Main index_events.rb yesterday_path=2012_04_26 input_prefix=hdfs://logs.etsy.com ...

For more details, see `jade -h`.
