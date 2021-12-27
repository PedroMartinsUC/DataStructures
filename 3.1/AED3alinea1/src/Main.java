import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        long[] array = ler_inputs();
        long[][] tree = fazer_arvore(array);
        imprimir(tree);
    }

    public static long hashcode(long x) {
        return x % 1000000007;
    }

    public static long hashcode(long x, long y) {
        int mod = 1000000007;
        return ((x % mod) + (y % mod)) % mod;
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream));
            tokenizer = null;
        }
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
        public long nextLong() {
            return Long.parseLong(next());
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static long[] ler_inputs(){
        InputReader in = new InputReader(System.in);
        int size = in.nextInt();
        long[] array = new long[size];
        if (size > 0 && ((size & (size - 1)) == 0)) {
            for (int i = 0; i < size; i++) {
                array[i] = in.nextLong();
            }
        } else {
            System.out.println("Numero de transacoes tem de ser potencia de 2");
            System.exit(-1);
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
