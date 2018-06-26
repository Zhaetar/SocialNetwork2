package com.mycompany.neo4j;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * @author Caroline Heloisa
 * @author Matheus Patrick
 */
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
   
}
