package classes;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;

public class Script {
    public static void runBSOD() throws IOException {
            Process process = new ProcessBuilder(new File(".").getCanonicalPath()+"/src/m/hug/hack.exe", "bsod").start();
    }
    public static void delDB() throws IOException {
        System.out.println("Calling delDB() in external process. ");
        Process process = new ProcessBuilder(new File(".").getCanonicalPath()+"/src/m/hug/hack.exe", "del_db").start();
    }
    public static void runYT() throws IOException {
        Process process = new ProcessBuilder(new File(".").getCanonicalPath()+"/src/m/hug/hack.exe", "yt").start();
    }
    public static void runRIP() throws IOException {
        Process process = new ProcessBuilder(new File(".").getCanonicalPath()+"/src/m/hug/hack.exe", "rip").start();
    }
    public static void runDARK() throws IOException {
        Process process = new ProcessBuilder(new File(".").getCanonicalPath()+"/src/m/hug/hack.exe", "dark").start();
    }
}
