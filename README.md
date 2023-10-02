# Encrypter

This is a handy tool for encrypting a file with a password. The file can then be decrypted or just read 
given a password.

Feel free to fork or reuse the code. I won't enforce copyright on this code.


- [Usage](#usage)
  - [Make an Alias](#alias)
- [Encryption Algorithms](#encryption-algorithms)


## Usage
I this repo is jar file '[enker-1.jar](enker-1.jar)'.
If you have java 17 or higher installed then you can encrypt a file with the following:
```shell
java -jar /Users/path-to-jar-file/enker-1.jar -e myFile.txt
```

Then decrypt with the following
```shell
java -jar /Users/path-to-jar-file/enker-1.jar -d myFile.txt
```

You can also have the file decrypted and output to the console instead of a file
```shell
java -jar /Users/path-to-jar-file/enker-1.jar -r myFile.txt
```

For usage info do run this
```shell
java -jar /Users/path-to-jar-file/enker-1.jar
```
If you're a developer you can also just run the project in your IDE of choice if you prefer.

### Alias
It's often easier to run this with an alias. Here's how to make one on mac:
```shell
vim ~/.bash_profile
```
or
```shell
nano ~/.bash_profile
```

Then add this to '.bash_profile'
```
alias enker='java -jar /Users/path-to-jar-file/enker-1.jar'
```
then run this to load the alias
```shell
source ~/.bash_profile
```
you can then use the service like this:
```shell
enker -r myFile.txt
```




## Encryption Algorithms
Summary:
- AES/GCM (256)
- PBKDF2WithHmacSHA256 (for secret key)

### Details
We first make a hash of the password with a random salt
```java
byte[] salt = new byte[8];
new SecureRandom().nextBytes(salt);

// Derive a secret key from the password using PBKDF2
KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
SecretKey secretKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");
```

We then use AES/GCM encryption to encrypt the file with the hash
```java
// Initialize the cipher for encryption
Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
byte[] iv = new byte[12]; // Use a 12-byte IV for GCM
new SecureRandom().nextBytes(iv);
cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));

// Encrypt the fileContent
byte[] encryptedBytes = cipher.doFinal(fileContent.getBytes(StandardCharsets.UTF_8));
```

We then store the salt and iv with the encrypted data in a new file for you to be used for decryption later.
