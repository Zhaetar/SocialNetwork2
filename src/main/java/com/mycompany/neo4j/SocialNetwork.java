package com.mycompany.neo4j;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

/**
 * @author Caroline Heloisa
 * @author Matheus Patrick
 */
public class SocialNetwork {
    public void createUser(Database db) throws InterruptedException, ParseException, SQLException{
    	System.out.println("------------------------------------------------");
        System.out.println("Módulo de criação de usuário iniciado,");
        System.out.println("por favor insira a seguir os dados do usuário a ser cadastrado");
    	System.out.println("------------------------------------------------");
    	Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o email: ");
        String email = scanner.next();
        if(db.getPerson(email)!=null){
            System.out.println("Este email já está cadastrado em nosso sistema, por favor escolha outro.");
            createUser(db);
            return;
        }
        System.out.println("Digite o nome: ");
        String name = scanner.next();
        System.out.println("Digite a Cidade de Nascimento: ");
        String birthTown = scanner.next();
        System.out.println("Digite a Cidade de Residencia: ");
        String livingTown = scanner.next();
        System.out.println("Digite a Data de Nascimento (dd/MM/yyyy): ");
        String birthDate = scanner.next();
        if(db.getPerson(email)!=null){
            System.out.println("Este email já está cadastrado em nosso sistema, por favor escolha outro.");
            return;
        }
        User user = new User(name, email, birthTown, livingTown, birthDate);
	db.insertPerson(user);
        Main.init(db);
	}
    
    public void updateUser(Database db) throws InterruptedException, ParseException, SQLException {
    	System.out.println("------------------------------------------------");
        System.out.println("Módulo de alteração de usuário iniciado,");
        System.out.println("por favor insira a seguir os dados do usuário a ser alterado");
    	System.out.println("------------------------------------------------");
        String email = getEmail();
    	int exit = 0;
    	Scanner scanner = new Scanner(System.in);
        String newEmail, newName, newBirthTown, newLivingTown, newBirthDate;

        Node person = db.getPerson(email);
        if (person == null) {
            System.out.println("Usuário inválido, voltando ao menu.");
            return ;
        }
        
        newEmail      = db.getPersonEmail(person);
        newName       = db.getPersonName(person);
        newBirthTown  = db.getPersonBirthTown(person);
        newLivingTown = db.getPersonLivingTown(person);
        newBirthDate  = db.getPersonBirthDate(person);
    	
    	while(exit!=1) {
            System.out.println("\nO que voce deseja alterar? ");
            System.out.println("\n1-  Email");  
            System.out.println("2-  Nome");                              
            System.out.println("3 - Cidade de Nascimento"); 
            System.out.println("4-  Cidade de Residencia");	
            System.out.println("5-  Data de Nascimento");
            System.out.println("9 - Cancelar");
            System.out.println("0 - Finalizar");

            int option = scanner.nextInt();

            switch (option) {	
            case 1:
                System.out.println("Digite o novo Email: ");
                newEmail = scanner.next();
                break;
            case 2:
                System.out.println("Digite o novo Nome: ");
                newName = scanner.next();
                break;
            case 3:
                System.out.println("Digite a nova Cidade de Nascimento: ");
                newBirthTown = scanner.next();
                break;
            case 4:
                System.out.println("Digite a nova Cidade de Residencia: ");
                newLivingTown = scanner.next();
                break;
            case 5:
                System.out.println("Digite a nova Data de Nascimento: ");
                newBirthDate = scanner.next();
                break;		
            case 9:
                exit = 1;
                break;
            case 0:
                exit = 1;
                break;
            default:
                System.out.println("Por favor, digite uma opcao valida!");
                break;
            }
        }
        User user = new User(newName, newEmail, newBirthTown, newLivingTown, newBirthDate);
        db.updatePerson(person, user);
    }

    public void deleteUser(Database db) {
    	String email = getEmail();
    	Node person = db.getPerson(email);
        if (person == null) {
            System.out.println("Usuário inválido, voltando ao menu.");
            return ;
        }
    	db.removePerson(person);
	}

    public void deleteFriendship(Database db) {
    	String email1 = getEmail();
	String email2 = getEmail();	
        Relationship friends = db.getFriendship(email1, email2);
    	db.removeFriendship(friends);
	}
    
    public void createFriendship(Database db) throws InterruptedException, ParseException, SQLException {
    	String email1 = getEmail();
    	String email2 = getEmail(); 
        
        Node friend1 = db.getPerson(email1);
        if (friend1 == null) {
            System.out.println("Usuário inválido, voltando ao menu.");
            return ;
        }
        Node friend2 = db.getPerson(email2);
        if (friend2 == null) {
            System.out.println("Usuário inválido, voltando ao menu.");
            return ;
        }
    	db.createFriendship(friend1, friend2);
        Main.init(db);
	}
    
    public void getFriends(Database db) {
    	String email = getEmail();
    	Node person = db.getPerson(email);
        if (person == null) {
            System.out.println("Usuário inválido, voltando ao menu.");
            return ;
        }
    	db.getFriends(person);
	}
    
    public String getEmail(){
    	System.out.println("Qual o e-mail do usuario que voce deseja selecionar?");
    	Scanner scanner = new Scanner(System.in);
    	
    	String email = scanner.next();
    	
    	if(email != null) {
            return email;
        } else  {
            System.out.println("\n E-mail invalido, por favor digite um E-mail valido.");
            return null;
        }
    }
    
    	
    
}