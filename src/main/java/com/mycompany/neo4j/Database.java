package com.mycompany.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.index.RelationshipIndex;

/**
 *
 * @author Caroline, Matheus
 */
public class Database {
    GraphDatabaseFactory factory;
    GraphDatabaseService db;
    Index<Node> persons;
    RelationshipIndex friendships;
    boolean DEBUG = true;
    

    public Database() {
        factory = new GraphDatabaseFactory();
        db = factory.newEmbeddedDatabase("/PortableNoSQL/neo4j/data/graph.db");
        try(Transaction tx = db.beginTx();) {
            IndexManager index = db.index();
            persons = index.forNodes( "Person" );
            friendships = index.forRelationships( "Friend" );
            tx.success();
            System.out.println("\nBanco de dados conectado com sucesso");
        } catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            System.out.println("\nErro ao conectar ao banco de dados..");
        }
    }
    
    public enum NodeType implements Label {
        Person;
    }
    
    public enum RelationType  implements RelationshipType {
        Friend;
    }
    
    public void insertPerson(User user) {
        try(Transaction tx = db.beginTx()) {
            Node node = db.createNode(Database.NodeType.Person);
            if(user.email!=null)
                node.setProperty("email", user.email.toUpperCase());
            if(user.name!=null)
                node.setProperty("name", user.name);
            if(user.birthTown!=null)
                node.setProperty("birthTown", user.birthTown);
            if(user.livingTown!=null)
                node.setProperty("livingTown", user.livingTown);
            if(user.birthDate!=null)
                node.setProperty("birthDate", user.birthDate);
               
            //Adiciona o index
            this.persons.add(node, "email", node.getProperty("email").toString().toUpperCase());
            tx.success();
            System.out.println("\nPessoa criada com sucesso!");
        } catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            System.out.println("\nErro ao inserir usuario! Voltando ao menu..");
        }
    }
    public Node getPerson (String email ) {
        try(Transaction tx = db.beginTx()) {
            IndexHits<Node> hits = persons.get( "email", email.toUpperCase());
            tx.success();
            return hits.getSingle();
        } catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            return null;
        }
    }
    public void removePerson(Node person) {
        try (Transaction tx = db.beginTx()) {
            this.persons.remove(person);
            for (Relationship r : person.getRelationships()) {
                r.delete();
            }
            person.delete();
            tx.success();
            System.out.println("\nUsuario removida com sucesso!");
        } catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            System.out.println("\nErro ao remover usuario! Voltando ao menu..");
        }
    }
    
    public void updatePerson(Node person, User user) {
        try (Transaction tx = db.beginTx()) {
           for ( Node node : this.persons.query( "email:"+person.getProperty("email").toString().toUpperCase()) ) {
               node.setProperty( "email", user.email.toUpperCase() );
               node.setProperty( "name", user.name );
               node.setProperty( "birthTown", user.birthTown );
               node.setProperty( "livingTown", user.livingTown );
               node.setProperty( "birthDate", user.birthDate);
            }
            tx.success();
        } 
    }
    
    public void createFriendship(Node friend1, Node friend2) {
       try (Transaction tx = db.beginTx()) {
            Relationship friends = friend1.createRelationshipTo(friend2, RelationType.Friend);
            String id =  friend1.getProperty("email") + "_" + friend2.getProperty("email");
            friends.setProperty("id", id);
            friends.setProperty("friend1", friend1.getProperty("email").toString().toUpperCase());
            friends.setProperty("friend2", friend2.getProperty("email").toString().toUpperCase());
            this.friendships.add(friends, "id", friends.getProperty("id"));
            tx.success();
            System.out.println("\nAmizade criada com sucesso!");
        } catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            System.out.println("\nErro ao criar amizade! Voltando ao menu..");
        }
    }    
    public Relationship getFriendship (String email1, String email2) {
        try (Transaction tx = db.beginTx()) {
            Relationship friendsReturn = null;
            for ( Relationship friends : friendships.get( "friend1", email1) ){
                if (friends.getEndNode().getProperty("email").toString().toUpperCase().equals(email2.toUpperCase())){
                    friendsReturn = friends;
                }
            }
            for ( Relationship friends : friendships.get( "friend1", email2) ){
                if (friends.getEndNode().getProperty("email").toString().toUpperCase().equals(email1.toUpperCase())){
                    friendsReturn = friends;
                }
            }
            tx.success();
            return friendsReturn;
        } catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            System.out.println("\nErro ao encontrar amizade! Voltando ao menu..");
            return null;
        }
    }
    
    public void getFriends (Node person) {
        try (Transaction tx = db.beginTx()) {
            boolean header = true;
            for ( Relationship friends : this.friendships.query( "id:*"+person.getProperty("email")+" OR id:"+person.getProperty("email")+"*") ) {
                if(header) {
                    System.out.println("Estes são os amigos de "+person.getProperty("name")+":");
                    header = false;
                }
                if((friends.getStartNode().getProperty("email")).equals(person.getProperty("email"))) {
                    System.out.println(friends.getEndNode().getProperty("name"));
                } else {
                    System.out.println(friends.getStartNode().getProperty("name"));
                }
            }
            tx.success();
        }  catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            System.out.println("\nErro ao encontrar amizades! Voltando ao menu..");
        }
    }
    
    public void removeFriendship(Relationship friends) { 
        try (Transaction tx = db.beginTx()) {
            this.friendships.remove(friends);
            friends.delete();
            tx.success();
            System.out.println("\nSucesso ao deletar amizade!");
        }  catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            System.out.println("\nErro ao remover amizade! Voltando ao menu..");
        }
    }
    
    public String getPersonEmail(Node person){
        try (Transaction tx = db.beginTx()) {
            return ((String) person.getProperty("email"));
        } catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            return null;
        }
    }
    
    public String getPersonName(Node person){
        try (Transaction tx = db.beginTx()) {
            return ((String) person.getProperty("name"));
        } catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            return null;
        }
    }
    
    public String getPersonBirthTown(Node person){
        try (Transaction tx = db.beginTx()) {
            return ((String) person.getProperty("birthTown"));
        } catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            return null;
        }
    }
    
    public String getPersonLivingTown(Node person){
        try (Transaction tx = db.beginTx()) {
            return ((String) person.getProperty("livingTown"));
        } catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            return null;
        }
    }
    
    public String getPersonBirthDate(Node person){
        try (Transaction tx = db.beginTx()) {
            return ((String) person.getProperty("birthDate"));
        } catch (Exception e) {
            if(DEBUG) {System.out.println(e);}
            return null;
        }
    }
    
    public void endConnection() {
        db.shutdown();
    }
}
        