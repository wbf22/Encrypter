package bf.encrypter;

import bf.encrypter.core.service.EncrypterService;
import bf.encrypter.core.service.EncrypterServiceImpl;
import bf.encrypter.core.service.exception.EncrypterException;

import java.io.IOException;
import java.util.Scanner;

import static bf.encrypter.common.TerminalUtil.print;

public class Encrypter {

    public static void main(String[] args) {

        String mode = (args.length > 0)?  args[0] : "-h";
        String path = (args.length > 1)?  args[1] : null;
        String password = (args.length > 2)?  args[2] : null;

//        String mode = "-e";
//        String path = "/Users/brandon.fowler/Documents/KNOT/notes/thinkingDoodles";
//        String password = "p";

//        String mode = "-r";
//        String path = "/Users/brandon.fowler/Documents/KNOT/notes/thinkingDoodles.enc";
//        String password = null;

        try {
            switch (mode) {
                case "-h" -> help();
                case "-help" -> help();
                case "--help" -> help();
                case "-r" -> readFile(path, password);
                case "-d" -> decryptFile(path, password);
                case "-e" -> encryptFile(path, password);
                default -> print("Unrecognized mode: " + mode);
            }
        } catch (EncrypterException e) {
            print("FAILURE: check file path and password");
            print("Exception: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static void help() {
        String helpMessage =
        """
        HELP
        
            Helpful utility for encrypting and decrypting files. Also
            provides handy option for reading an encrypted file without
            decrypting to a new file.
                
            usage: enker [-rde mode] [path] [password]
            
            
            examples:
                + encrypt file:          enker -e ./myFile.txt
                + read encrypted file:   enker -r ./myFile.txt
                + decrypt file:          enker -d ./myFile.txt.enc
                
                
        """;

        print(helpMessage);
    }


    public static void readFile(String path, String password) throws IOException {
        print("READ");

        Scanner scanner = new Scanner(System.in);
        if (path == null) {
            print("File path: ");
            path = scanner.nextLine();
        }

        if (password == null) {
            print("File password: ");
            password = scanner.nextLine();
        }


        print("Decrypting, make take a minute...");
        EncrypterService encrypterService = new EncrypterServiceImpl();
        String contents = encrypterService.readFile(password, path);

        print();
        print(contents);
        print();
        print("***************************************************************************************");
        print("WARNING: make sure to close this terminal so that your password and data is not exposed");
        print("***************************************************************************************");
        print();
    }

    public static int countLines(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        String[] lines = text.split("\r\n|\r|\n");
        return lines.length;
    }

    private static void clearLines(int lines) {

        print("Hit enter to clear");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        // Move cursor up five lines (ESC[5A), clear the line (ESC[K), and return to the beginning of the line (ESC[G)
        print("\u001B[" + lines + "A");
        for (int i = 0; i < lines; i++) {
            print();
        }
        print("Cleared terminal output");
    }


    public static void decryptFile(String path, String password) {
        print("DECRYPT");

        Scanner scanner = new Scanner(System.in);
        if (path == null) {
            print("File path: ");
            path = scanner.nextLine();
        }

        if (password == null) {
            print("File password: ");
            password = scanner.nextLine();
        }

        String destinationFile = path.split(".enc")[0];
        print("Decrypt into " + destinationFile + "?(y/n)");
        if (scanner.nextLine().equals("n")) {
            print("New file name: ");
            destinationFile = scanner.nextLine();
        }

        print("Decrypting, make take a minute...");
        EncrypterService encrypterService = new EncrypterServiceImpl();
        encrypterService.decryptFile(password, path, destinationFile);
        print(destinationFile);
    }


    public static void encryptFile(String path, String password) {
        print("ENCRYPT");

        Scanner scanner = new Scanner(System.in);
        if (path == null) {
            print("File path: ");
            path = scanner.nextLine();
        }

        if (password == null) {
            print("File password: ");
            password = scanner.nextLine();
        }


        String destinationPath = path;
        if (path.endsWith(".enc")) {
            print("REENCRYPT");
            print("Assuming '" + path + "' has been encrypted before from '.enc' ending");
        }
        else {
            destinationPath += ".enc";
        }


        print("Encrypting, make take a minute...");
        EncrypterService encrypterService = new EncrypterServiceImpl();
        encrypterService.encryptFile(password, path, destinationPath);
        print(destinationPath);
    }




}
