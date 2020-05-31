using APIGateway.Models;
using APIGateway.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIGateway.DB
{
    public class DBSeeder
    {
        public DBSeeder(UserContext dbcontext)
        {

            User user1 = new User();
            user1.Id = Guid.NewGuid().ToString();
            user1.Username = "admin";
            user1.Password = Crypto.Sha256("admin");
            user1.Email = "admin@sa50.edu";
            dbcontext.Add(user1);

            User user2 = new User();
            user2.Id = Guid.NewGuid().ToString();
            user2.Username = "john";
            user2.Password = Crypto.Sha256("john");
            user2.Email = "john@sa50.edu";
            dbcontext.Add(user2);


            dbcontext.SaveChanges();
        
        }
      }
   }