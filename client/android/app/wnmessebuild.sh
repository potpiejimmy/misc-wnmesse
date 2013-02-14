#!/bin/sh
cd $(dirname $0)
ant release
jarsigner -verbose -sigalg MD5withRSA -digestalg SHA1 -keystore wnmesse.keystore -storepass wnmesse -keypass wnmesse bin/TheSoftwareKitchen-release-unsigned.apk wnmesse
/Users/Shared/android-sdk-mac_x86/tools/zipalign -f -v 4 bin/TheSoftwareKitchen-release-unsigned.apk bin/TheSoftwareKitchen.apk
cp bin/TheSoftwareKitchen.apk /Users/Shared/jboss-7.1.1/welcome-content/
