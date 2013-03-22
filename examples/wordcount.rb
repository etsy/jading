require 'rubygems'
require 'cascading'

input_path = ARGV.shift || (raise 'input_path required')
mode = ARGV.shift # Set to "local" to run locally

puts "Args: input_path=#{input_path}, mode=#{mode}"

word_count_flow = nil
word_count_cascade = cascade 'wordcount', :mode => mode do
  word_count_flow = flow 'wordcount' do
    source 'input', tap(input_path)

    assembly 'input' do
      split_rows 'line', 'word', :pattern => /[.,]*\s+/, :output => 'word'
      group_by 'word' do
        count
      end
      project 'count', 'word'
    end

    sink 'input', tap('output/wordcount', :sink_mode => :replace)
  end
end

# When this script is run directly via JRuby, there is no "runner" to complete
# the wordcount Cascade, so we must do it ourselves
word_count_cascade.complete if word_count_flow.mode.local
