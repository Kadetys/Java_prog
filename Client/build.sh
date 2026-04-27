javac -verbose -d ./build Main.java >> build/build.log &&
cd build/ &&
jar -cvfe Client.jar Client/Main Client/*.class >> build.log &&
rm -rv Client/ >> build.log