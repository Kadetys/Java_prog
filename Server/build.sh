javac -verbose -d ./build Integral.java Main.java RecIntegral.java SimpleGUI.java >> build/build.log &&
cd build/ &&
jar -cvfe Server.jar Server/Main Server/*.class >> build.log &&
rm -rv Server/ >> build.log