/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    GraphDatabaseFactory factory = new GraphDatabaseFactory();
    GraphDatabaseService db = factory.newEmbeddedDatabase("/PortableNoSQL/neo4j/data/graph.db");
    Index<Node> persons;
    RelationshipIndex friendships;
    

    public Database() {
        try(Transaction tx = db.beginTx();) {
            IndexManager index = db.index();
            persons = index.forNodes( "Person" );
            friendships = index.forRelationships( "Friend" );
            tx.success();
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
                node.setProperty("email", user.email);
            if(user.name!=null)
                node.setProperty("name", user.name);
            if(user.birthTown!=null)
                node.setProperty("birthTown", user.birthTown);
            if(user.livingTown!=null)
                node.setProperty("livingTown", user.livingTown);
            if(user.birthDate!=null)
                node.setProperty("birthDate", user.birthDate);
               
            //Adiciona o index
            this.persons.add(node, "email", node.getProperty("email"));
            tx.success();
            System.out.println("Pessoa criada com sucesso!");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("\nErro ao inserir usuario! Voltando ao menu..");
        }
    }
    public Node getPerson (String email ) {
        IndexHits<Node> hits = persons.get( "email", email );
        return hits.getSingle();
    }
    public void removePerson(Node person) {
        this.persons.remove(person);
        try (Transaction tx = db.beginTx()) {
            for (Relationship r : person.getRelationships()) {
                r.delete();
            }
            person.delete();
            tx.success();
        }
    }
    
    public void updatePerson(User user) { //fix
        Node node = getPerson(user.email);
        removePerson(node);
        insertPerson(user);
    }
    
    public void createFriendship(Node friend1, Node friend2) {
       try (Transaction tx = db.beginTx()) {
            Relationship friends = friend1.createRelationshipTo(friend2, RelationType.Friend);
            String id =  friend1.getProperty("email") + "_" + friend2.getProperty("email");
            friends.setProperty("id", id);
            this.friendships.add(friends, "email", friends.getProperty("id"));
            tx.success();
        }
    }
    
    public Relationship getFriendship (String email1, String email2) {
        String id =  email1 + "_" + email2;
        Relationship friends = this.friendships.get( "id", id ).getSingle();
//        Node friend1 = friends.getStartNode();
//        Node friend2 = friends.getEndNode();;
        return friends;
    }
    public void removeFriendship(Relationship friends) {
        this.friendships.remove(friends);
        try (Transaction tx = db.beginTx()) {
            friends.delete();
            tx.success();
        }
    }
    
    public void endConnection() {
        db.shutdown();
    }
}
        