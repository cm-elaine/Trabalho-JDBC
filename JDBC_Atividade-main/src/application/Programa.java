package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import entities.Aluno;
import jdbc.AlunoJDBC;
import jdbc.DB;

public class Programa {

    public static void main(String[] args) throws IOException, SQLException {

        Connection con = DB.getConexao();
        System.out.println("Conexão realizada com sucesso !");
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        
        Scanner console = new Scanner(System.in);
        
        int opcao = 0;
        
        try {

            do {
                System.out.print("######## Menu ########" + 
                                "\n1- Cadastrar" + 
                                "\n2- Listar"    + 
                                "\n3- Alterar"   +
                                "\n4- Excluir"   + 
                                "\n5- Sair"      +
                                "\n\tOpção: ");
                opcao = Integer.parseInt(console.nextLine());

                AlunoJDBC acao = new AlunoJDBC();
                
                if (opcao == 1) {

                    Aluno a = new Aluno();

                    System.out.print("\n*** Cadastrar Aluno ***\n\r");
                    System.out.print("Nome: ");
                    a.setNome(console.nextLine());
                    System.out.print("Sexo: ");
                    a.setSexo(console.nextLine());
        
                    System.out.print("Data de nascimento (dd/MM/yyyy): ");
                    a.setDt_nasc(Date.valueOf(LocalDate.parse(console.nextLine(), formato)));
                    
                    acao.salvar(a, con);
                    console.nextLine();

                } else if (opcao == 2) {
                    // Opção 2 - Listar
                    System.out.println("\n*** Lista de Alunos ***\n");
                    List<Aluno> alunos = acao.listar(con);
                    for (Aluno aluno : alunos) {
                        System.out.println("ID: " + aluno.getId() +
                                           ", Nome: " + aluno.getNome() +
                                           ", Sexo: " + aluno.getSexo() +
                                           ", Data de Nascimento: " + aluno.getDt_nasc());
                    }
                    console.nextLine();

                } else if (opcao == 3) {
                    // Opção 3 - Alterar
                    System.out.print("\n*** Alterar Aluno ***\n\r");
                    System.out.print("Informe o ID do aluno a ser alterado: ");
                    int id = Integer.parseInt(console.nextLine());

                    Aluno a = new Aluno();
                    a.setId(id);

                    System.out.print("Novo Nome: ");
                    a.setNome(console.nextLine());
                    System.out.print("Novo Sexo: ");
                    a.setSexo(console.nextLine());
        
                    System.out.print("Nova Data de Nascimento (dd/MM/yyyy): ");
                    a.setDt_nasc(Date.valueOf(LocalDate.parse(console.nextLine(), formato)));
                    
                    acao.alterar(a, con);
                    console.nextLine();

                } else if (opcao == 4) {
                    // Opção 4 - Excluir
                    System.out.print("\n*** Excluir Aluno ***\n\r");
                    System.out.print("Informe o ID do aluno a ser excluído: ");
                    int id = Integer.parseInt(console.nextLine());
                    
                    acao.apagar(id, con);
                    console.nextLine();

                }

            } while (opcao != 5);

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        
        DB.fechaConexao();
        System.out.println("Conexão fechada com sucesso !");
    }
}
