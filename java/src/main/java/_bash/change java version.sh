#List all java versions:
update-java-alternatives --list

#Set java version as default (needs root permissions):
sudo update-java-alternatives --set /path/to/java/version
#...where /path/to/java/version is one of those listed by the previous command (e.g. /usr/lib/jvm/java-7-openjdk-amd64).