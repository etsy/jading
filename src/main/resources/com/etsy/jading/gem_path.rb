# vendor/gems is a peer of com/etsy/jading in the jade.jar
gem_path = File.expand_path(File.join(File.dirname(__FILE__), '..', '..', '..', 'vendor', 'gems'))

puts "Setting GEM_PATH to '#{gem_path}'"
ENV['GEM_PATH'] = gem_path
