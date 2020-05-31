using APIGateway.DB;
using APIGateway.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIGateway.Services
{
    public class DBUser
    {
        private UserContext db;

        public DBUser(UserContext db)
        {
            this.db = db;
        }

        public User retrieveUserByNamePwd(User user)
        {
            User userDetails = db.User.Where(
            x => x.Username == user.Username && x.Password == user.Password)
            .FirstOrDefault();
            return userDetails;
        }
    }
}
