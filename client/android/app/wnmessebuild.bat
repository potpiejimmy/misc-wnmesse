call ant release
"c:\Program Files\Java\jdk6\bin\jarsigner.exe" -verbose -sigalg MD5withRSA -digestalg SHA1 -keystore wnmesse.keystore -storepass wnmesse -keypass wnmesse bin\TheSoftwareKitchen-release-unsigned.apk wnmesse
zipalign -v 4 bin\TheSoftwareKitchen-release-unsigned.apk bin\TheSoftwareKitchen-release.apk