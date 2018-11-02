# BSUIRCryptoLab3
BSUIR Cryptography lab work – MD5/SHA1 implementation

MD5 implementation based on [RosettaCode](http://rosettacode.org/wiki/MD5/Implementation#Kotlin) implementation with some additions.

SHA-1 implementation based on [this code](https://github.com/daveho/CloudCoder/blob/master/CloudCoderModelClasses/src/org/cloudcoder/app/shared/model/SHA1.java)
with some modifications and conversion to Kotlin.

To see this project in action, run ```./gradlew run``` or ```./gradlew.bat run```

To add options for app, pass args to gradle via: ```./gradlew run --args="-key value"```. 
You can see list of the available command line options below:

 * -a, or -algorithm – hashing algorithm, either MD5 or SHA1. Defaults to MD5
 * -f or -file – path to file with clear text to encrypt