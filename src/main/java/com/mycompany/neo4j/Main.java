package com.mycompany.neo4j;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * @author Carol Heloisa
 * @author Matheus Patrick
 */
public class Main {
    public static void init() throws InterruptedException, ParseException, SQLException {
        Database db = new Database();        
        System.out.println("\n Rede Social ");
        System.out.println("1 - Criar usuario");  
        System.out.println("2 - Cadastro de amizade");  
        System.out.println("3 - Alteracao de dados de usuario");                              
        System.out.println("4 - Remocao de amizades"); 
        System.out.println("5 - Remocao de pessoas");	
        System.out.println("6 - Buscar todos os amigos de primeiro grau de uma pessoa");
        System.out.println("0 - Sair do Programa");

        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();
        SocialNetwork SN = new SocialNetwork();

        switch (opcao) {	
                case 1:
                        SN.createUser(db);
                        break;	
                case 2:
                        SN.createFriendship(db);
                        break;	
//			case 3:
//				SN.updateUser();
//				break;	
                case 4:
                        SN.deleteFriendship(db);
                        break;
                case 5:
                        SN.deleteUser(db);
                        break;
//			case 6:	
//				SN.getFriends();
//				break;			
                case 0:
                        System.exit(0);		
                case 11:
                         db.endConnection();
                         break;
                default:
                        System.out.println("Por favor, digite uma opcao valida!");
                        init();
                        break;
        }
    }

	public static void main(String[] args) throws InterruptedException, SQLException {
		try {
			init();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}