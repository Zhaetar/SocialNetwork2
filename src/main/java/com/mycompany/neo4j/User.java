package com.mycompany.neo4j;

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
 
  
   public User (String name, String email, String birthTown, String livingTown, String birthDate ) {
        if(!"".equals(email) && email!=null)
            this.email = email;
        if(!"".equals(name) && name!=null)
            this.name = name;
        if(!"".equals(birthTown) && birthTown!=null)
            this.birthTown = birthTown;
        if(!"".equals(livingTown) && livingTown!=null)
            this.livingTown = livingTown;
        if(birthDate!=null)
            this.birthDate = birthDate;
    }
   
}
