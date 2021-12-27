import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Random;

public class Main3{
    public static void main(String[] args) {
        long[] array = ler_inputs();
        long inicio = System.nanoTime();
        long[][] tree = fazer_arvore(array);
        long fim = System.nanoTime();
        long tempo = (fim - inicio);
        System.out.println(tempo + " ns.");
        //imprimir(tree);
    }

    public static long hashcode(long x) {
        return x % 1000000007;
    }

    public static long hashcode(long x, long y) {
        int mod = 1000000007;
        return ((x % mod) + (y % mod)) % mod;
    }

    public static long[] ler_inputs(){
        int size = 33554432;
        long[] array = new long[size];
        Random rand = new Random();
        String up = "999999999999";
        String down = "000000000000";
        long uplong = Long.parseLong(up);
        long downlong = Long.parseLong(down);

        for (int i = 0; i < size; i++) {
            array[i] = downlong+((long)(rand.nextDouble()*(uplong-downlong)));
        }
        return array;
    }

    public static int log2(int N){
        return (int)(Math.log(N) / Math.log(2));
    }

    public static long[][] fazer_arvore(long[] array){
        long[][] tree = new long[log2(array.length) + 1][];
        int marcador = 0;

        for (int i = 0; i < log2(array.length) + 1; i++){
            long[] ramo = new long[array.length / (int)(Math.pow(2,i))];
            for (int j = 0; j < Math.pow(2,(log2(array.length)-i)); j++){
                if(i == 0){
                    ramo[j] = hashcode(array[j]);
                } else {
                    ramo[j] = hashcode(tree[i-1][marcador], tree[i-1][marcador + 1]);
                    marcador += 2;
                }
            }
            tree[i] = ramo;
            marcador = 0;
        }

        return tree;
    }

    public static void imprimir(long[][] tree){
        for (int i = tree.length - 1; i >= 0; i--){
            for (int j = 0; j < tree[i].length; j++){
                System.out.printf("%d\n", tree[i][j]);
            }
        }
    }
}
