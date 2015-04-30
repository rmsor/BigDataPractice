/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labMapReduce;

/**
 *
 * @author rmsor_000
 */
public class MapFoldMine {
 
    public static int f(int x) {
        return isPrime(x);
    }

    public static int g(int x, int y) {
        if(x==-1) return y;
	if((x==0 && y==1) || (x==1 && y==0)) return y;
	if(x==y || x==2) return 2;
        return 2;
    }

    public static int isPrime(int n) {
        if (n < 2) {
            return 0;
        }
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) {
                return 0;
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        int[] a = {17,17,4,13, 10,11,17};
        int[] b = new int[a.length];
        int[] c = new int[a.length];

        for (int i = 0; i < a.length; i++) {
            b[i] = f(a[i]);
        }

        int x = -1;
        for (int i = 0; i < a.length; i++) {
            x = g(x, b[i]);
            c[i] = x;
        }
        for (int i = 0; i < a.length; i++) {
            System.out.print("\t" + a[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < a.length; i++) {
            System.out.print("\t" + b[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < a.length; i++) {
            System.out.print("\t" + c[i] + " ");
        }
        System.out.println();
    }
}
