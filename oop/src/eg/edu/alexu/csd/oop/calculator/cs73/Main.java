/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.edu.alexu.csd.oop.calculator.cs73;

import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author youssefali
 */
public class Main {
    public static void main(String[] args){
        JFrame jf = new MainWindow();
        jf.setTitle("Youssef's Simple Calculator");
        jf.setVisible(true);
        /*Scanner in = new Scanner(System.in);
        for(int j=0; j<10; j++){
            String s = in.nextLine();
            String[] x = s.split("\\+|\\*|/|-");
            for(int i=0; i<x.length; i++)
                System.out.println(x[i]);
        }*/
    }
}
