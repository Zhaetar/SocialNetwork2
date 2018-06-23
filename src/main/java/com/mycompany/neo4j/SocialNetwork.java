package com.mycompany.neo4j;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class SocialNetwork {
    public void createUser(Database db) throws InterruptedException, ParseException, SQLException{
    	Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o email: ");
        String email = scanner.next();
        System.out.println("Digite o nome: ");
        String name = scanner.next();
        System.out.println("Digite a Cidade de Nascimento: ");
        String birthTown = scanner.next();
        System.out.println("Digite a Cidade de Residencia: ");
        String livingTown = scanner.next();
        System.out.println("Digite a Data de Nascimento (dd/MM/yyyy): ");
        String birthDate = scanner.next();
        User user = new User(name, email, birthTown, livingTown, birthDate);
	db.insertPerson(user);
        Main.init();
	}
    
//    public void updateUser() throws InterruptedException, ParseException, SQLException {;
//    	String email = getEmail();
//    	User user = getUser(email);
//    	int exit = 0;
//    	Scanner scanner = new Scanner(System.in);
//
//    	String newEmail = user.getEmail();
//    	String newName = user.getName();
//    	String newBirthTown = user.getBirthTown();
//    	String newLivingTown = user.getLivingTown();
//    	String newBirthDate = user.getBirthDate();
//    	
//    	while(exit!=1) {
//	    	System.out.println("O que voc� deseja alterar? ");
//			System.out.println("1-  Email");  
//			System.out.println("2-  Nome");                              
//			System.out.println("3 - Cidade de Nascimento"); 
//			System.out.println("4-  Cidade de Residencia");	
//			System.out.println("5-  Data de Nascimento");
//			System.out.println("9 - Cancelar");
//			System.out.println("0 - Finalizar");
//			
//			int option = scanner.nextInt();
//	
//			switch (option) {	
//			case 1:
//				System.out.println("Digite o novo Email: ");
//				newEmail = scanner.next();
//				break;
//			case 2:
//				System.out.println("Digite o novo Nome: ");
//				newName = scanner.next();
//				break;
//			case 3:
//				System.out.println("Digite a nova Cidade de Nascimento: ");
//				newBirthTown = scanner.next();
//				break;
//			case 4:
//				System.out.println("Digite a nova Cidade de Residencia: ");
//				newLivingTown = scanner.next();
//				break;
//			case 5:
//				System.out.println("Digite a nova Data de Nascimento: ");
//				newBirthDate = scanner.next();
//				break;		
//			case 9:
//				exit = 1;
//				Interface.init();
//				break;
//			case 0:
//				exit = 1;
//				break;
//			default:
//				System.out.println("Por favor, digite uma opcao valida!");
//				break;
//			}
//		}
//    	
//        user.update(newName, newEmail, newBirthTown, newLivingTown, newBirthDate);
//	}
    
    public void deleteUser(Database db) throws InterruptedException, ParseException, SQLException {
    	String email = getEmail();
    	Node person = db.getPerson(email);
    	db.removePerson(person);
	}

    public void deleteFriendship(Database db) throws InterruptedException, ParseException, SQLException {
    	String email1 = getEmail();
	String email2 = getEmail();	
        Relationship friends = db.getFriendship(email1, email2);
    	db.removeFriendship(friends);
	}
    
    public void createFriendship(Database db) throws InterruptedException, ParseException, SQLException {
    	String email1 = getEmail();
    	String email2 = getEmail();
        
        Node friend1 = db.getPerson(email1);
        Node friend2 = db.getPerson(email2);
    	db.createFriendship(friend1, friend2);
	}
    
//    public void getFriends() throws InterruptedException, ParseException, SQLException {
//    	String email = getEmail();
//    	User user = getUser(email);
//		
//    	user.getFriends();
//	}
//    
    public String getEmail(){
    	System.out.println("Qual o e-mail do usuario que voce deseja selecionar?");
    	Scanner scanner = new Scanner(System.in);
    	
    	String email = scanner.next();
    	
    	if(email != null)
            return email;
    	else 
            System.out.println("\n E-mail invalido, por favor digite um E-mail valido.");
            return null;
    }
    
    	
    
}