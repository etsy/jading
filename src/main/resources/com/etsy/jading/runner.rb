puts "Found #{Cascading::Cascade.all.size} Cascades in global registry"

# All cascades are run with shared properties and ARGV
Cascading::Cascade.all.each do |cascade|
  puts "Jading is running the '#{cascade.name}' Cascade"
  cascade = Cascading::Cascade.get(cascade.name)

  # $jobconf_properties is bound in com.etsy.jading.Main#run(Properties, String[])
  cascade.complete($jobconf_properties)
end
