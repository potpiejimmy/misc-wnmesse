CD /d %~dp0
call ant release
"c:\Program Files\Java\jdk6\bin\jarsigner.exe" -verbose -sigalg MD5withRSA -digestalg SHA1 -keystore wnmesse.keystore -storepass wnmesse -keypass wnmesse bin\TheSoftwareKitchen-release-unsigned.apk wnmesse
zipalign -f -v 4 bin\TheSoftwareKitchen-release-unsigned.apk bin\TheSoftwareKitchen_%1.apk
copy bin\TheSoftwareKitchen_%1.apk c:\jboss-7.1.1\welcome-content\
pscp -pw wnmessetemppw bin\TheSoftwareKitchen_%1.apk liese@potpiejimmy.de:/srv/www/vhosts/potpiejimmy.de/httpdocs/download