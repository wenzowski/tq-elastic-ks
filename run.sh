eval "java -Xms1024M -Xmx4G -classpath ./lib/$(ls ./lib | awk -v ORS=: '{ print $1 }' | sed 's/\:$//' | sed 's/\:/\:\.\/lib\//g'):./classes/ org.topicquests.ks.ui.Main";
