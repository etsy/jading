puts "Found #{Cascading::Cascade.all.size} Cascades in global registry"

cascade_name = Cascading::Cascade.all.last.name
puts "Jading is running the '#{cascade_name}' Cascade"

cascade = Cascading::Cascade.get(cascade_name)

# $jobconf_properties is bound in com.etsy.jading.Main#run(Properties, String[])
cascade.complete($jobconf_properties)
