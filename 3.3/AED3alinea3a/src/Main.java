import java.util.*;

class Cliente{
    String nome;
    String morada;
    int compras;

    public Cliente(String a, String b, int c){
        this.nome = a;
        this.morada = b;
        this.compras = c;
    }

    public void atualizarCompras(int a){
        this.compras += a;
        System.out.println("AQUISIÇÃO INSERIDA");
    }
}

class Ponto{
    Cliente pessoa;
    Ponto esquerda;
    Ponto direita;

    public Ponto(Cliente a){
        this.pessoa = a;
        esquerda = null;
        direita = null;
    }
}

class Arvore{
    Ponto raiz;

    public Arvore(){
        this.raiz = null;
    }

    public void adicionar(Cliente cliente){
        raiz = adicionar_recursivo(cliente, raiz);
    }

    public Ponto adicionar_recursivo(Cliente cliente, Ponto atual){
        if (atual == null){
            atual = new Ponto(cliente);
            System.out.println("NOVO CLIENTE INSERIDO");
            return atual;
        }

        if (cliente.nome.compareTo(atual.pessoa.nome) < 0) {
            atual.esquerda = adicionar_recursivo(cliente, atual.esquerda);
        } else if (cliente.nome.compareTo(atual.pessoa.nome) > 0) {
            atual.direita = adicionar_recursivo(cliente, atual.direita);
        }
        if (cliente.nome.equals(atual.pessoa.nome)){
            System.out.println("CLIENTE JÁ EXISTENTE");
        }
        return atual;
    }

    public void nova_aquisicao(String nome, int compras){
        nova_aquisicao_recursivo(nome, compras, raiz);
    }

    public void nova_aquisicao_recursivo(String nome, int compras, Ponto atual){
        try {
            if (nome.compareTo(atual.pessoa.nome) < 0) {
                consultar_recursivo(nome, atual.esquerda);
            } else if (nome.compareTo(atual.pessoa.nome) > 0) {
                consultar_recursivo(nome, atual.direita);
            } else {
                atual.pessoa.atualizarCompras(compras);
                System.out.println("AQUISICAO INSERIDA");
            }
        } catch (NullPointerException e){
            System.out.println("CLIENTE NÃO REGISTADO.");
        }
    }

    public void apresentar(){
        apresentar_recursivo(raiz);
    }

    public void apresentar_recursivo(Ponto atual){
        if (atual != null) {
            apresentar_recursivo(atual.esquerda);
            System.out.println(atual.pessoa.nome + " " + atual.pessoa.morada + " " + atual.pessoa.compras);
            apresentar_recursivo(atual.direita);
        }
    }

    public void consultar(String nome){
        consultar_recursivo(nome, raiz);
    }

    public void consultar_recursivo(String nome, Ponto atual){
        try {
            if (nome.compareTo(atual.pessoa.nome) < 0) {
                consultar_recursivo(nome, atual.esquerda);
            } else if (nome.compareTo(atual.pessoa.nome) > 0) {
                consultar_recursivo(nome, atual.direita);
            } else {
                System.out.println(atual.pessoa.nome + " " + atual.pessoa.morada + " " + atual.pessoa.compras);
            }
        } catch (NullPointerException e){
            System.out.println("CLIENTE NÃO REGISTADO.");
        }

    }

    public void remover(){
        raiz = null;
        System.out.println("LISTAGEM DE CLIENTES APAGADA");
    }
}

public class Main {

    public static void main(String[] args) {
        Arvore arvore = new Arvore();
        ler_inputs(arvore);

    }

    public static void ler_inputs(Arvore arvore){
        Scanner sc = new Scanner(System.in);
        while(true) {
            String s = sc.nextLine();
            String[] s1 = s.split(" ");
            if (s1[0].equals("ACRESCENTA")) {
                Cliente cliente = new Cliente(s1[1], s1[2], Integer.parseInt(s1[3]));
                arvore.adicionar(cliente);
            } else if (s1[0].equals("NOVA_AQUISICAO)")){
                arvore.nova_aquisicao(s1[1], Integer.parseInt(s1[2]));
            } else if (s1[0].equals("CONSULTA")) {
                arvore.consultar(s1[1]);
            } else if (s1[0].equals("LISTA")) {
                arvore.apresentar();
                System.out.print("FIM\n");
            } else if (s1[0].equals("APAGA")) {
                arvore.remover();
            } else{
                System.exit(-1);
            }
        }
    }

}
