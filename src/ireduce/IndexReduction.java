/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ireduce;

import java.util.ArrayList;
import sun.security.util.Length;

/**
 *
 * @author 984317
 */
public class IndexReduction {

    public static int byteRequiredForInt(int n) {
        int size = 1;
        if ((n >>> 28) != 0) {
            size = 5;
        } else if ((n >>> 21) != 0) {
            size = 4;
        } else if ((n >>> 14) != 0) {
            size = 3;
        } else if ((n >>> 7) != 0) {
            size = 2;
        }

        return size;
    }

    public static byte[] encodeInt(int n) {
        int size = byteRequiredForInt(n);

        byte[] code = new byte[size];

        for (int i = 0; i < size; i++) {
            code[i] = (byte) (n & 0x0000007F);
            n = n >>> 7;
        }
        byte mask = (byte) 0x80;
        code[0] = (byte) (code[0] | mask);

        return code;
    }

    public static int decodeInt(byte[] code) {
        byte mask = (byte) 0x7F;
        code[0] = (byte) (code[0] & mask);

        int n = 0;
        for (int i = code.length - 1; i >= 0; i--) {
            n = n << 7;
            n = (n | code[i]);
        }
        return n;
    }

    public static int[] degap(int[] a) {
        int[] degap = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            if (i % 2 == 0 && i != 0) {
                degap[i] = a[i] - a[i - 2];
            } else {
                degap[i] = a[i];
            }
        }
        return degap;
    }
    public static int[] degapReverse(int[] a) {
        int[] degap = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            if (i % 2 == 0 && i != 0 && i+2<a.length) {
                degap[i] = a[i] + a[i + 2];
            } else {
                degap[i] = a[i];
            }
        }
        return degap;
    }

    public static ArrayList<ArrayList<Byte>> encode(int[] a) {
        ArrayList<ArrayList<Byte>> arrByte = new ArrayList();
        for (int i = 0; i < a.length; i++) {
            byte[] eByte = encodeInt(a[i]);
            ArrayList<Byte> inside = new ArrayList();
            for (int j = 0; j < eByte.length; j++) {
                inside.add(eByte[j]);
            }
            arrByte.add(inside);
        }
        return arrByte;
    }

    public static int[] decode(ArrayList<ArrayList<Byte>> code) {
        ArrayList<Integer> ints = new ArrayList();
        for (int i = 0; i < code.size(); i++) {
            byte[] newInn = new byte[code.get(i).size()];
            for (int j = 0; j < code.get(i).size(); j++) {
                newInn[j] = code.get(i).get(j);
            }
            Integer x = decodeInt(newInn);
            ints.add(x);

        }
        int[] intsA = new int[ints.size()];
        for (int i = 0; i < ints.size(); i++) {
            intsA[i] = ints.get(i);
        }
        return intsA;
    }

    public static String toStringByte(byte b) {
        String str = "";
        for (int i = 0; i < 8; i++) {
            str += (b < 0) ? "1" : "0";
            b = (byte) (b << 1);
        }
        return str;
    }

    public static void printArr(int[] a) {
        String str = "";
        for (int i = 0; i < a.length; i++) {
            str += "\t" + a[i];
        }
        System.out.println(str);
    }

    public static void main(String[] args) {
        int[] plist = {100, 8, 150, 7, 300, 24, 500, 36};
        // you can add whatever you want
        printArr(plist);
        //apply degap
        int[] degap = degap(plist);
        //
        printArr(degap);
        //
        ArrayList<ArrayList<Byte>> arrByte = encode(degap);
        String str = "\t";
        for (int i = 0; i < arrByte.size(); i++) {
            for (int j = 0; j < arrByte.get(i).size(); j++) {
                str += toStringByte(arrByte.get(i).get(j)) + " ";
            }

        }
        System.out.println(str);

        int[] output = decode(arrByte);
        
        printArr(output);
         //apply degapreverse
        int[] degapR = degapReverse(output);
        //
        printArr(degapR);
    }
}
