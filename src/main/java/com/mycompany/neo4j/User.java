package com.mycompany.neo4j;

import java.sql.SQLException;
import java.text.ParseException;

public final class User { 
   protected String name;
   protected String email;
   protected String birthDate;
   protected String birthTown;
   protected String livingTown;
 
  
   public User (String name, String email, String birthTown, String livingTown, String birthDate ) throws InterruptedException, ParseException, SQLException {
        if(!"".equals(email) && email!=null)
            this.setEmail(email);
        if(!"".equals(name) && name!=null)
            this.setName(name);
        if(!"".equals(birthTown) && birthTown!=null)
            this.setBirthTown(birthTown);
        if(!"".equals(livingTown) && livingTown!=null)
            this.setLivingTown(livingTown);
        if(birthDate!=null)
            this.setBirthDate(birthDate);
    }

   public String getName() {
       return name;
   }
   
   public void setName(String name) {
       this.name = name;
   }
   
   public String getEmail() {
       return email;
   }   
   
   public void setEmail(String email) {
       this.email = email;
   }
   
   public String getBirthTown() {
       return birthTown;
   }
   
   public void setBirthTown(String birthTown) {
       this.birthTown = birthTown;
   }
   
   public String getLivingTown() {
       return livingTown;
   }
   
   public void setLivingTown(String livingTown) {
       this.livingTown = livingTown;
   }
   
   public String getBirthDate() {
       return birthDate;
   }
   
   public void setBirthDate(String birthDate) {
       this.birthDate = birthDate;
   }
   
}
