import java.util.*;

public class Main {

    static class Cliente{
        String nome;
        String morada;
        int compras;

        public Cliente(String a, String b, int c){
            this.nome = a;
            this.morada = b;
            this.compras = c;
        }

        public Cliente(String a){
            this.nome = a;
        }

        public void atualizarCompras(int a){
            this.compras += a;
            System.out.println("AQUISICAO INSERIDA");
        }

        public boolean checkAvenida(){
            String[] s = morada.split(" ");
            if (s[0].toUpperCase().equals("RUA") || s[0].toUpperCase().equals("AVENIDA")){
                return true;
            } else {
                return false;
            }
        }

        public String toString(){
            String[] s = morada.split(" ");
            return String.format("%s %s %s %d", nome, s[0], s[1], compras);
        }
    }

    static class Ponto{
        Cliente pessoa;
        Ponto esquerda;
        Ponto direita;

        public Ponto(Cliente a){
            this.pessoa = a;
            esquerda = null;
            direita = null;
        }
    }

    static class Arvore{
        Ponto raiz;

        public Arvore(){
            this.raiz = null;
        }

        public void adicionar(Cliente cliente){
            raiz = adicionar_ordenado(cliente, raiz);
        }

        public Ponto adicionar_ordenado(Cliente cliente, Ponto atual){

            if (!cliente.checkAvenida()){
                return null;

            } else {

                if (atual == null) {
                    System.out.println("NOVO CLIENTE INSERIDO");
                    return new Ponto(cliente);
                }

                atual = puxar(atual, cliente);

                if (atual.pessoa.nome.equals(cliente.nome)) {
                    System.out.println("CLIENTE JA EXISTENTE");
                    return atual;
                }

                Ponto novo = new Ponto(cliente);

                if (atual.pessoa.nome.compareTo(cliente.nome) < 0) {
                    novo.esquerda = atual;
                    novo.direita = atual.direita;
                    atual.direita = null;

                } else {
                    novo.direita = atual;
                    novo.esquerda = atual.esquerda;
                    atual.esquerda = null;

                }

                System.out.println("NOVO CLIENTE INSERIDO");
                return novo;

            }
        }


        public Ponto puxar(Ponto atual, Cliente cliente) {
            if (atual == null || atual.pessoa.nome.equals(cliente.nome)){
                return atual;
            }

            else if (cliente.nome.compareTo(atual.pessoa.nome) > 0) {
                if (atual.direita == null){
                    return atual;
                }

                if (atual.direita.pessoa.nome.compareTo(cliente.nome) > 0) {
                    atual.direita.esquerda = puxar(atual.direita.esquerda, cliente);

                    if (atual.direita.esquerda != null) {
                        atual.direita = rodardireita(atual.direita);
                    }

                }
                else if (atual.direita.pessoa.nome.compareTo(cliente.nome) < 0) {
                    atual.direita.direita = puxar(atual.direita.direita, cliente);
                    atual = rodaresquerda(atual);

                }

                if (atual.direita != null){
                    raiz = rodaresquerda(atual);
                    return raiz;
                } else {
                    return atual;
                }

            } else {
                if (atual.esquerda == null){
                    return atual;
                }

                if (atual.esquerda.pessoa.nome.compareTo(cliente.nome) > 0) {
                    atual.esquerda.esquerda = puxar(atual.esquerda.esquerda, cliente);
                    atual = rodardireita(atual);

                } else if (atual.esquerda.pessoa.nome.compareTo(cliente.nome) < 0) {
                    atual.esquerda.direita = puxar(atual.esquerda.direita, cliente);

                    if (atual.esquerda.direita != null) {
                        atual.esquerda = rodaresquerda(atual.esquerda);
                    }
                }

                if (atual.esquerda != null){
                    raiz = rodardireita(atual);
                    return raiz;
                } else {
                    return atual;
                }
            }
        }

        public Ponto rodardireita(Ponto atual){
            Ponto root = atual.esquerda;
            Ponto direita = root.direita;

            root.direita = atual;
            atual.esquerda = direita;

            return root;
        }

        public Ponto rodaresquerda(Ponto atual){
            Ponto root = atual.direita;
            Ponto direita = root.esquerda;

            root.esquerda = atual;
            atual.direita = direita;

            return root;
        }

        public void nova_aquisicao(String nome, int compras){
            nova_aquisicao_recursivo(nome, compras, raiz);
        }

        public void nova_aquisicao_recursivo(String nome, int compras, Ponto atual){
            try {
                if (nome.compareTo(atual.pessoa.nome) < 0) {
                    nova_aquisicao_recursivo(nome, compras, atual.esquerda);
                } else if (nome.compareTo(atual.pessoa.nome) > 0) {
                    nova_aquisicao_recursivo(nome, compras, atual.direita);
                } else {
                    atual.pessoa.atualizarCompras(compras);
                }
            } catch (NullPointerException e){
                System.out.println("CLIENTE NAO REGISTADO");
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

        public Ponto consultar(String nome){
            try{
                Ponto atual = procurar(nome, raiz);
                Ponto ponto = puxar(raiz, atual.pessoa);
                System.out.println(ponto.pessoa.toString() + "\nFIM");
                return ponto;
            } catch (NullPointerException e){
                System.out.println("CLIENTE NAO REGISTADO");
                return null;
            }
        }

        public Ponto procurar(String nome, Ponto atual){
            if(atual != null){
                if(!atual.pessoa.nome.equals(nome)){
                    Ponto no = procurar(nome, atual.esquerda);
                    if(no == null) {
                        no = procurar(nome, atual.direita);
                    }
                    return no;
                } else {
                    return atual;
                }
            } else {
                return null;
            }
        }

        public void remover(){
            raiz = null;
            System.out.println("LISTAGEM DE CLIENTES APAGADA");
        }
    }

    public static void main(String[] args) {
        Arvore arvore = new Arvore();
        ler_inputs(arvore);

    }

    public static void ler_inputs(Arvore arvore){
        Scanner sc = new Scanner(System.in);
        while(true) {
            String s = sc.nextLine();
            String[] s1 = s.split(" ");
            if (s1[0].equals("CLIENTE")) {
                Cliente cliente = new Cliente(s1[1], s1[2] + " " + s1[3], Integer.parseInt(s1[4]));
                arvore.adicionar(cliente);
            } else if (s1[0].equals("AQUISICAO")){
                arvore.nova_aquisicao(s1[1], Integer.parseInt(s1[2]));
            } else if (s1[0].equals("CONSULTA")) {
                arvore.consultar(s1[1]);
            } else if (s1[0].equals("LISTAGEM")) {
                arvore.apresentar();
                System.out.print("FIM\n");
            } else if (s1[0].equals("APAGA")) {
                arvore.remover();
            } else if (s1[0].equals("FIM")){
                System.exit(0);
            }
        }
    }

}
