puts "Found #{Cascading::Cascade.all.size} Cascades in global registry"

# All cascades are run with shared ARGV but copies of the global $jobconf_properties
Cascading::Cascade.all.each do |cascade|
  puts "Jading is running the '#{cascade.name}' Cascade"
  Cascading::Cascade.get(cascade.name).complete
end
