# This is a vanilla cascading job that helps us test the "runner" Main class.

require 'rubygems'
require 'cascading'

output_path = ARGV.shift

cascade 'test_runner' do
  flow 'test_runner' do
    source 'this_file', tap(__FILE__, :scheme => text_line_scheme('line'))

    assembly 'this_file' do
      pass
    end

    sink 'this_file', tap(output_path, :scheme => text_line_scheme, :sink_mode => :replace)
  end
end
